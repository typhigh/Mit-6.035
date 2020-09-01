package edu.mit.compilers.semantic.checker;

public class SemanticError {
	private String error = "";
	private int line = 0;
	private int column = -1;
	private int ruleId = 0;

	public static SemanticError NoError = new SemanticError();

	public SemanticError() {}

	public void set(String error, int ruleId) {
		set(error, ruleId, -1, -1);
	}

	public void set(String error, int ruleId, int line) {
		set(error, ruleId, line, -1);
	}

	public void set(String error, int ruleId, int line, int column) {
		this.error = error;
		this.ruleId = ruleId;
		this.line = line;
		this.column = column;
	}

	public boolean hasError() {
		return !error.isEmpty();
	}

	@Override
	public String toString() {
		return "Line " + line + " Rule " + ruleId + " : " + error;
	}
}
