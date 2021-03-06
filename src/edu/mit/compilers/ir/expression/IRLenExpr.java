package edu.mit.compilers.ir.expression;

import edu.mit.compilers.ir.common.IR;
import edu.mit.compilers.ir.common.IRVariable;
import edu.mit.compilers.ir.common.IRVisitor;

import java.util.ArrayList;


public class IRLenExpr extends IRExpression {
	
	private IRVariable variable;
	
	public IRLenExpr(IRVariable variable) {
		super("IRLenExpr");
		this.variable = variable;
		setLine(variable.getLine());
	}


	public IRVariable getVariable() {
		return variable;
	}
	
	
	@Override
	public <T> T accept(IRVisitor<T> visitor) {
		return visitor.visit(this);
	}

	@Override
	public ArrayList<IR> getChildren() {
		ArrayList<IR> ret = new ArrayList<>();
		ret.add(variable);
		return ret;
	}

	@Override
	public IRExpression clone() throws CloneNotSupportedException{
		IRLenExpr clone = (IRLenExpr) super.clone();
		if (variable != null) {
			clone.variable = variable.clone();
		}
		return clone;
	}
}
