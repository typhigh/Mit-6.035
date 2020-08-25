package edu.mit.compilers.ir.type;

import java.util.ArrayList;

import edu.mit.compilers.ir.common.IR;
import edu.mit.compilers.ir.common.IRVisitor;
import edu.mit.compilers.ir.expression.literal.IRIntLiteral;

/*
 * Simple Array type
 */
public class IRArrayType extends IRType {
	
	// Internal type 
	private final IRType type;
	private IRIntLiteral len;
	
	public IRArrayType(IRType type, IRIntLiteral len) {
		super("IRArrayType", len.getValue() * type.getSize());
		this.type = type;
		this.len = len;
	}

	public IRType getType() {
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
		return type.toString() + "[" + String.valueOf(len.getValue()) + "]";
	}

	@Override
	public <T> T accept(IRVisitor<T> visitor) {
		return visitor.visit(this);
	}

	@Override
	public ArrayList<IR> getChildren() {
		ArrayList<IR> ret = new ArrayList<IR>();
		ret.add(type);
		ret.add(len);
		return ret;
	}
}