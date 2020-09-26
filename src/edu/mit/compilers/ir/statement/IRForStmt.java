package edu.mit.compilers.ir.statement;

import edu.mit.compilers.ir.common.IR;
import edu.mit.compilers.ir.common.IRBlock;
import edu.mit.compilers.ir.common.IRVisitor;
import edu.mit.compilers.ir.expression.IRExpression;

import java.util.ArrayList;

public class IRForStmt extends IRStatement {
	
	private IRAssignStmt initializer;
	private IRExpression condition;
	private IRPlusAssignStmt step;
	private IRBlock block;

	public IRForStmt(IRAssignStmt initializer, IRExpression condition, IRPlusAssignStmt step, IRBlock block) {
		super("IRForStmt");
		this.initializer = initializer;
		this.condition = condition;
		this.step = step;
		this.block = block;
	}

	public IRExpression getCondition() {
		return condition;
	}

	@Override
	public <T> T accept(IRVisitor<T> visitor) {
		return visitor.visit(this);
	}

	@Override
	public ArrayList<IR> getChildren() {
		ArrayList<IR> ret = new ArrayList<>();
		ret.add(initializer);
		ret.add(condition);
		ret.add(step);
		ret.add(block);
		return ret;
	}

	@Override
	public IRForStmt clone() throws CloneNotSupportedException {
		IRForStmt clone = (IRForStmt) super.clone();
		if (initializer != null) {
			clone.initializer = initializer.clone();
		}
		if (condition != null) {
			clone.condition = condition.clone();
		}
		if (step != null) {
			clone.step = step.clone();
		}
		if (block != null) {
			clone.block = block.clone();
		}
		return clone;
	}
}
