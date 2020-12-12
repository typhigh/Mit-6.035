package edu.mit.compilers.ir.decl;

import edu.mit.compilers.ir.common.IR;
import edu.mit.compilers.ir.common.IRVariable;
import edu.mit.compilers.utils.StringInfo;

public class IRMemberDecl extends IR {
	private IRVariable variable;
	private boolean global = false;

	public IRMemberDecl(String tag, IRVariable variable) {
		super(tag);
		this.variable = variable;
		setLine(variable.getLine());
		setColumn(variable.getColumn());
	}

	public IRVariable getVariable() {
		return variable;
	}

	public void setGlobal(boolean global) {
		this.global = global;
	}

	public boolean isGlobal() {
		return global;
	}

	@Override
	public StringInfo getInfoForShow(String prefix) {
		return super.getInfoForShow(prefix).addInfo("Global: " + global);
	}

	@Override
	public IRMemberDecl clone() throws CloneNotSupportedException {
		IRMemberDecl clone = (IRMemberDecl) super.clone();
		if (variable != null) {
			clone.variable = variable.clone();
		}
		return clone;
	}
}
