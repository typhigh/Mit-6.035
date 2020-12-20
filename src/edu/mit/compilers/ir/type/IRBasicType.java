package edu.mit.compilers.ir.type;

import edu.mit.compilers.ir.common.IR;
import edu.mit.compilers.ir.common.IRVisitor;
import edu.mit.compilers.utils.StringInfo;

import java.util.ArrayList;

public class IRBasicType extends IRType {

	private String typeName;

	//Type instance
	public static final IRBasicType IntType = new IRBasicType("int", 8);

	public static final IRBasicType CharType = new IRBasicType("char", 1);
	public static final IRBasicType BoolType = new IRBasicType("bool", 8);
	public static final IRBasicType VoidType = new IRBasicType("void", 0);
	public static final IRBasicType StringType = new IRBasicType("string", 0);
	
	protected IRBasicType(String typeName, int size) {
		super("IRBasicType");
		this.typeName = typeName;
		setSize(size);
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

	@Override
	public StringInfo getInfoForShow(String prefix) {
		return super.getInfoForShow(prefix).addInfo("TypeName: " + getTypeName());
	}

	@Override
	public IRBasicType clone() throws CloneNotSupportedException {
		// instance no need to clone
		return this;
	}
}
