package edu.mit.compilers.ir.expression;

import java.util.ArrayList;

import edu.mit.compilers.ir.common.IR;
import edu.mit.compilers.ir.common.IRVisitor;

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
	public ArrayList<IR> getChildren() {
		ArrayList<IR> ret = new ArrayList<IR>();
		ret.add(left);
		ret.add(right);
		return ret;
	}
	
	public String getInfoForShow(String prefix) {
		return prefix + 
				" DebugID: " + getDebugID() + 
				" Tag: " + getTag() + 
				" Op: " + getOperator() + '\n';
	}
}
