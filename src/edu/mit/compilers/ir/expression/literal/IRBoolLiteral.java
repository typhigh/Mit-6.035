package edu.mit.compilers.ir.expression.literal;

import antlr.Token;
import edu.mit.compilers.ir.common.IRVisitor;

public class IRBoolLiteral extends IRLiteral<Boolean> {

	public IRBoolLiteral(Token token) {
		super("IRBoolLiteral", parseTokenValue(token), token);
	}

	private static Boolean parseTokenValue(Token token) {
		String text = token.getText();
		System.out.println("."+text+".");
		if (text.equals("true")) {
			return true;
		} else if (text.equals("false")) {
			return false;
		} else {
			throw new RuntimeException("Expected bool literal such as true or false not: "+ text);
		}
	}

	@Override
	public <T> T accept(IRVisitor<T> visitor) {
		return visitor.visit(this);
	}
}
