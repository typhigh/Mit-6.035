package edu.mit.compilers.ir.expression.literal;

import antlr.Token;
import edu.mit.compilers.ir.common.IRVisitor;

public class IRIntLiteral extends IRLiteral<Long> {

	public IRIntLiteral(String literalValue) {
		super("IRIntLiteral", literalValue);
	}

	public IRIntLiteral(Token token) {
		super("IRIntLiteral", token);
	}

	@Override
	public <T> T accept(IRVisitor<T> visitor) {
		return visitor.visit(this);
	}

	@Override
	public IRIntLiteral clone() throws CloneNotSupportedException {
		return (IRIntLiteral) super.clone();
	}
}
