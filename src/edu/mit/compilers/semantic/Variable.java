package edu.mit.compilers.semantic;

import antlr.Token;

public class Variable {
	private TypeDesc type;
	private String name;
	
	public Variable(TypeDesc type) {
		this(type, null);
	}
	
	public Variable(TypeDesc type, Token name) {
		this.type = type;
		this.name = name.getText();
	}
	
	public Variable(Token type, Token name) {
		this(BasicTypeDesc.GetInstance(type.getText()), name);
	}

	public TypeDesc getType() {
		return type;
	}

	public String getName() {
		return name;
	}
	
	
}
