package edu.mit.compilers.ir.statement;

import edu.mit.compilers.ir.common.IR;
import edu.mit.compilers.ir.common.IRVisitor;
import edu.mit.compilers.ir.expression.IRExpression;

import java.util.ArrayList;

public class IRWhileStmt extends IRStatement {

	private IRExpression condition;
	private IRBlock block;
	public IRWhileStmt(IRExpression condition, IRBlock block) {
		super("IRWhileStmt");
		this.condition = condition;
		this.block = block;
	}

	public IRExpression getCondition() {
		return condition;
	}

	public IRBlock getBlock() {
		return block;
	}

	@Override
	public <T> T accept(IRVisitor<T> visitor) {
		return visitor.visit(this);
	}

	@Override
	public ArrayList<IR> getChildren() {
		ArrayList<IR> ret = new ArrayList<>();
		ret.add(condition);
		ret.add(block);
		return ret;
	}
}
