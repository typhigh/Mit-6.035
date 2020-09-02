package edu.mit.compilers.ir.statement;

import edu.mit.compilers.ir.common.IR;
import edu.mit.compilers.ir.common.IRVariable;
import edu.mit.compilers.ir.common.IRVisitor;
import edu.mit.compilers.ir.expression.IRExpression;
import edu.mit.compilers.ir.expression.IRLocation;

import java.util.ArrayList;

public class IRAssignStmt extends IRStatement {
	private IRLocation location;
	private IRExpression value;
	
	public IRAssignStmt(IRLocation location, IRExpression value) {
		super("IRAssignStmt");
		this.location = location;
		assert value != null;
		this.value = value;
	}
	
	public IRAssignStmt(IRVariable variable, IRExpression value) {
		this(new IRLocation(variable), value);
	}

	public IRLocation getLocation() {
		return location;
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
		ArrayList<IR> ret = new ArrayList<>();
		ret.add(location);
		ret.add(value);
		return ret;
	}
}
