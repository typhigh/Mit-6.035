package edu.mit.compilers.ir.expression;

import edu.mit.compilers.ir.IRVisitor;
import edu.mit.compilers.ir.expression.IRExpression;
import edu.mit.compilers.semantic.Identifier;

public class IRLocation extends IRExpression {
	
	private Identifier identifier;
	private boolean isArrayLocation;
	private IRExpression location;
	
	public IRLocation(Identifier identifier, IRExpression location) {
		super("IRLocation");
		this.identifier = identifier;
		this.isArrayLocation = true;
		this.location = location;
	}
	
	public IRLocation(Identifier identifier) {
		super("IRLocation");
		this.identifier = identifier;
		this.isArrayLocation = false;
		this.location = null;
	}
	
	public String getName() {
		return identifier.name;
	}

	public Identifier getIdentifier() {
		return identifier;
	}
	
	public boolean isArrayLocation() {
		return isArrayLocation;
	}

	public IRExpression getLocation() {
		return location;
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
				" identifier: " + getName() + '\n';
		result.append(info);
		if (isArrayLocation) {
			location.showTreeImpl(prefix + " ", result);
		}
	}
}
