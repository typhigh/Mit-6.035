package edu.mit.compilers.semantic;

public class BasicTypeDesc extends TypeDesc {

	private final String typeName;
	
	//Type instance
	public static final BasicTypeDesc IntType = new BasicTypeDesc("int", 4);
	public static final BasicTypeDesc BoolType = new BasicTypeDesc("bool", 1);
	public static final BasicTypeDesc VoidType = new BasicTypeDesc("void", 0);
	
	protected BasicTypeDesc(String typeName, int len) {
		super(len);
		this.typeName = typeName;
	}

	public String getTypeName() {
		return typeName;
	}

	@Override
	public boolean isBasicType() {
		return true;
	}
	
	@Override
	public String toString() {
		return typeName;
	}
	
	public static BasicTypeDesc GetInstance(String typeName) {
		switch(typeName) {
		case "int"	: return IntType;
		case "bool"	: return BoolType;
		case "void" : return VoidType;
		default		: 
			throw new RuntimeException("Unkhown basic type : " + typeName);
		}
	}
}
