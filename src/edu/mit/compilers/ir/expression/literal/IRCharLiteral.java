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
			if (line == "\\n") {
				value = '\n';
			} else if (line == "\\r") {
				value = '\r';
			} else if (line == "\\t") {
				value = '\t';
			} else {
				value = line.charAt(1);
			} 
		}
		return value;
	}

	@Override
	public <T> T accept(IRVisitor<T> visitor) {
		return visitor.visit(this);
	}
	
}
