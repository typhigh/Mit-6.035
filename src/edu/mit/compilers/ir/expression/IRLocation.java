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
		setLine(variable.getLine());
	}
	
	public IRLocation(IRVariable variable) {
		this(variable, null);
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
	public <T> T accept(IRVisitor<T> visitor) throws CloneNotSupportedException {
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

	@Override
	public IRLocation clone() throws CloneNotSupportedException {
		IRLocation clone = (IRLocation) super.clone();
		if (variable != null) {
			clone.variable = variable.clone();
		}
		if (location != null) {
			clone.location = location.clone();
		}
		return clone;
	}
}
