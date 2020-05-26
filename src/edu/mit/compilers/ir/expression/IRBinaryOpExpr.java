package edu.mit.compilers.ir.expression;

import edu.mit.compilers.ir.IRVisitor;

public class IRBinaryOpExpr extends IRExpression {

	private IRExpression left;
	private IRExpression right;
	private String operator;

	public IRBinaryOpExpr(IRExpression left, String operator, IRExpression right) {
		super("IRBinaryOpExpr");
		this.left = left;
		this.operator = operator;
		this.right = right;
	}

	public IRExpression getLeft() {
		return left;
	}

	public IRExpression getRight() {
		return right;
	}

	@Override
	public <T> T accept(IRVisitor<T> visitor) {
		return visitor.visit(this);
	}

	@Override
	public void showTreeImpl(String prefix, StringBuilder result) {
		// TODO Auto-generated method stub
		
	}

	public String getOperator() {
		return operator;
	}
	
}
