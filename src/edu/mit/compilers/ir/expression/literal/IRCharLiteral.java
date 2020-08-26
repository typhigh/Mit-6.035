package edu.mit.compilers.ir.expression.literal;

import antlr.Token;
import edu.mit.compilers.ir.common.IRVisitor;

public class IRCharLiteral extends IRLiteral<Character> {

	public IRCharLiteral(Token token) {
		super("IRCharLiteral", parseTokenValue(token), token);
	}

	private static Character parseTokenValue(Token token) {
		String line = token.getText();
		char value = line.charAt(0);
		if (value == '\\') {
			switch (line) {
				case "\\n":
					value = '\n';
					break;
				case "\\r":
					value = '\r';
					break;
				case "\\t":
					value = '\t';
					break;
				default:
					value = line.charAt(1);
					break;
			}
		}
		return value;
	}

	@Override
	public <T> T accept(IRVisitor<T> visitor) {
		return visitor.visit(this);
	}
	
}
