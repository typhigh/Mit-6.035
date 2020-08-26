package edu.mit.compilers.ir.decl;

import edu.mit.compilers.ir.common.IR;
import edu.mit.compilers.ir.common.IRVariable;
import edu.mit.compilers.ir.common.IRVisitor;

import java.util.ArrayList;

public class IRImportDecl extends IRMemberDecl {

	public IRImportDecl(IRVariable variable) {
		super("IRImportDecl", variable);
	}

	@Override
	public ArrayList<IR> getChildren() {
		ArrayList<IR> children = new ArrayList<>();
		children.add(getVariable());
		return children;
	}
	
	@Override
	public <T> T accept(IRVisitor<T> visitor) {
		return visitor.visit(this);
	}	
}
