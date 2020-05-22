package edu.mit.compilers.semantic;

import antlr.Token;

public class Variable {
	private TypeDesc type;
	private Identifier identifier;
	
	public Variable(TypeDesc type) {
		this(type, null);
	}
	
	public Variable(TypeDesc type, Token identifier) {
		this.type = type;
		this.identifier = new Identifier(identifier);
	}
	
	public Variable(Token type, Token identifier) {
		this(BasicTypeDesc.GetInstance(type.getText()), identifier);
	}

	public TypeDesc getType() {
		return type;
	}

	public String getName() {
		return identifier.name;
	}
	
	public String toString() {
		assert(identifier.name != null);
		return "Tag: Variable" +  
				" Type: " + type.toString() + 
				" Name: " + identifier.name; 
	}
	
	public int getLine() {
		return identifier.line;
	}
	
	public int getColumn() {
		return identifier.column;
	}
	
}
