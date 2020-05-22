package edu.mit.compilers.semantic;

import antlr.Token;

public class Identifier {
	public final String name;
	public final int line;
	public final int column;
	Identifier(String name, int line, int column) {
		this.name = name;
		this.line = line;
		this.column = column;
	}
	public Identifier(Token token) {
		this(token.getText(), token.getLine(), token.getColumn());
	}
}
