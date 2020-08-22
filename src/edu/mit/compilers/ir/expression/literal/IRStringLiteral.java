package edu.mit.compilers.ir.expression.literal;

import antlr.Token;
import edu.mit.compilers.ir.common.IRVisitor;

public class IRStringLiteral extends IRLiteral<String> {

	public IRStringLiteral(Token token) {
		super("IRStringLiteral", token.getText(), token);
	}

	@Override
	public <T> T accept(IRVisitor<T> visitor) {
		return visitor.visit(this);
	}
	
}
