package edu.mit.compilers.ir.statement;

import java.util.ArrayList;

import edu.mit.compilers.ir.common.IR;
import edu.mit.compilers.ir.common.IRVariable;
import edu.mit.compilers.ir.common.IRVisitor;
import edu.mit.compilers.ir.expression.IRExpression;
import edu.mit.compilers.ir.expression.IRLocation;

public class IRAssignStmt extends IRStatement {
	private IRLocation location;
	// Type uses String (simple implement)
	private String operator;
	private IRExpression value;
	
	public IRAssignStmt(IRLocation location, String operator, IRExpression value) {
		super("IRAssignStmt");
		this.location = location;
		this.operator = operator;
		this.value = value;
	}
	
	public IRAssignStmt(IRLocation location, String operator) {
		this(location, operator, null);
		assert(operator.equals("++") || operator.equals("--"));
	}
	
	public IRAssignStmt(IRVariable variable, String operator1, IRExpression initValue) {
		this(new IRLocation(variable), operator1, initValue);
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
	public ArrayList<IR> getChildren() {
		ArrayList<IR> ret = new ArrayList<IR>();
		ret.add(location);
		ret.add(value);
		return ret;
	}
	
	public String getInfoForShow(String prefix) {
		return prefix + 
				" DebugID: " + getDebugID() + 
				" Tag: " + getTag() + 
				" Op: " + getOperator() + '\n';
	}
}
