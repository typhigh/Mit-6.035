package edu.mit.compilers.ir.statement;

import java.util.ArrayList;

import edu.mit.compilers.ir.common.IR;
import edu.mit.compilers.ir.common.IRVisitor;
import edu.mit.compilers.ir.expression.IRExpression;

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
		ArrayList<IR> ret = new ArrayList<IR>();
		ret.add(expr);
		return ret;
	}

}
