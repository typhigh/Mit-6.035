package edu.mit.compilers.ir.statement;

import edu.mit.compilers.ir.common.IR;

public class IRStatement extends IR {

	private IRStatement nextStmt = null;

	public IRStatement getNextStmt() {
		return nextStmt;
	}

	public void setNextStmt(IRStatement nextStmt) {
		this.nextStmt = nextStmt;
	}

	public IRStatement(String name) {
		super(name);
	}

	@Override
	public IRStatement clone() throws CloneNotSupportedException {
		return (IRStatement) super.clone();
	}
}
