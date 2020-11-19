package edu.mit.compilers.ir.statement;

import edu.mit.compilers.ir.common.IR;
import edu.mit.compilers.ir.common.IRBlock;
import edu.mit.compilers.ir.common.IRVisitor;
import edu.mit.compilers.ir.expression.IRExpression;

import java.util.ArrayList;

public class IRWhileStmt extends IRLoopStmt {

	public IRWhileStmt(IRExpression condition, IRBlock block) {
		super("IRWhileStmt", condition, block);
	}

	@Override
	public <T> T accept(IRVisitor<T> visitor) throws CloneNotSupportedException {
		return visitor.visit(this);
	}

	@Override
	public ArrayList<IR> getChildren() {
		ArrayList<IR> ret = new ArrayList<>();
		ret.add(getCondition());
		ret.add(getBlock());
		return ret;
	}

	@Override
	public IRWhileStmt clone() throws CloneNotSupportedException {
		return (IRWhileStmt) super.clone();
	}
}
