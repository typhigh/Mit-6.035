package edu.mit.compilers.ir.decl;

import antlr.Token;
import edu.mit.compilers.ir.*;
import edu.mit.compilers.semantic.Identifier;

public abstract class IRMemberDecl extends IR {
	private Identifier identifier; 
	
	public IRMemberDecl(String tag, Token token) {
		super(tag);
		this.identifier = new Identifier(token);
		setLine(token.getLine());
	}

	public String getName() {
		return identifier.name;
	}
	
	@Override
	public int getLine() {
		return identifier.line;
	}
	
	@Override
	public int getColumn() {
		return identifier.column;
	}
}
