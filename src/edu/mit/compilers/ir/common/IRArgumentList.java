package edu.mit.compilers.ir.common;

import java.util.ArrayList;

import edu.mit.compilers.ir.expression.IRExpression;

public class IRArgumentList extends IR {

	private ArrayList<IRExpression> argList;
	public IRArgumentList(ArrayList<IRExpression> argList) {
		super("IRArgumentList");
		this.argList = argList;
	}

	public ArrayList<IRExpression> getArgs() {
		return argList;
	}

	@Override
	public ArrayList<IR> getChildren() {
		ArrayList<IR> ret = new ArrayList<IR>(argList);
		return ret;
	}

	@Override
	public <T> T accept(IRVisitor<T> visitor) {
		return visitor.visit(this);
	}

}
