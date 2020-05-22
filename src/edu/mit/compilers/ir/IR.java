package edu.mit.compilers.ir;

public abstract class IR {
	private final String tag;
	
	// Debug id
	private int debugID;
	private static int currentID = 0;
	
	// Location information
	private int line;
	private int column;
	
	public abstract<T> T accept(IRVisitor<T> visitor);

	public IR(String tag) {
		this.tag = tag;
		this.debugID = currentID++;
	}
	
	public String getTag() {
		return tag;
	}
	
	public int getDebugID() {
		return debugID;
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

	/*
	 * Show the tree
	 */
	public String showTree() {
		StringBuilder result = new StringBuilder("");
		showTreeImpl("", result);
		return result.toString();
	}
	
	/*
	 * The implement of showTree with prefix
	 */
	public abstract void showTreeImpl(String prefix, StringBuilder result);
}
