package edu.mit.compilers.cst;

import java.util.ArrayList;
import java.util.Iterator;

import antlr.Token;
import edu.mit.compilers.ir.IRProgram;
import edu.mit.compilers.ir.decl.IRFieldDecl;
import edu.mit.compilers.ir.decl.IRImportDecl;
import edu.mit.compilers.ir.decl.IRMethodDecl;
import edu.mit.compilers.ir.expression.IRBinaryOpExpr;
import edu.mit.compilers.ir.expression.IRExpression;
import edu.mit.compilers.ir.expression.IRLenExpr;
import edu.mit.compilers.ir.expression.IRLocation;
import edu.mit.compilers.ir.expression.IRMethodCall;
import edu.mit.compilers.ir.expression.IRTernaryExpr;
import edu.mit.compilers.ir.expression.IRUnaryOpExpr;
import edu.mit.compilers.ir.statement.IRAssignStmt;
import edu.mit.compilers.ir.statement.IRBlock;
import edu.mit.compilers.ir.statement.IRBreakStmt;
import edu.mit.compilers.ir.statement.IRContinueStmt;
import edu.mit.compilers.ir.statement.IRForStmt;
import edu.mit.compilers.ir.statement.IRIfStmt;
import edu.mit.compilers.ir.statement.IRImportArg;
import edu.mit.compilers.ir.statement.IRMethodCallStmt;
import edu.mit.compilers.ir.statement.IRStatement;
import edu.mit.compilers.ir.statement.IRWhileStmt;
import edu.mit.compilers.semantic.ArrayTypeDesc;
import edu.mit.compilers.semantic.BasicTypeDesc;
import edu.mit.compilers.semantic.Identifier;
import edu.mit.compilers.semantic.TypeDesc;
import edu.mit.compilers.semantic.Variable;

public class CSTParser {

	public static IRProgram parseIRProgram(CST tree) {
		return parseIRProgram(tree.getRoot().getChild(0));
	}
	
	private static IRProgram parseIRProgram(CSTNode node) {
		IRProgram ret = new IRProgram();
		
		// Parse the sub children
		// program : (import_decl)* (field_decl)* (method_decl)*
		Iterator<CSTNode> iter = node.getChildren().iterator();
		while (iter.hasNext()) {
			CSTNode child = iter.next();
			switch(child.getName()) {
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
		assert(node.getChildren().size() >= 2);
		return new IRImportDecl(node.getChildren().get(1).getToken());
	}
	
	private static IRMethodDecl parseIRMethodDecl(CSTNode node) {
		assert(node.getChildrenSize() >= 4);
		
		// Method declaration consists of (return)type, identifier, Variable, block 
		Token type = node.getChild(0).getChild(0).getToken();
		Token identifier = node.getChild(1).getToken();
		ArrayList<Variable> Variables = parseVariableList(node.getChild(2));
		IRBlock block = parseIRBlock(node.getChild(3));
		return new IRMethodDecl(type, identifier, Variables, block);
	}

	private static IRBlock parseIRBlock(CSTNode node) {
		IRBlock ret = new IRBlock();
		
		// block : (field_decl)* (statement)* ;
		Iterator<CSTNode> iter = node.getChildren().iterator();
		while (iter.hasNext()) {
			CSTNode child = iter.next();
			switch(child.getName()) {
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

	private static ArrayList<Variable> parseVariableList(CSTNode node) {
		ArrayList<Variable> ret = new ArrayList<Variable>();
		
		while (node.hasChild() && node.getChildrenSize()>= 3) {
			Token type = node.getChild(0).getChild(0).getToken();
			Token identifier = node.getChild(1).getToken();
			ret.add(new Variable(type, identifier));
			node = node.getChild(2);
		}
		
		return ret;
	}

	private static ArrayList<IRFieldDecl> parseIRFieldDecls(CSTNode node) {
		Token type = node.getChild(0).getChild(0).getToken();
		BasicTypeDesc basicType = BasicTypeDesc.GetInstance(type.getText());
		
		node = node.getChildren().get(1);
		ArrayList<IRFieldDecl> ret = new ArrayList<IRFieldDecl>();
		
		// Top-down 
		while (node.hasChild()) {
			Token identifier = node.getChild(0).getToken();
			CSTNode arrayNode = node.getChild(1);
			TypeDesc nowType = basicType;
			
			/// this type maybe array type
			if (arrayNode.hasChild()) {
				Token lengthToken = arrayNode.getChild(0).getToken();
				int length = Integer.parseInt(lengthToken.getText());
				nowType = new ArrayTypeDesc(nowType, length);
			}
			ret.add(new IRFieldDecl(nowType, identifier));
			node = node.getChild(2);
		}
		
		return ret;	
	}
	
	private static IRStatement parseIRStatement(CSTNode node) {
		assert(node.hasChild());
		node = node.getChild(0);
		switch(node.getName()) {
		case "assign_stmt"		: return parseIRAssignStmt(node);
		case "break_stmt"		: return parseIRBreakStmt(node);
		case "continue_stmt"	: return parseIRContinueStmt(node);
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
		if (tag == "assign_op") {
			// assign_op : OP_ASSIGN | OP_ASSIGN_PLUS | OP_ASSIGN_MINUS;
			operator = firstChild.getChild(0).getToken().getText();
			IRExpression expression = parseIRExpression(node.getChild(1));
			ret = new IRAssignStmt(location, operator, expression);
		} else if (tag == "increment") {
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
		case "expr1":
		case "expr2":
		case "expr3":
		case "expr4":
		case "expr5":
		case "expr6":
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
		case "expr7":
			// op* expr;
			// Consider right-associative
			IRExpression right = parseIRExpression(node.getLastChild());
			for (int i = node.getChildrenSize()-2; i >= 0; --i) {
				String operator = parseOperator(node.getChild(i));
				right = new IRUnaryOpExpr(operator, right);
			}
			ret = right;
			break;
		case "expr_base":
			ret = parseExprBaseImpl(node);
		}
		return null;
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
			Identifier identifier = new Identifier(node.getToken());
			return new IRLenExpr(identifier);
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
		// TODO Auto-generated method stub
		return null;
	}

	private static IRLocation parseIRLocation(CSTNode node) {
		// location : ID | ID expr;
		IRLocation ret;
		Identifier identifier = new Identifier(node.getChild(0).getToken());
		if (node.getChildrenSize() == 2) {
			IRExpression expression = parseIRExpression(node.getChild(1));
			ret = new IRLocation(identifier, expression);
		} else {
			ret = new IRLocation(identifier);
		}
		return ret;
	}

	private static IRBreakStmt parseIRBreakStmt(CSTNode node) {
		return new IRBreakStmt();
	}

	private static IRContinueStmt parseIRContinueStmt(CSTNode node) {
		return new IRContinueStmt();
	}

	private static IRForStmt parseIRForStmt(CSTNode node) {
		// for_stmt : ID OP_ASSIGN expr1  expr2 location (compound_assign_op expr | increment) block;
		Identifier identifier = new Identifier(node.getChild(0).getToken());
		String operator1 = node.getChild(1).getToken().getText();
		IRExpression  initValue = parseIRExpression(node.getChild(2));
		IRAssignStmt initializer = new IRAssignStmt(identifier, operator1, initValue);
		
		IRExpression condition = parseIRExpression(node.getChild(3));
		IRLocation location = parseIRLocation(node.getChild(4));
		String operator2;
		IRAssignStmt step;
		
		if (node.getChild(5).getName() == "compound_assign_op") {
			operator2 = node.getChild(5).getChild(0).getChild(0).getToken().getText();
			IRExpression stepRValue = parseIRExpression(node.getChild(6));
			step = new IRAssignStmt(location, operator2, stepRValue);
		} else {
			operator2 = node.getChild(5).getToken().getText();
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
		Identifier identifier = new Identifier(node.getChild(0).getToken());
		ArrayList<IRImportArg> args;
		if (node.getChildrenSize() > 1) {
			args = parseIRImportArgs(node.getChild(1));
		} else {
			args = new ArrayList<IRImportArg>();
		}
		
		return new IRMethodCall(identifier, args);
	}

	private static ArrayList<IRImportArg> parseIRImportArgs(CSTNode node) {
		// import_arg_list : import_arg import_arg_list_more;
		ArrayList<IRImportArg> ret = new ArrayList<IRImportArg>();
		while (node.hasChild()) {
			ret.add(parseIRImportArg(node.getChild(0)));
			node = node.getChild(1);
		}
		return ret;
	}

	private static IRImportArg parseIRImportArg(CSTNode node) {
		// TODO Auto-generated method stub
		return null;
	}

	private static IRStatement parseIRReturnStmt(CSTNode node) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private static IRWhileStmt parseIRWhileStmt(CSTNode node) {
		// TODO Auto-generated method stub
		return null;
	}
}
