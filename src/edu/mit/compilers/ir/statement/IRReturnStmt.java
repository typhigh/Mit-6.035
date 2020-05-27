package edu.mit.compilers.ir.statement;

import edu.mit.compilers.ir.IRVisitor;
import edu.mit.compilers.ir.expression.IRExpression;

public class IRReturnStmt extends IRStatement {

	private IRExpression expr;
	public IRReturnStmt(IRExpression expr) {
		super("IRReturnStmt");
		this.expr = expr;
	}

	public IRExpression getExpr() {
		return expr;
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
		expr.showTreeImpl(prefix + " ", result);
	}

}
