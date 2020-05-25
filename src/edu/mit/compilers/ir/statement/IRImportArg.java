package edu.mit.compilers.ir.statement;

import edu.mit.compilers.ir.IR;
import edu.mit.compilers.ir.IRVisitor;

public class IRImportArg extends IR {

	public IRImportArg() {
		super("IRImportArg");
		// TODO
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
