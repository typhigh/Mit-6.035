package edu.mit.compilers.ir.statement;

import edu.mit.compilers.ir.common.IR;
import edu.mit.compilers.ir.common.IRBlock;
import edu.mit.compilers.ir.common.IRVisitor;
import edu.mit.compilers.ir.expression.IRExpression;

import java.util.ArrayList;

public class IRForStmt extends IRLoopStmt {
	
	private IRAssignStmt initializer;
	private IRPlusAssignStmt step;

	public IRForStmt(IRAssignStmt initializer, IRExpression condition, IRPlusAssignStmt step, IRBlock block) {
		super("IRForStmt", condition, block);
		this.initializer = initializer;
		this.step = step;
	}

	public IRAssignStmt getInitializer() {
		return initializer;
	}

	public IRPlusAssignStmt getStep() {
		return step;
	}

	@Override
	public <T> T accept(IRVisitor<T> visitor) throws CloneNotSupportedException {
		return visitor.visit(this);
	}

	@Override
	public ArrayList<IR> getChildren() {
		ArrayList<IR> ret = new ArrayList<>();
		ret.add(getInitializer());
		ret.add(getCondition());
		ret.add(getStep());
		ret.add(getBlock());
		return ret;
	}

	@Override
	public IRForStmt clone() throws CloneNotSupportedException {
		IRForStmt clone = (IRForStmt) super.clone();

		// IRForStmt clone condition, block, initializer, step
		// because initializer don't contain field declare so it is right
		if (initializer != null) {
			clone.initializer = initializer.clone();
		}
		if (step != null) {
			clone.step = step.clone();
		}
		return clone;
	}
}
