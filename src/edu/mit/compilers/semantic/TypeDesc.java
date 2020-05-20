package edu.mit.compilers.semantic;

/*
 * 3 kind of types : 
 * (1) Basic type: integer / boolean 
 * (2) Array type
 * (3) Complex type(not supported)
 */
public abstract class TypeDesc {
	private final int len;
	
	public TypeDesc(int len) {
		this.len = len;
	}
	
	public int getLen() {
		return len;
	}
	
	
	public boolean isArrayType() {	
		return !isBasicType();
	}
	
	public abstract boolean isBasicType();
	
	public abstract String toString();
}
