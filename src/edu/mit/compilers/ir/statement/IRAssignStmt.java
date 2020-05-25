package edu.mit.compilers.ir.statement;

import edu.mit.compilers.ir.IRVisitor;
import edu.mit.compilers.ir.expression.IRExpression;
import edu.mit.compilers.semantic.Identifier;

public class IRAssignStmt extends IRStatement {
	private final IRLocation location;
	// Type uses String (simple implement)
	private final String operator;
	private final IRExpression value;
	
	public IRAssignStmt(IRLocation location, String operator, IRExpression value) {
		super("IRAssignStmt");
		this.location = location;
		this.operator = operator;
		this.value = value;
	}
	
	public IRAssignStmt(IRLocation location, String operator) {
		this(location, operator, null);
		assert(operator == "++" || operator == "--");
	}
	
	public IRAssignStmt(Identifier identifier, String operator1, IRExpression initValue) {
		this(new IRLocation(identifier), operator1, initValue);
	}

	public IRLocation getLocation() {
		return location;
	}

	public String getOperator() {
		return operator;
	}

	public IRExpression getValue() {
		return value;
	}
	
	@Override
	public <T> T accept(IRVisitor<T> visitor) {
		return visitor.visit(this);
	}

	@Override
	public void showTreeImpl(String prefix, StringBuilder result) {
		String info = prefix + 
				" DebugID: " + getDebugID() + 
				" Tag: " + getTag() + 
				" Operator: " + getOperator() + '\n';
		result.append(info);
		
		location.showTreeImpl(prefix + " ", result);
		value.showTreeImpl(prefix + " ", result);
	}
}
