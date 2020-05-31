package edu.mit.compilers.ir.statement;

import java.util.ArrayList;

import edu.mit.compilers.ir.IR;
import edu.mit.compilers.ir.IRVisitor;
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
	public void showTreeImpl(String prefix, StringBuilder result) {
		String info = prefix + 
				" DebugID: " + getDebugID() + 
				" Tag: " + getTag() + '\n';
		result.append(info);
		
		initializer.showTreeImpl(prefix + " ", result);
		condition.showTreeImpl(prefix + " ", result);
		step.showTreeImpl(prefix + " ", result);
		block.showTreeImpl(prefix + " ", result);
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
