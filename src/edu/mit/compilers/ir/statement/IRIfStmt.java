package edu.mit.compilers.ir.statement;

import edu.mit.compilers.ir.IRVisitor;
import edu.mit.compilers.ir.expression.IRExpression;

public class IRIfStmt extends IRStatement{
	
	private final IRExpression condition;
	private final IRBlock ifBlock;
	private final IRBlock elseBlock;
	
	public IRIfStmt(IRExpression condition, IRBlock ifBlock, IRBlock elseBlock) {
		super("IRIfStmt");
		this.condition = condition;
		this.ifBlock = ifBlock;
		this.elseBlock = elseBlock;
	}

	public IRIfStmt(IRExpression condition, IRBlock ifBlock) {
		this(condition, ifBlock, null);
	}

	@Override
	public <T> T accept(IRVisitor<T> visitor) {
		// TODO Auto-generated method stub
		return visitor.visit(this);
	}

	@Override
	public void showTreeImpl(String prefix, StringBuilder result) {
		// TODO Auto-generated method stub
		String info = prefix + 
				" DebugID: " + getDebugID() + 
				" Tag: " + getTag() + '\n';
		result.append(info);
		condition.showTreeImpl(prefix + " ", result);
		ifBlock.showTreeImpl(prefix + " ", result);
		if (elseBlock != null) {
			elseBlock.showTreeImpl(prefix + " ", result);
		}
	}

}
