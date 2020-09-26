package edu.mit.compilers.ir.expression;

import edu.mit.compilers.ir.common.IR;
import edu.mit.compilers.ir.common.IRArgumentList;
import edu.mit.compilers.ir.common.IRVariable;
import edu.mit.compilers.ir.common.IRVisitor;

import java.util.ArrayList;

public class IRMethodCall extends IRExpression {

	private IRVariable variable;
	private IRArgumentList argList;

	public IRMethodCall(IRVariable variable, IRArgumentList argList) {
		super("IRMethodCall");
		this.variable = variable;
		this.argList = argList;
		setLine(variable.getLine());
		assert(argList != null);
	}

	public IRVariable getVariable() {
		return variable;
	}
	
	public IRArgumentList getArgList() {
		return argList;
	}

	@Override
	public <T> T accept(IRVisitor<T> visitor) {
		return visitor.visit(this);
	}

	@Override
	public ArrayList<IR> getChildren() {
		ArrayList<IR> ret = new ArrayList<>();
		ret.add(variable);
		ret.add(argList);
		return ret;
	}

	@Override
	public IRMethodCall clone() throws CloneNotSupportedException {
		IRMethodCall clone = (IRMethodCall) super.clone();
		if (variable != null) {
			clone.variable = variable.clone();
		}
		if (argList != null) {
			clone.argList = argList.clone();
		}
		return clone;
	}
}
