package edu.mit.compilers.cst;

import antlr.Token;
import edu.mit.compilers.grammar.DecafParserTokenTypes;
import edu.mit.compilers.ir.common.*;
import edu.mit.compilers.ir.decl.IRFieldDecl;
import edu.mit.compilers.ir.decl.IRImportDecl;
import edu.mit.compilers.ir.decl.IRMethodDecl;
import edu.mit.compilers.ir.expression.*;
import edu.mit.compilers.ir.expression.literal.IRBoolLiteral;
import edu.mit.compilers.ir.expression.literal.IRCharLiteral;
import edu.mit.compilers.ir.expression.literal.IRIntLiteral;
import edu.mit.compilers.ir.expression.literal.IRStringLiteral;
import edu.mit.compilers.ir.statement.*;
import edu.mit.compilers.ir.type.IRArrayType;
import edu.mit.compilers.ir.type.IRBasicType;
import edu.mit.compilers.ir.type.IRType;

import java.util.ArrayList;

public class CSTParser {

	public static IRProgram parseIRProgram(CST tree) {
		return parseIRProgram(tree.getRoot().getChild(0));
	}
	
	private static IRProgram parseIRProgram(CSTNode node) {
		IRProgram ret = new IRProgram();
		
		// Parse the sub children
		// program : (import_decl)* (field_decl)* (method_decl)*
		for (CSTNode child : node.getChildren()) {
			switch (child.getName()) {
				case "import_decl":
					ret.addIRImportDecl(parseIRImportDecl(child));
					break;
				case "field_decl":
					ret.addIRFieldDecls(parseIRFieldDecls(child));
					break;
				case "method_decl":
					ret.addMethodDecl(parseIRMethodDecl(child));
					break;
				default:
					System.out.println("node's identifier is wrong");
			}
		}
		return ret;
	}
	
	private static IRImportDecl parseIRImportDecl(CSTNode node) { 
		// import_decl : ID;
		assert(node.getChildren().size() == 1);
		IRVariable variable = new IRVariable(node.getChild(0).getToken());
		return new IRImportDecl(variable);
	}
	
	private static IRMethodDecl parseIRMethodDecl(CSTNode node) {
		assert(node.getChildrenSize() == 4);
		
		// method_decl : method_decl_type ID method_decl_args_list block;
		// Method declaration consists of (return)type, identifier, Variable, block 
		
		// method_decl_type : type | TK_void;
		CSTNode cur = node.getChild(0).getChild(0);
		Token typeToken = cur.getToken();
		if (cur.hasChild()) {
			cur = cur.getChild(0);
			typeToken = cur.getToken();
		} 
		assert(typeToken != null);
		
		IRBasicType type = IRBasicType.GetInstance(typeToken.getText());
		IRVariable variable = new IRVariable(node.getChild(1).getToken());
		IRParameterList paraList = parseIRFormalParaList(node.getChild(2));
		IRBlock block = parseIRBlock(node.getChild(3));
		return new IRMethodDecl(variable, type, paraList, block);
	}

	private static IRBlock parseIRBlock(CSTNode node) {
		IRBlock ret = new IRBlock();
		
		// block : (field_decl)* (statement)* ;
		for (CSTNode child : node.getChildren()) {
			switch (child.getName()) {
				case "field_decl":
					ret.addFieldDecls(parseIRFieldDecls(child));
					break;
				case "statement":
					ret.addStatement(parseIRStatement(child));
					break;
			}
		}
		return ret;
	}

	private static IRParameterList parseIRFormalParaList(CSTNode node) {
		ArrayList<IRParameter> paras = new ArrayList<>();
		
		while (node.hasChild() && node.getChildrenSize()>= 3) {
			Token typeToken = node.getChild(0).getChild(0).getToken();
			IRBasicType type = IRBasicType.GetInstance(typeToken.getText());
			IRVariable variable = new IRVariable(node.getChild(1).getToken());
			
			paras.add(new IRParameter(type, variable));
			node = node.getChild(2);
		}
		
		return new IRParameterList(paras);
	}

	private static ArrayList<IRFieldDecl> parseIRFieldDecls(CSTNode node) {
		Token typeToken = node.getChild(0).getChild(0).getToken();
		IRBasicType type = IRBasicType.GetInstance(typeToken.getText());
		
		node = node.getChildren().get(1);
		ArrayList<IRFieldDecl> ret = new ArrayList<>();
		
		// Top-down 
		while (node.hasChild()) {
			IRVariable variable = new IRVariable(node.getChild(0).getToken());
			CSTNode arrayNode = node.getChild(1);
			IRType nowType = type;
			
			/// this type maybe array type
			if (arrayNode.hasChild()) {
				Token size = arrayNode.getChild(0).getToken();
				nowType = new IRArrayType(nowType, new IRIntLiteral(size));
			}
			ret.add(new IRFieldDecl(nowType, variable));
			node = node.getChild(2);
		}
		
		return ret;	
	}
	
	private static IRStatement parseIRStatement(CSTNode node) {
		assert(node.hasChild());
		node = node.getChild(0);
		switch(node.getName()) {
		case "assign_stmt"		: return parseIRAssignStmt(node);
		case "break_stmt"		: return parseIRBreakStmt();
		case "continue_stmt"	: return parseIRContinueStmt();
		case "for_stmt"			: return parseIRForStmt(node);
		case "if_stmt"			: return parseIRIfStmt(node);
		case "method_call_stmt"	: return parseIRMethodCallStmt(node);
		case "return_stmt"		: return parseIRReturnStmt(node);
		case "while_stmt"		: return parseIRWhileStmt(node);
		}
		return null;
	}

	private static IRAssignStmt parseIRAssignStmt(CSTNode node) {
		// assign_stmt: location assign_expr;
		assert(node.getChildrenSize() == 2);
		IRLocation location = parseIRLocation(node.getChild(0));
		node = node.getChild(1);
		
		// assign_expr : assign_op expr | increment;
		CSTNode firstChild = node.getChild(0);
		String tag = firstChild.getName();
		String operator;
		IRAssignStmt ret;
		if (tag.equals("assign_op")) {
			// assign_op : OP_ASSIGN | OP_ASSIGN_PLUS | OP_ASSIGN_MINUS;
			operator = firstChild.getChild(0).getToken().getText();
			IRExpression expression = parseIRExpression(node.getChild(1));
			ret = new IRAssignStmt(location, operator, expression);
		} else if (tag.equals("increment")) {
			// increment : OP_INC | OP_DEC;
			operator = firstChild.getChild(0).getToken().getText();
			ret = new IRAssignStmt(location, operator);
		} else {
			throw new RuntimeException("Unknown tag for assign_expr :" + tag);
		}
		return ret;
	}

	private static IRExpression parseIRExpression(CSTNode node) {
		assert(node.hasChild());
		IRExpression ret;
		switch(node.getName()) {
		case "expr":
		{
			// expr : expr1 (expr expr);
			IRExpression condition = parseIRExpression(node.getChild(0));
			if (node.getChildrenSize() == 3) {
				IRExpression first = parseIRExpression(node.getChild(1));
				IRExpression second = parseIRExpression(node.getChild(2));
				ret = new IRTernaryExpr(condition, first, second);
			} else {
				ret = condition;
			}
			break;
		}
		case "expr1":
		case "expr2":
		case "expr3":
		case "expr4":
		case "expr5":
		case "expr6":
		{
			// xxx : exprx (op exprx)*;
			// Consider left-associative
			IRExpression left = parseIRExpression(node.getChild(0));
			for (int i = 1; i < node.getChildrenSize(); i += 2) { 
				String operator = parseOperator(node.getChild(i));
				IRExpression right = parseIRExpression(node.getChild(i+1));
				left = new IRBinaryOpExpr(left, operator, right);
			}
			ret = left;
			break;
		}
		case "expr7":
		{
			// op* expr;
			// Consider right-associative
			IRExpression right = parseIRExpression(node.getLastChild());
			for (int i = node.getChildrenSize()-2; i >= 0; --i) {
				String operator = parseOperator(node.getChild(i));
				right = new IRUnaryOpExpr(operator, right);
			}
			ret = right;
			break;
		}
		case "expr_base":
		{
			ret = parseExprBaseImpl(node);
			break;
		}
		default:
		{
			throw new RuntimeException("Unknown expr type: " + node.getName());
		}
		}
		
		assert(ret != null);
		return ret;
	}

	private static String parseOperator(CSTNode node) {
		if (node.hasChild()) {
			// Maybe rel_op or eq_op
			node = node.getChild(0);
		}
		return  node.getToken().getText();
	}
	
	private static IRExpression parseExprBaseImpl(CSTNode node) {
		// expr_base : location | method_call | literal | TK_len LPAREN! ID RPAREN! | LPAREN! expr RPAREN!;
		assert(node.getChildrenSize() == 1);
		node = node.getChild(0);
		if (!node.hasChild()) {
			// ID
			IRVariable variable = new IRVariable(node.getToken());
			return new IRLenExpr(variable);
		}
		
		switch(node.getName()) {
		case "location"		:	return parseIRLocation(node);
		case "method_call"	:	return parseIRMethodCall(node);
		case "literal"		:	return parseIRLiteral(node);
		case "expr"			:	return parseIRExpression(node);
		default :
			throw new RuntimeException("Wrong token: " + node.getToken().getText());
		}
	}

	private static IRExpression parseIRLiteral(CSTNode node) {
		// literal	: INT | CHAR | bool_literal
		assert(node.hasChild());
		node = node.getChild(0);
		if (node.getName().equals("bool_literal")) {
			// bool_literal : TK_true | TK_false;
			assert(node.hasChild());
			node = node.getChild(0);
			return new IRBoolLiteral(node.getToken());
		} 
		
		if (node.getToken().getType() == DecafParserTokenTypes.INT) {
			return new IRIntLiteral(node.getToken());
		}
		
		if (node.getToken().getType() == DecafParserTokenTypes.CHAR){
			return new IRCharLiteral(node.getToken());
		}
		
		throw new RuntimeException("Unexpected literal: " + node.getName());
	}

	private static IRLocation parseIRLocation(CSTNode node) {
		// location : ID | ID expr;
		IRLocation ret;
		IRVariable variable = new IRVariable(node.getChild(0).getToken());
		if (node.getChildrenSize() == 2) {
			IRExpression expression = parseIRExpression(node.getChild(1));
			ret = new IRLocation(variable, expression);
		} else {
			ret = new IRLocation(variable);
		}
		return ret;
	}

	private static IRBreakStmt parseIRBreakStmt() {
		return new IRBreakStmt();
	}

	private static IRContinueStmt parseIRContinueStmt() {
		return new IRContinueStmt();
	}

	private static IRForStmt parseIRForStmt(CSTNode node) {
		// for_stmt : ID OP_ASSIGN expr1  expr2 location (compound_assign_op expr | increment) block;
		assert(node.getChildrenSize() >= 7);
		IRVariable variable = new IRVariable(node.getChild(0).getToken());
		String operator1 = node.getChild(1).getToken().getText();
		IRExpression  initValue = parseIRExpression(node.getChild(2));
		IRAssignStmt initializer = new IRAssignStmt(variable, operator1, initValue);
		
		IRExpression condition = parseIRExpression(node.getChild(3));
		IRLocation location = parseIRLocation(node.getChild(4));
		String operator2;
		IRAssignStmt step;
		
		if (node.getChild(5).getName().equals("compound_assign_op")) {
			// compound_assign_op : OP_ASSIGN_PLUS | OP_ASSIGN_MINUS;
			operator2 = node.getChild(5).getChild(0).getToken().getText();
			IRExpression stepRValue = parseIRExpression(node.getChild(6));
			step = new IRAssignStmt(location, operator2, stepRValue);
		} else {
			// increment : OP_INC | OP_DEC;
			operator2 = node.getChild(5).getChild(0).getToken().getText();
			step = new IRAssignStmt(location, operator2);
		}
		
		IRBlock block = parseIRBlock(node.getLastChild());
		return new IRForStmt(initializer, condition, step, block);
	}

	private static IRIfStmt parseIRIfStmt(CSTNode node) {
		//if_stmt : expr block (block)?;
		IRIfStmt ret;
		IRExpression condition = parseIRExpression(node.getChild(0));
		IRBlock ifBlock = parseIRBlock(node.getChild(1));
		if (node.getChildrenSize() == 3) {
			IRBlock elseBlock = parseIRBlock(node.getChild(2));  
			ret = new IRIfStmt(condition, ifBlock, elseBlock);
		} else {
			ret = new IRIfStmt(condition, ifBlock);
		}
		return ret;
	}

	private static IRMethodCallStmt parseIRMethodCallStmt(CSTNode node) {
		// method_call_stmt : method_call;
		assert(node.getChildrenSize() == 1);
		node = node.getChild(0);
		return new IRMethodCallStmt(parseIRMethodCall(node));
	}

	private static IRMethodCall parseIRMethodCall(CSTNode node) {
		// method_call : ID (import_arg_list)?;
		IRVariable variable = new IRVariable(node.getChild(0).getToken());
		ArrayList<IRExpression> args;
		if (node.getChildrenSize() > 1) {
			args = parseImportArgsImpl(node.getChild(1));
		} else {
			args = new ArrayList<>();
		}
		IRArgumentList argList = new IRArgumentList(args);
		
		return new IRMethodCall(variable, argList);
	}

	private static ArrayList<IRExpression> parseImportArgsImpl(CSTNode node) {
		// import_arg_list : import_arg import_arg_list_more;
		ArrayList<IRExpression> ret = new ArrayList<>();
		while (node.hasChild()) {
			ret.add(parseImportArgImpl(node.getChild(0)));
			node = node.getChild(1);
		}
		return ret;
	}

	private static IRExpression parseImportArgImpl(CSTNode node) {
		// import_arg : expr | STRING;
		assert(node.hasChild());
		node = node.getChild(0);
		if (node.getName().equals("expr")) {
			return parseIRExpression(node);
		} else {
			return new IRStringLiteral(node.getToken());
		}
	}

	private static IRStatement parseIRReturnStmt(CSTNode node) {
		// return_stmt: (expr)? ;
		IRExpression expr;
		if (node.hasChild()) {
			expr = parseIRExpression(node.getChild(0));
		} else {
			expr = new IREmptyExpr();
		}
		return new IRReturnStmt(expr);
	}
	
	private static IRWhileStmt parseIRWhileStmt(CSTNode node) {
		// while_stmt: expr block;
		assert(node.hasChild());
		IRExpression condition = parseIRExpression(node.getChild(0));
		IRBlock block = parseIRBlock(node.getChild(1));
		return new IRWhileStmt(condition, block);
	}
}
