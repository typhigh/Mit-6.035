package JunitTest.TestTool;

import edu.mit.compilers.ir.common.IRBlock;
import edu.mit.compilers.ir.common.IRParameterList;
import edu.mit.compilers.ir.common.IRProgram;
import edu.mit.compilers.ir.common.IRVariable;
import edu.mit.compilers.ir.decl.IRFieldDecl;
import edu.mit.compilers.ir.decl.IRImportDecl;
import edu.mit.compilers.ir.decl.IRMethodDecl;
import edu.mit.compilers.ir.expression.literal.IRIntLiteral;
import edu.mit.compilers.ir.statement.IRStatement;
import edu.mit.compilers.ir.type.IRArrayType;
import edu.mit.compilers.ir.type.IRBasicType;
import edu.mit.compilers.ir.type.IRType;

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
	
	public static IRFieldDecl makeIRFieldDecl(String typeName, boolean isArray, int len, String variableName) {
		IRType type = makeIRType(typeName, isArray, len);
		IRVariable variable = makeIRVariable(variableName);
		return new IRFieldDecl(type, variable);
	}

	public static IRMethodDecl makeIRMethodDecl(String typeName, String variableName, IRParameterList list, IRBlock block) {
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

	/*
	 * just main func, all variable is local
	 */
	public static IRProgram makeSimpleIRProgram(ArrayList<IRFieldDecl> fields, ArrayList<IRStatement> stmts) {
		IRProgram ret = new IRProgram();
		IRBlock block = new IRBlock();
		block.addFieldDecls(fields);
		block.addStatements(stmts);
		ret.addMethodDecl(new IRMethodDecl(new IRVariable("main"), IRBasicType.VoidType,
				new IRParameterList(new ArrayList<>()), block));
		return ret;
	}
}
