package edu.mit.compilers.ir;

public abstract class IR {
	private String tag;
	
	// Location information
	private int line;
	private int column;
	
	public abstract<T> T accept(IRVisitor<T> visitor);

	public IR(String tag) {
		this.tag = tag;
	}
	
	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public int getLine() {
		return line;
	}

	public void setLine(int line) {
		this.line = line;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public String showTree() {
		StringBuilder result = new StringBuilder("");
		showTreeImpl("", result);
		return result.toString();
	}
	
	protected abstract void showTreeImpl(String prefix, StringBuilder result);
}
