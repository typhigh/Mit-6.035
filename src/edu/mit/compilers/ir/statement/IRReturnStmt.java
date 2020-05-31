package edu.mit.compilers.ir.statement;

import java.util.ArrayList;

import edu.mit.compilers.ir.IR;
import edu.mit.compilers.ir.IRVisitor;
import edu.mit.compilers.ir.expression.IRExpression;

public class IRReturnStmt extends IRStatement {

	private IRExpression expr;
	public IRReturnStmt(IRExpression expr) {
		super("IRReturnStmt");
		assert(expr != null);
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

	@Override
	public ArrayList<IR> getChildren() {
		ArrayList<IR> ret = new ArrayList<IR>();
		ret.add(expr);
		return ret;
	}

}
