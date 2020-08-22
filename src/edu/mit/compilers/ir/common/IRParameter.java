package edu.mit.compilers.ir.common;

import java.util.ArrayList;

import edu.mit.compilers.ir.type.IRType;

public class IRParameter extends IR {

	private IRType type;
	private IRVariable variable;
	
	public IRParameter(IRType type, IRVariable variable) {
		super("IRFormalPara");
		this.type = type;
		this.variable = variable;
	}

	public IRType getType() {
		return type;
	}

	public IRVariable getVariable() {
		return variable;
	}

	@Override
	public ArrayList<IR> getChildren() {
		ArrayList<IR> ret = new ArrayList<IR>();
		ret.add(type);
		ret.add(variable);
		return ret;
	}

	@Override
	public <T> T accept(IRVisitor<T> visitor) {
		return visitor.visit(this);
	}
}
