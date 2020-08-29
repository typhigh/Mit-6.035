package edu.mit.compilers.ir.common;

import edu.mit.compilers.ir.expression.IRExpression;

import java.util.ArrayList;

public class IRArgumentList extends IR {

	private ArrayList<IRExpression> argList;
	public IRArgumentList(ArrayList<IRExpression> argList) {
		super("IRArgumentList");
		this.argList = argList;
	}

	public ArrayList<IRExpression> getArgList() {
		return argList;
	}

	public IRExpression get(int index) {
		return getArgList().get(index);
	}

	public int size() {
		return getArgList().size();
	}

	@Override
	public ArrayList<IR> getChildren() {
		return new ArrayList<>(argList);
	}

	@Override
	public <T> T accept(IRVisitor<T> visitor) {
		return visitor.visit(this);
	}

}
