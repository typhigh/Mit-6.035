package JunitTest.TestTool;

import antlr.Token;
import edu.mit.compilers.ir.common.IRVariable;
import edu.mit.compilers.ir.decl.IRFieldDecl;
import edu.mit.compilers.ir.expression.literal.IRIntLiteral;
import edu.mit.compilers.ir.type.IRArrayType;
import edu.mit.compilers.ir.type.IRBasicType;
import edu.mit.compilers.ir.type.IRType;

public class IRMaker {

	private static Token MakeToken(String text) {
		return new FakeToken(0, text);
	}
	
	public static IRType MakeIRType(String typeName, boolean isArray, int len) {
		IRBasicType basicType = IRBasicType.GetInstance(typeName);
		if (isArray) {
			return new IRArrayType(basicType, new IRIntLiteral(MakeToken(Integer.toString(len))));
		} 
		return basicType;
	}
	
	public static IRVariable MakeIRVariable(String name) {
		return new IRVariable(MakeToken(name));
	}
	
	public static IRFieldDecl MakeIRFieldDecl(String typeName, String variableName) {
		return MakeIRFieldDecl(typeName, false, 0, variableName);
	}
	
	public static IRFieldDecl MakeIRFieldDecl(String typeName, boolean isArray, int len, String variableName) {
		IRType type = MakeIRType(typeName, isArray, len);
		IRVariable variable = MakeIRVariable(variableName);
		return new IRFieldDecl(type, variable);
	}

}
