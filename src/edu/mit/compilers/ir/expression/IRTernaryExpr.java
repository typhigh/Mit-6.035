package edu.mit.compilers.ir.expression;

import java.util.ArrayList;

import edu.mit.compilers.ir.common.IR;
import edu.mit.compilers.ir.common.IRVisitor;

/*
 * Ternary expr: expr1 ? expr2 : expr3
 */
public class IRTernaryExpr extends IRExpression {
	
	private IRExpression condition;
	private IRExpression first;
	private IRExpression second;
	
	public IRTernaryExpr(IRExpression condition, IRExpression first, IRExpression second) {
		super("IRTernaryExpr");
		this.condition = condition;
		this.first = first;
		this.second = second;
	}

	public IRExpression getCondition() {
		return condition;
	}

	public IRExpression getFirst() {
		return first;
	}

	public IRExpression getSecond() {
		return second;
	}
	
	@Override
	public <T> T accept(IRVisitor<T> visitor) {
		return visitor.visit(this);
	}

	@Override
	public ArrayList<IR> getChildren() {
		ArrayList<IR> ret = new ArrayList<IR>();
		ret.add(condition);
		ret.add(first);
		ret.add(second);
		return ret;
	}

}
