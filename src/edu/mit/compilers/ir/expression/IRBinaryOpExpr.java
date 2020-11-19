package edu.mit.compilers.ir.expression;

import edu.mit.compilers.ir.common.IR;
import edu.mit.compilers.ir.common.IRVisitor;
import edu.mit.compilers.utils.StringInfo;

import java.util.ArrayList;

public class IRBinaryOpExpr extends IRExpression {

	private IRExpression left;
	private IRExpression right;
	private String operator;

	public IRBinaryOpExpr(IRExpression left, String operator, IRExpression right) {
		super("IRBinaryOpExpr");
		this.left = left;
		this.operator = operator;
		this.right = right;
		setLine(left.getLine());
	}

	public IRExpression getLeft() {
		return left;
	}

	public IRExpression getRight() {
		return right;
	}


	public String getOperator() {
		return operator;
	}
	
	@Override
	public <T> T accept(IRVisitor<T> visitor) throws CloneNotSupportedException {
		return visitor.visit(this);
	}

	@Override
	public ArrayList<IR> getChildren() {
		ArrayList<IR> ret = new ArrayList<>();
		ret.add(left);
		ret.add(right);
		return ret;
	}

	@Override
	public StringInfo getInfoForShow(String prefix) {
		return super.getInfoForShow(prefix).addInfo("Op: " + getOperator());
	}

	@Override
	public IRBinaryOpExpr clone() throws CloneNotSupportedException {
		IRBinaryOpExpr clone = (IRBinaryOpExpr) super.clone();
		if (left != null) {
			clone.left = left.clone();
		}
		if (right != null) {
			clone.right = right.clone();
		}
		return clone;
	}
}
