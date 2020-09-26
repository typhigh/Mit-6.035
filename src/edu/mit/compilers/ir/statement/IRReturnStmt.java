package edu.mit.compilers.ir.statement;

import edu.mit.compilers.ir.common.IR;
import edu.mit.compilers.ir.common.IRVisitor;
import edu.mit.compilers.ir.expression.IRExpression;

import java.util.ArrayList;

public class IRReturnStmt extends IRStatement {

	private IRExpression expr;
	public IRReturnStmt(IRExpression expr) {
		super("IRReturnStmt");
		this.expr = expr;
		assert(expr != null);
	}

	public IRExpression getExpr() {
		return expr;
	}
	
	@Override
	public <T> T accept(IRVisitor<T> visitor) {
		return visitor.visit(this);
	}

	@Override
	public ArrayList<IR> getChildren() {
		ArrayList<IR> ret = new ArrayList<>();
		ret.add(expr);
		return ret;
	}

	@Override
	public IRReturnStmt clone() throws CloneNotSupportedException {
		IRReturnStmt clone = (IRReturnStmt) super.clone();
		if (expr != null) {
			clone.expr = expr.clone();
		}
		return clone;
	}
}
