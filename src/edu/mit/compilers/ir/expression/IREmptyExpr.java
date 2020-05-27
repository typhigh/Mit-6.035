package edu.mit.compilers.ir.expression;

import edu.mit.compilers.ir.IRVisitor;

public class IREmptyExpr extends IRExpression {

	public IREmptyExpr() {
		super("IREmptyExpr");
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
	}

}
