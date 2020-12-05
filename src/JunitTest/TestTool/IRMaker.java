package JunitTest.TestTool;

import edu.mit.compilers.ir.common.IRBlock;
import edu.mit.compilers.ir.common.IRParameterList;
import edu.mit.compilers.ir.common.IRProgram;
import edu.mit.compilers.ir.common.IRVariable;
import edu.mit.compilers.ir.decl.IRFieldDecl;
import edu.mit.compilers.ir.decl.IRImportDecl;
import edu.mit.compilers.ir.decl.IRMethodDecl;
import edu.mit.compilers.ir.expression.IRBinaryOpExpr;
import edu.mit.compilers.ir.expression.literal.IRBoolLiteral;
import edu.mit.compilers.ir.expression.literal.IRIntLiteral;
import edu.mit.compilers.ir.expression.literal.IRLiteral;
import edu.mit.compilers.ir.statement.IRStatement;
import edu.mit.compilers.ir.type.IRArrayType;
import edu.mit.compilers.ir.type.IRBasicType;
import edu.mit.compilers.ir.type.IRType;
import edu.mit.compilers.utils.OperatorUtils;

import java.util.ArrayList;

public class IRMaker {
	
	public static IRType makeIRType(String typeName, boolean isArray, int len) {
		IRBasicType basicType = makeIRType(typeName);
		if (isArray) {
			return new IRArrayType(basicType, new IRIntLiteral(Integer.toString(len)));
		} 
		return basicType;
	}

	public static IRBasicType makeIRType(String typeName) {
		return IRBasicType.GetInstance(typeName);
	}

	public static IRVariable makeIRVariable(String name) {
		return new IRVariable(name);
	}
	
	public static IRFieldDecl makeIRFieldDecl(String typeName, String variableName) {
		return makeIRFieldDecl(typeName, false, 0, variableName);
	}
	
	public static IRFieldDecl makeIRFieldDecl(String typeName,
											  boolean isArray,
											  int len,
											  String variableName) {
		IRType type = makeIRType(typeName, isArray, len);
		IRVariable variable = makeIRVariable(variableName);
		return new IRFieldDecl(type, variable);
	}

	public static IRMethodDecl makeIRMethodDecl(String typeName,
												String variableName,
												IRParameterList list,
												IRBlock block) {
		IRType type = makeIRType(typeName, false, 0);
		IRVariable variable = makeIRVariable(variableName);
		if (list == null) {
			list = new IRParameterList(new ArrayList<>());
		}
		if (block == null) {
			block = new IRBlock();
		}
		return new IRMethodDecl(variable, (IRBasicType) type, list, block);
	}

	public static IRImportDecl makeIRImportDecl(String variableName) {
		return new IRImportDecl(makeIRVariable(variableName));
	}

	public static IRMethodDecl makeMainMethod(IRBlock block) {
		return makeIRMethodDecl("void", "main", null, block);
	}

	public static IRIntLiteral makeIRIntLiteral(String value) {
		return makeIRIntLiteral(value, false);
	}

	public static IRIntLiteral makeIRIntLiteral(String value, boolean needConverted) {
		IRIntLiteral ret = new IRIntLiteral(value);
		if (needConverted) {
			ret.setValue(Long.parseLong(value));
		}
		return ret;
	}

	public static IRBoolLiteral makeIRBoolLiteral(String value) {
		return new IRBoolLiteral(value);
	}

	public static IRMethodDecl makeVoidMethod(ArrayList<IRFieldDecl> fields,
											  ArrayList<IRStatement> stmts,
											  String name) {
		IRBlock block = new IRBlock();
		if (fields != null) {
			block.addFieldDecls(fields);
		}
		if (stmts != null) {
			block.addStatements(stmts);
		}
		return new IRMethodDecl(
				new IRVariable(name),
				IRBasicType.VoidType,
				new IRParameterList(new ArrayList<>()),
				block);
	}


	public static IRProgram makeSimpleIRProgram(ArrayList<IRFieldDecl> fields,
												ArrayList<IRStatement> stmts) {
		return makeSimpleIRProgram(fields, stmts, null);
	}

	/*
	 * just main func, all variable is local
	 */
	public static IRProgram makeSimpleIRProgram(ArrayList<IRFieldDecl> fields,
												ArrayList<IRStatement> stmts,
												ArrayList<IRMethodDecl> methods) {
		IRProgram ret = new IRProgram();
		if (methods != null) {
			ret.addMethodDecls(methods);
		}
		IRMethodDecl method = makeVoidMethod(fields, stmts, "main");
		ret.addMethodDecl(method);
		return ret;
	}


	/*
	 * make a binary expression with literal
	 */
	public static IRBinaryOpExpr makeBinaryOpExprWithLiteral(String left, String op, String right) {
		assert OperatorUtils.isBinary(op);
		IRLiteral first;
		IRLiteral second;
		boolean isBoolLiteral = left.equals("true") || left.equals("false");
		if (isBoolLiteral) {
			first = new IRBoolLiteral(left);
			second = new IRBoolLiteral(right);
		} else {
			first = new IRIntLiteral(left);
			second = new IRIntLiteral(right);
		}
		return new IRBinaryOpExpr(first, op, second);
	}
}
