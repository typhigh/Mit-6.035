package edu.mit.compilers.ir.decl;

import antlr.Token;
import edu.mit.compilers.ir.*;

public abstract class IRMemberDecl extends IR {
	private String name; 
	
	public IRMemberDecl(String tag, Token token) {
		super(tag);
		this.name = token.getText();
		setLine(token.getLine());
	}

	public String getName() {
		return name;
	}
}
