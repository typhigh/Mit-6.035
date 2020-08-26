package edu.mit.compilers.ir.statement;

import edu.mit.compilers.ir.common.IR;
import edu.mit.compilers.ir.common.IRVisitor;
import edu.mit.compilers.ir.expression.IRExpression;

import java.util.ArrayList;

public class IRIfStmt extends IRStatement{
	
	private IRExpression condition;
	private IRBlock ifBlock;
	private IRBlock elseBlock;
	
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
		return visitor.visit(this);
	}

	@Override
	public ArrayList<IR> getChildren() {
		ArrayList<IR> ret = new ArrayList<>();
		ret.add(condition);
		ret.add(ifBlock);
		if (elseBlock != null) {
			ret.add(elseBlock);
		}
		return ret;
	}

}
