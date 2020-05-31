package edu.mit.compilers.ir.expression;

import java.util.ArrayList;

import edu.mit.compilers.ir.IR;
import edu.mit.compilers.ir.IRVisitor;

public class IRUnaryOpExpr extends IRExpression {

	private String operator;
	private IRExpression right;
	
	public IRUnaryOpExpr(String operator, IRExpression right) {
		super("IRUnaryOpExpr");
		// TODO Auto-generated constructor stub
		this.operator = operator;
		this.right = right;
	}

	public String getOperator() {
		return operator;
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
		String info = prefix + 
				" DebugID: " + getDebugID() +
				" Tag: " + getTag() + 
				" Operator: " + getOperator() + "\n";
		result.append(info);
		right.showTreeImpl(prefix + " ", result);
	}

	@Override
	public ArrayList<IR> getChildren() {
		ArrayList<IR> ret = new ArrayList<IR>();
		ret.add(right);
		return ret;
	}
	
}
