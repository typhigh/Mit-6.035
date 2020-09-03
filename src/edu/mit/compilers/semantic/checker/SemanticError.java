package edu.mit.compilers.semantic.checker;

public class SemanticError {
	private String error = "";
	private int line = 0;
	private int column = 0;
	private int ruleId = 0;

	public static SemanticError NoError = new SemanticError();

	public SemanticError() {}

	public void set(String error, int ruleId) {
		set(error, ruleId, 0, 0);
	}

	public void set(String error, int ruleId, int line) {
		set(error, ruleId, line, 0);
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
		String lineInfo = line != 0 ? "Line " + line : "";
		String columnInfo = column != 0 ? " Column " + column : "";
		return lineInfo + columnInfo + " Rule " + ruleId + " : " + error;
	}
}
