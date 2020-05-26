package edu.mit.compilers.ir.expression.literal;

import edu.mit.compilers.ir.IRVisitor;

public class IRIntLiteral extends IRLiteral {

	private int value; 
	
	public IRIntLiteral(int value) {
		super("IntLiteral");
		// TODO Auto-generated constructor stub
		this.value = value;
	}

	public int getValue() {
		return value;
	}
	
	@Override
	public <T> T accept(IRVisitor<T> visitor) {
		// TODO Auto-generated method stub
		return visitor.visit(this);
	}

	@Override
	public void showTreeImpl(String prefix, StringBuilder result) {
		// TODO Auto-generated method stub
		
	}
	
}
