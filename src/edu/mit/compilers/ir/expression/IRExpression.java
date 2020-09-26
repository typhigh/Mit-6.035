package edu.mit.compilers.ir.expression;
import edu.mit.compilers.ir.common.IR;
import edu.mit.compilers.ir.type.IRBasicType;
import edu.mit.compilers.utils.StringInfo;

public class IRExpression extends IR {

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

	@Override
	public StringInfo getInfoForShow(String prefix) {
		return super.getInfoForShow(prefix).addInfo("TypeName: " + (type == null ? "null" : type.toString()));
	}

	@Override
	public IRExpression clone() throws CloneNotSupportedException {
		return (IRExpression) super.clone();
	}
}
