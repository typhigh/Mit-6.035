package edu.mit.compilers.ir.expression;

import edu.mit.compilers.ir.IRVisitor;
import edu.mit.compilers.semantic.Identifier;

public class IRLenExpr extends IRExpression {
	
	private Identifier identifier;
	
	public IRLenExpr(Identifier identifier) {
		super("IRLenExpr");
		// TODO Auto-generated constructor stub
		this.identifier = identifier;
	}

	@Override
	public <T> T accept(IRVisitor<T> visitor) {
		return visitor.visit(this);
	}

	@Override
	public void showTreeImpl(String prefix, StringBuilder result) {
		// TODO Auto-generated method stub
		
	}

	public Identifier getIdentifier() {
		return identifier;
	}
	
	
}
