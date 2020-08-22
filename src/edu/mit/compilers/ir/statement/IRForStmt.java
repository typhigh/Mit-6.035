package edu.mit.compilers.ir.statement;

import java.util.ArrayList;

import edu.mit.compilers.ir.common.IR;
import edu.mit.compilers.ir.common.IRVisitor;
import edu.mit.compilers.ir.expression.IRExpression;

public class IRForStmt extends IRStatement {
	
	private IRAssignStmt initializer;
	private IRExpression condition;
	private IRAssignStmt step;
	private IRBlock block;

	public IRForStmt(IRAssignStmt initializer, IRExpression condition, IRAssignStmt step, IRBlock block) {
		// TODO Auto-generated constructor stub
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
		ArrayList<IR> ret = new ArrayList<IR>();
		ret.add(initializer);
		ret.add(condition);
		ret.add(step);
		ret.add(block);
		return ret;
	}

}
