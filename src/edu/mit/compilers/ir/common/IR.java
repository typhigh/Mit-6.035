package edu.mit.compilers.ir.common;

import edu.mit.compilers.ir.decl.IRMethodDecl;
import edu.mit.compilers.utils.IRCloneHelper;
import edu.mit.compilers.utils.StringInfo;

import java.util.ArrayList;

public class IR implements Cloneable {
	private String tag;
	
	// Debug id
	private int debugID;
	private static int currentID = 0;
	
	// Location information
	private int line = 0;
	private int column = 0;
	
	// Empty children
	private final static ArrayList<IR> emptyChildren = new ArrayList<>();
	
	// Parent (in ir-tree) filler by SetParentHelper
	private IR parent = null;

	// Which method cover this ir filled by semantic checker
	private IRMethodDecl coveredByWhichMethod = null;

	private IR() {}

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
		result.append(getInfoForShow(prefix).toString());
		ArrayList<IR> children = getChildren();
		assert(children != null);
		for (IR child : children) {
			child.showTreeImpl(prefix + "  ", result);
		}
	}
	
	/*
	 * The info of the node
	 */
	public StringInfo getInfoForShow(String prefix) {
		StringInfo info = new StringInfo(prefix);
		info.addInfo("DebugID: " + getDebugID());
		info.addInfo("Tag: " + getTag());
//		info.addInfo("DebugAddress: " + this);
		return info;
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
	public ArrayList<IR> getChildren() {
		throw new RuntimeException("IR not support getChildren");
	}
	
	/*
	 * Accept function for visitor
	 */
	public<T> T accept(IRVisitor<T> visitor) {
		throw new RuntimeException("IR not support accept");
	}


	/*
	 * Clone function
	 */

	public IR clone() throws CloneNotSupportedException {
		IR clone = (IR) super.clone();
		IRCloneHelper.put(this, clone);
		clone.parent = IRCloneHelper.getClone(parent);
		clone.coveredByWhichMethod = (IRMethodDecl) IRCloneHelper.getClone(coveredByWhichMethod);
		return clone;
	}

	public static ArrayList<IR> getEmptyChildren() {
		return emptyChildren;
	}
}
