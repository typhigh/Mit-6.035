package edu.mit.compilers.ir.expression.literal;

import antlr.Token;
import edu.mit.compilers.ir.IRVisitor;

public class IRIntLiteral extends IRLiteral<Integer> {
	
	public IRIntLiteral(Token token) {
		super("IntLiteral", parseTokenValue(token), token);
	}
	
	private static Integer parseTokenValue(Token token) {
		return Integer.valueOf(token.getText());
	}

	@Override
	public <T> T accept(IRVisitor<T> visitor) {
		return visitor.visit(this);
	}

}
