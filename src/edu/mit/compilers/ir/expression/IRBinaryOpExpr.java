package edu.mit.compilers.ir.expression;

import java.util.ArrayList;

import edu.mit.compilers.ir.IR;
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


	public String getOperator() {
		return operator;
	}
	
	@Override
	public <T> T accept(IRVisitor<T> visitor) {
		return visitor.visit(this);
	}

	@Override
	public void showTreeImpl(String prefix, StringBuilder result) {
		String info = prefix + 
				" DebugID: " + getDebugID() +
				" Tag: " + getTag() + 
				" Operator: " + getOperator() + "\n";
		result.append(info);
		left.showTreeImpl(prefix + " ", result);
		right.showTreeImpl(prefix + " ", result);
	}

	@Override
	public ArrayList<IR> getChildren() {
		ArrayList<IR> ret = new ArrayList<IR>();
		ret.add(left);
		ret.add(right);
		return ret;
	}
	
}
