package edu.mit.compilers.ir.decl;

import java.util.ArrayList;

import edu.mit.compilers.ir.common.IR;
import edu.mit.compilers.ir.common.IRVariable;
import edu.mit.compilers.ir.common.IRVisitor;
import edu.mit.compilers.ir.type.IRType;

public class IRFieldDecl extends IRMemberDecl {
	private IRType type;
	public IRFieldDecl(IRType type, IRVariable variable) {
		super("IRFieldDecl", variable);
		this.type = type;
	}

	@Override
	public ArrayList<IR> getChildren() {
		ArrayList<IR> children = new ArrayList<IR>();
		children.add(type);
		children.add(getVariable());
		return children;
	}

	@Override
	public <T> T accept(IRVisitor<T> visitor) {
		return visitor.visit(this);
	}
}