package edu.mit.compilers.semantic;

import edu.mit.compilers.ir.expression.literal.IRIntLiteral;

/*
 * Simple Array type
 */
public class ArrayTypeDesc extends TypeDesc {
	
	// Internal type 
	private final TypeDesc type;
	private IRIntLiteral size;
	
	public ArrayTypeDesc(TypeDesc type, IRIntLiteral size) {
		super(size.getValue() * type.getLen());
		this.type = type;
		this.size = size;
	}

	public TypeDesc getType() {
		return type;
	}
	
	public IRIntLiteral getSize() {
		return size;
	}	
	
	@Override
	public boolean isBasicType() {
		return false;
	}
	
	@Override
	public String toString() {
		// Not consider "int [3][4]"
		return type.toString() + "[" + String.valueOf(size.getValue()) + "]";
	}

}
