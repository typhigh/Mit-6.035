package edu.mit.compilers.ir.decl;

import edu.mit.compilers.ir.*;

public class IRMethodDecl extends IRMemberDecl{

	public IRMethodDecl(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public <T> T accept(IRVisitor<T> visitor) {
		return visitor.visit(this);
	}

}
