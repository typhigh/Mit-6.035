package edu.mit.compilers.ir.expression;

import edu.mit.compilers.ir.common.IR;
import edu.mit.compilers.ir.common.IRVisitor;

import java.util.ArrayList;

public class IREmptyExpr extends IRExpression {

	public IREmptyExpr() {
		super("IREmptyExpr");
	}

	@Override
	public <T> T accept(IRVisitor<T> visitor) {
		return visitor.visit(this);
	}

	@Override
	public ArrayList<IR> getChildren() {
		return getEmptyChildren();
	}
}
