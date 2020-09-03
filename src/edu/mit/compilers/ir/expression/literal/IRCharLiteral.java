package edu.mit.compilers.ir.expression.literal;

import antlr.Token;
import edu.mit.compilers.ir.common.IRVisitor;

public class IRCharLiteral extends IRLiteral<Character> {

	public IRCharLiteral(Token token) {
		super("IRCharLiteral", token);
	}

	@Override
	public <T> T accept(IRVisitor<T> visitor) {
		return visitor.visit(this);
	}
}
