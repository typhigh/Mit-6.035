package edu.mit.compilers.ir.statement;

import edu.mit.compilers.ir.IR;
import edu.mit.compilers.ir.IRVisitor;

public class IRBlock extends IR {
	
	public IRBlock() {
		super("IRBlock");
		// TODO
		
	}

	@Override
	public <T> T accept(IRVisitor<T> visitor) {
		return visitor.visit(this);
	}
	
}
