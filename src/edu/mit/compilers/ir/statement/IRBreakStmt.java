package edu.mit.compilers.ir.statement;

import edu.mit.compilers.ir.IRVisitor;

public class IRBreakStmt extends IRStatement {

	public IRBreakStmt() {
		super("IRBreakStmt");
	}

	@Override
	public <T> T accept(IRVisitor<T> visitor) {
		return visitor.visit(this);
	}

	@Override
	public void showTreeImpl(String prefix, StringBuilder result) {
		String info = prefix + 
				" Tag: " + getTag() + '\n';
		result.append(info);
	}

}
