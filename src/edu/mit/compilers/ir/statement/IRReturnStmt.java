package edu.mit.compilers.ir.statement;

import edu.mit.compilers.ir.IRVisitor;

public class IRReturnStmt extends IRStatement {

	public IRReturnStmt(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public <T> T accept(IRVisitor<T> visitor) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void showTreeImpl(String prefix, StringBuilder result) {
		// TODO Auto-generated method stub
		
	}

}
