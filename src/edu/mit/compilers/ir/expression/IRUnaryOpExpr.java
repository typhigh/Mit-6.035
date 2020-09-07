package edu.mit.compilers.ir.expression;

import edu.mit.compilers.ir.common.IR;
import edu.mit.compilers.ir.common.IRVisitor;
import edu.mit.compilers.utils.StringInfo;

import java.util.ArrayList;

public class IRUnaryOpExpr extends IRExpression {

	private String operator;
	private IRExpression right;
	
	public IRUnaryOpExpr(String operator, IRExpression right) {
		super("IRUnaryOpExpr");
		this.operator = operator;
		this.right = right;
		setLine(right.getLine());
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
	public ArrayList<IR> getChildren() {
		ArrayList<IR> ret = new ArrayList<>();
		ret.add(right);
		return ret;
	}
	
	public StringInfo getInfoForShow(String prefix) {
		return super.getInfoForShow(prefix).addInfo("Op: " + getOperator());
	}
}
