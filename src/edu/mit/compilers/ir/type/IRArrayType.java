package edu.mit.compilers.ir.type;

import edu.mit.compilers.ir.common.IR;
import edu.mit.compilers.ir.common.IRVisitor;
import edu.mit.compilers.ir.expression.literal.IRIntLiteral;

import java.util.ArrayList;

/*
 * Simple Array type
 */
public class IRArrayType extends IRType {
	
	// Internal type 
	private IRBasicType type;
	private IRIntLiteral len;
	
	public IRArrayType(IRBasicType type, IRIntLiteral len) {
		super("IRArrayType");
		this.type = type;
		this.len = len;
		this.setLine(type.getLine());
	}

	public IRBasicType getBasicType() {
		return type;
	}
	
	public IRIntLiteral getLen() {
		return len;
	}	 
	
	@Override
	public boolean isBasicType() {
		return false;
	}
	
	@Override
	public String toString() {
		// Not consider "int [3][4]"
		return type.toString() + "[" + len.getValue() + "]";
	}

	@Override
	public <T> T accept(IRVisitor<T> visitor) {
		return visitor.visit(this);
	}

	@Override
	public ArrayList<IR> getChildren() {
		ArrayList<IR> ret = new ArrayList<>();
		ret.add(type);
		ret.add(len);
		return ret;
	}

	@Override
	public IRArrayType clone() throws CloneNotSupportedException {
		IRArrayType clone = (IRArrayType) super.clone();
		if (len != null) {
			clone.len = len.clone();
		}
		return clone;
	}
}
