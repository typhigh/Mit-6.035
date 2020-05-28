package edu.mit.compilers.semantic.checker;

public class SemanticError {
	boolean hasError;
	public String error;
	public int line;
	public int column;
	
	public static SemanticError NoError = new SemanticError();
	
	SemanticError(String error, int line, int column) {
		this.error = error;
		this.line = line;
		this.column = column;
		this.hasError = true;
	}

	public SemanticError() {
		this.hasError = false;
	}
}
