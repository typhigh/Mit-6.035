package edu.mit.compilers.ir.expression;

import edu.mit.compilers.ir.common.IR;
import edu.mit.compilers.ir.common.IRVariable;
import edu.mit.compilers.ir.common.IRVisitor;

import java.util.ArrayList;

public class IRLocation extends IRExpression {
	
	private IRVariable variable;
	private boolean isArrayLocation;
	private IRExpression location;
	
	public IRLocation(IRVariable variable, IRExpression location) {
		super("IRLocation");
		this.variable = variable;
		this.isArrayLocation = location == null ? false : true;
		this.location = location;
	}
	
	public IRLocation(IRVariable variable) {
		super("IRLocation");
		this.variable = variable;
		this.isArrayLocation = false;
		this.location = null;
	}
	
	public IRVariable getVariable() {
		return variable;
	}
	
	public boolean isArrayLocation() {
		return isArrayLocation;
	}

	public IRExpression getLocation() {
		return location;
	}
	
	@Override
	public <T> T accept(IRVisitor<T> visitor) {
		return visitor.visit(this);
	}

	@Override
	public ArrayList<IR> getChildren() {
		ArrayList<IR> ret = new ArrayList<>();
		ret.add(variable);
		if (isArrayLocation) {
			ret.add(location);
		}
		return ret;
	}
}
