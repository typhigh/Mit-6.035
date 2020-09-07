package edu.mit.compilers.ir.statement;

import edu.mit.compilers.ir.common.IR;
import edu.mit.compilers.ir.common.IRVisitor;

import java.util.ArrayList;

public class IRBreakStmt extends IRStatement {

	// filled by BreakContinueRule
	private IRStatement loopStmt;

	public IRBreakStmt() {
		super("IRBreakStmt");
	}

	public IRStatement getLoopStmt() {
		return loopStmt;
	}

	public void setLoopStmt(IRStatement loopStmt) {
		this.loopStmt = loopStmt;
	}

	@Override
	public <T> T accept(IRVisitor<T> visitor) {
		return visitor.visit(this);
	}

	@Override
	public ArrayList<IR> getChildren() {
		return getEmptyChildren();
	}

}
