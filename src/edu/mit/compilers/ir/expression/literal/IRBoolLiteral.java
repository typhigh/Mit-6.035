package edu.mit.compilers.ir.expression.literal;

import edu.mit.compilers.ir.IRVisitor;

public class IRBoolLiteral extends IRLiteral {

	private boolean value; 
	public IRBoolLiteral(boolean value) {
		super("IRBoolLiteral");
		this.value = value;
	}

	public boolean isValue() {
		return value;
	}
	
	@Override
	public <T> T accept(IRVisitor<T> visitor) {
		return visitor.visit(this);
	}

	@Override
	public void showTreeImpl(String prefix, StringBuilder result) {
		// TODO Auto-generated method stub
		
	}

}
