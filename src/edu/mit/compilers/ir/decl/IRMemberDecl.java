package edu.mit.compilers.ir.decl;

import edu.mit.compilers.ir.common.IR;
import edu.mit.compilers.ir.common.IRVariable;

public abstract class IRMemberDecl extends IR {
	private IRVariable variable; 
	
	public IRMemberDecl(String tag, IRVariable variable) {
		super(tag);
		this.variable = variable;
		setLine(variable.getLine());
	}

	public IRVariable getVariable() {
		return variable;
	}
}
