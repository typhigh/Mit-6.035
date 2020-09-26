package edu.mit.compilers.ir.expression.literal;

import antlr.Token;
import edu.mit.compilers.ir.common.IRVisitor;

public class IRBoolLiteral extends IRLiteral<Boolean> {

	public IRBoolLiteral(Token token) {
		super("IRBoolLiteral", token);
	}

	@Override
	public <T> T accept(IRVisitor<T> visitor) {
		return visitor.visit(this);
	}

	@Override
	public IRBoolLiteral clone() throws CloneNotSupportedException {
		return (IRBoolLiteral) super.clone();
	}
}
