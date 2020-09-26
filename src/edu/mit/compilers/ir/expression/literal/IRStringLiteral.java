package edu.mit.compilers.ir.expression.literal;

import antlr.Token;
import edu.mit.compilers.ir.common.IRVisitor;

public class IRStringLiteral extends IRLiteral<String> {

	public IRStringLiteral(Token token) {
		super("IRStringLiteral", token);
		setValue(getLiteralValue());
	}

	@Override
	public <T> T accept(IRVisitor<T> visitor) {
		return visitor.visit(this);
	}

	@Override
	public IRStringLiteral clone() throws CloneNotSupportedException {
		return (IRStringLiteral) super.clone();
	}
}
