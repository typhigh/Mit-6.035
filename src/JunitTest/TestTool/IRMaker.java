package JunitTest.TestTool;

import antlr.Token;
import edu.mit.compilers.ir.common.IRVariable;
import edu.mit.compilers.ir.decl.IRFieldDecl;
import edu.mit.compilers.ir.decl.IRMemberDecl;
import edu.mit.compilers.ir.decl.IRMethodDecl;
import edu.mit.compilers.ir.expression.literal.IRIntLiteral;
import edu.mit.compilers.ir.type.IRArrayType;
import edu.mit.compilers.ir.type.IRBasicType;
import edu.mit.compilers.ir.type.IRType;

public class IRMaker {

	private static Token makeToken(String text) {
		return new FakeToken(0, text);
	}
	
	public static IRType makeIRType(String typeName, boolean isArray, int len) {
		IRBasicType basicType = IRBasicType.GetInstance(typeName);
		if (isArray) {
			return new IRArrayType(basicType, new IRIntLiteral(makeToken(Integer.toString(len))));
		} 
		return basicType;
	}
	
	public static IRVariable makeIRVariable(String name) {
		return new IRVariable(makeToken(name));
	}
	
	public static IRFieldDecl makeIRFieldDecl(String typeName, String variableName) {
		return makeIRFieldDecl(typeName, false, 0, variableName);
	}
	
	public static IRFieldDecl makeIRFieldDecl(String typeName, boolean isArray, int len, String variableName) {
		IRType type = makeIRType(typeName, isArray, len);
		IRVariable variable = makeIRVariable(variableName);
		return new IRFieldDecl(type, variable);
	}

	public static IRMemberDecl makeIRMethodDecl(String typeName, String variableName) {
		IRType type = makeIRType(typeName, false, 0);
		IRVariable variable = makeIRVariable(variableName);
		return new IRMethodDecl(variable, (IRBasicType) type, null, null);


	}
}
