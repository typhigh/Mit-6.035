package edu.mit.compilers.ir.expression.literal;

import antlr.Token;
import edu.mit.compilers.ir.common.IRVisitor;

public class IRIntLiteral extends IRLiteral<Integer> {
	
	public IRIntLiteral(Token token) {
		super("IRIntLiteral", token);
	}

	@Override
	public <T> T accept(IRVisitor<T> visitor) {
		return visitor.visit(this);
	}
}
