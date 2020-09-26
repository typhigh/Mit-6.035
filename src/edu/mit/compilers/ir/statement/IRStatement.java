package edu.mit.compilers.ir.statement;

import edu.mit.compilers.ir.common.IR;

public class IRStatement extends IR {

	public IRStatement(String name) {
		super(name);
	}

	@Override
	public IRStatement clone() throws CloneNotSupportedException {
		return (IRStatement) super.clone();
	}
}
