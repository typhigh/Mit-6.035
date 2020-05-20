package edu.mit.compilers.semantic;

/*
 * Simple Array type
 */
public class ArrayTypeDesc extends TypeDesc {
	
	// Internal type 
	private final TypeDesc type;
	
	public ArrayTypeDesc(TypeDesc type,int len) {
		super(len * type.getLen());
		this.type = type;
	}

	public TypeDesc getType() {
		return type;
	}

	@Override
	public boolean isBasicType() {
		return false;
	}
	
	@Override
	public String toString() {
		// Don't consider "int [3][4]"
		return type.toString() + "[" + String.valueOf(getLen()) + "]";
	}	
	
}
