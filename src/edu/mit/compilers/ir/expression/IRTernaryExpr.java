package edu.mit.compilers.ir.expression;

import edu.mit.compilers.ir.IRVisitor;

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
	public void showTreeImpl(String prefix, StringBuilder result) {
		String info = prefix + 
				" DebugID: " + getDebugID() +
				" Tag: " + getTag() + '\n';
		result.append(info);
		condition.showTreeImpl(prefix + " ", result);
		first.showTreeImpl(prefix + " ", result);
		second.showTreeImpl(prefix + " ", result);
	}

}
