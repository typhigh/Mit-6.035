package edu.mit.compilers.ir.statement;

import java.util.ArrayList;

import edu.mit.compilers.ir.IR;
import edu.mit.compilers.ir.IRVisitor;
import edu.mit.compilers.ir.expression.IRExpression;

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
	public void showTreeImpl(String prefix, StringBuilder result) {
		String info = prefix + 
				" DebugID: " + getDebugID() + 
				" Tag: " + getTag() + '\n';
		result.append(info);
		condition.showTreeImpl(prefix + " ", result);
		block.showTreeImpl(prefix + " ", result);
	}

	@Override
	public ArrayList<IR> getChildren() {
		ArrayList<IR> ret = new ArrayList<IR>();
		ret.add(condition);
		ret.add(block);
		return ret;
	}
}
