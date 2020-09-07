package edu.mit.compilers.ir.type;

import edu.mit.compilers.ir.common.IR;

public abstract class IRType extends IR {
	
	// The size (bytes) in ArrayType filled by ArrayLenRule
	private long size;

	public IRType(String name) {
		super(name);
	}

	public void setSize(long size) {
		this.size = size;
	}

	public long getSize() {
		return size;
	}
	
	public boolean isArrayType() {	
		return !isBasicType();
	}
	
	public abstract boolean isBasicType();
	
	public abstract String toString();
}
