package edu.mit.compilers.ir.expression;
import edu.mit.compilers.ir.common.IR;
import edu.mit.compilers.ir.type.IRBasicType;

public abstract class IRExpression extends IR {

	// filled by TypeRule
	private IRBasicType type = null;
	public IRExpression(String name) {
		super(name);
	}

	public void setType(IRBasicType type) {
		this.type = type;
	}

	public IRBasicType getType() {
		return type;
	}
}
