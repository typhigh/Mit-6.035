package edu.mit.compilers.ir.common;

import java.util.ArrayList;

import antlr.Token;

public class IRVariable extends IR {
	private String name;
	public IRVariable(String name, int line, int column) {
		super("IRVariable");
		this.name = name;
		setLine(line);
		setColumn(column);
	}
	
	public IRVariable (Token token) {
		this(token.getText(),token.getLine(), token.getColumn());
	}

	public String getName() {
		return name;
	}

	@Override
	public ArrayList<IR> getChildren() {
		return getEmptyChildren();
	}
	
	@Override
	public <T> T accept(IRVisitor<T> visitor) {
		return visitor.visit(this);
	}

}
