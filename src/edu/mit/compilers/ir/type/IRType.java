package edu.mit.compilers.ir.type;

import edu.mit.compilers.ir.common.IR;

public abstract class IRType extends IR {
	
	// The size (bytes)
	private final int size;
	
	public IRType(String name, int size) {
		super(name);
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
