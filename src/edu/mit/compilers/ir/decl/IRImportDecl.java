package edu.mit.compilers.ir.decl;

import java.util.ArrayList;

import antlr.Token;
import edu.mit.compilers.ir.common.IR;
import edu.mit.compilers.ir.common.IRVariable;
import edu.mit.compilers.ir.common.IRVisitor;

public class IRImportDecl extends IRMemberDecl {

	public IRImportDecl(IRVariable variable) {
		super("IRImportDecl", variable);
	}

	@Override
	public ArrayList<IR> getChildren() {
		ArrayList<IR> children = new ArrayList<IR>();
		children.add(getVariable());
		return children;
	}
	
	@Override
	public <T> T accept(IRVisitor<T> visitor) {
		return visitor.visit(this);
	}	
}
