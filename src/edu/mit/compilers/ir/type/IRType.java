package edu.mit.compilers.ir.type;

import edu.mit.compilers.ir.common.IR;

public class IRType extends IR {
	
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
	
	public boolean isBasicType() {
		throw new RuntimeException("IRType not support isArrayType/isBasicType");
	}
	
	public String toString() {
		throw new RuntimeException("IRType not support toString");
	}

	@Override
	public IRType clone() throws CloneNotSupportedException {
		return (IRType) super.clone();
	}
}
