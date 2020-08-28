package edu.mit.compilers.semantic.checker;

public class SemanticError {
	public String error;
	public int line;
	public int column;

	public static SemanticError NoError = new SemanticError();

	public SemanticError(String error, int line, int column) {
		this.error = error;
		this.line = line;
		this.column = column;
	}

    public SemanticError() {
        this("", -1, -1);
    }

	public boolean hasError() {
		return !error.isEmpty();
	}
}
