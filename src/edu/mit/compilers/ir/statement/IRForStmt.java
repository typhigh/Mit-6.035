package edu.mit.compilers.ir.statement;

import edu.mit.compilers.ir.common.IR;
import edu.mit.compilers.ir.common.IRBlock;
import edu.mit.compilers.ir.common.IRVisitor;
import edu.mit.compilers.ir.expression.IRExpression;

import java.util.ArrayList;

public class IRForStmt extends IRStatement {
	
	private IRAssignStmt initializer;
	private IRExpression condition;
	private IRAssignStmt step;
	private IRBlock block;

	public IRForStmt(IRAssignStmt initializer, IRExpression condition, IRAssignStmt step, IRBlock block) {
		super("IRForStmt");
		this.initializer = initializer;
		this.condition = condition;
		this.step = step;
		this.block = block;
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

}
