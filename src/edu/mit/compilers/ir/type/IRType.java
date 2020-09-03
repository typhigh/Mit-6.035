package edu.mit.compilers.ir.type;

import edu.mit.compilers.ir.common.IR;

public abstract class IRType extends IR {
	
	// The size (bytes) filled by semantic checker
	private int size;
	
	public IRType(String name) {
		super(name);
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getSize() {
		return size;
	}
	
	public boolean isArrayType() {	
		return !isBasicType();
	}
	
	public abstract boolean isBasicType();
	
	public abstract String toString();
}
