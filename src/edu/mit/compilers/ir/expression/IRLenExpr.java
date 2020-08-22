package edu.mit.compilers.ir.expression;

import java.util.ArrayList;

import edu.mit.compilers.ir.common.IR;
import edu.mit.compilers.ir.common.IRVariable;
import edu.mit.compilers.ir.common.IRVisitor;


public class IRLenExpr extends IRExpression {
	
	private IRVariable variable;
	
	public IRLenExpr(IRVariable variable) {
		super("IRLenExpr");
		this.variable = variable;
	}


	public IRVariable getvariable() {
		return variable;
	}
	
	
	@Override
	public <T> T accept(IRVisitor<T> visitor) {
		return visitor.visit(this);
	}

	@Override
	public ArrayList<IR> getChildren() {
		ArrayList<IR> ret = new ArrayList<IR>();
		ret.add(variable);
		return ret;
	}
	
}
