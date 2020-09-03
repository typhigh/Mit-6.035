package edu.mit.compilers.ir.common;

import edu.mit.compilers.ir.decl.IRMethodDecl;

import java.util.ArrayList;

public abstract class IR {
	private final String tag;
	
	// Debug id
	private final int debugID;
	private static int currentID = 0;
	
	// Location information
	private int line = 0;
	private int column = 0;
	
	// Empty children
	private final static ArrayList<IR> emptyChildren = new ArrayList<>();
	
	// Parent (in ir-tree) filler by semantic checker
	private IR parent = null;

	// Which method cover this ir filled by semantic checker
	private IRMethodDecl coveredByWhichMethod = null;
	
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

	public IRMethodDecl getCoveredByWhichMethod() {
		return coveredByWhichMethod;
	}

	public void setCoveredByWhichMethod(IRMethodDecl coveredByWhichMethod) {
		this.coveredByWhichMethod = coveredByWhichMethod;
	}

	/*
	 * Show the tree
	 */
	public String showTree() {
		StringBuilder result = new StringBuilder();
		showTreeImpl("", result);
		return result.toString();
	}
	
	/*
	 * The implement of showTree with prefix
	 */
	public void showTreeImpl(String prefix, StringBuilder result) {
		result.append(getInfoForShow(prefix));
		ArrayList<IR> children = getChildren();
		assert(children != null);
		for (IR child : children) {
			child.showTreeImpl(prefix + " ", result);
		}
	}
	
	/*
	 * The info of the node
	 */
	public String getInfoForShow(String prefix) {
		return prefix + " DebugID: " + getDebugID() + " Tag: " + getTag() + '\n';
	}

	public IR getParent() {
		return parent;
	}

	public void setParent(IR parent) {
		this.parent = parent;
	}
	
	/*
	 * Return children of IR 
	 */
	public abstract ArrayList<IR> getChildren();
	
	/*
	 * accept function for visitor 
	 */
	public abstract<T> T accept(IRVisitor<T> visitor);
	
	public static ArrayList<IR> getEmptyChildren() {
		return emptyChildren;
	}
}
