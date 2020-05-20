package edu.mit.compilers.ir.decl;

import antlr.Token;
import edu.mit.compilers.ir.IRVisitor;

public class IRImportDecl extends IRMemberDecl {
	public IRImportDecl(Token id) {
		super("IRImportDecl", id);
	}

	@Override
	public <T> T accept(IRVisitor<T> visitor) {
		return visitor.visit(this);
	}
	
	
	
}
