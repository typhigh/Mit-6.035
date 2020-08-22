package edu.mit.compilers.ir.type;

import java.util.ArrayList;

import edu.mit.compilers.ir.common.IR;
import edu.mit.compilers.ir.common.IRVisitor;

public class IRBasicType extends IRType {

	private final String typeName;

	//Type instance
	public static final IRBasicType IntType = new IRBasicType("int", 4);
	public static final IRBasicType BoolType = new IRBasicType("bool", 1);
	public static final IRBasicType VoidType = new IRBasicType("void", 0);
	
	protected IRBasicType(String typeName, int size) {
		super("IRBasicType", size);
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
	
	public static IRBasicType GetInstance(String typeName) {
		switch(typeName) {
		case "int"	: return IntType;
		case "bool"	: return BoolType;
		case "void" : return VoidType;
		default		: 
			throw new RuntimeException("Unkhown basic type : " + typeName);
		}
	}

	@Override
	public <T> T accept(IRVisitor<T> visitor) {
		return visitor.visit(this);
	}

	@Override
	public ArrayList<IR> getChildren() {
		return getEmptyChildren();
	}
	
	public String getInfoForShow(String prefix) {
		return prefix + 
				" DebugID: " + getDebugID() + 
				" Tag: " + getTag() + 
				" TypeName: " + getTypeName() + '\n';
	}

}
