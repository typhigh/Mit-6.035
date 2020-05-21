package edu.mit.compilers.cst;
import java.util.ArrayList;
import java.util.Iterator;

import antlr.Token;
import edu.mit.compilers.utils.TokenUtils;
/* 
 * Concrete Tree Node
 * The first tree of the parser
 */
public class CSTNode {
	
	private CSTNode parent;
	private ArrayList<CSTNode> children;
	private String name;
	private Token token;
	private int line;
	private int column;
	
	// For Debug
	private static int currentID = 0;
	private int debugID;
	
	public CSTNode() {
		this.children = new ArrayList<CSTNode>();
		this.parent = null;
		
		// not thread safe
		this.debugID = currentID++;
	}
	
	public CSTNode(String name) {
		this();
		this.name = name;
	}
	
	public CSTNode(Token token, String name) {
		this(name);
		this.token = token;
		this.line = token.getLine();
		this.column = token.getColumn();
	}
	
	public CSTNode(Token token) {
		this(token, token.getText());
	}
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setChildren(ArrayList<CSTNode> children) {
		this.children = children;
	}
	
	public ArrayList<CSTNode> getChildren() {
		return children;
	}
	
	/*
	 * Get the children copy
	 */
	public ArrayList<CSTNode> getChildrenCopy() {
		return new ArrayList<CSTNode>(children);
	}

	public void setParent(CSTNode parent) {
		this.parent = parent;
	}
	
	public CSTNode getParent() {
		return parent;
	}

	public Token getToken() {
		return token;
	}
	
	public int getLine() {
		return line;
	}

	public int getColumn() {
		return column;
	}
	
	public int getDebugID() {
		return debugID;
	}
	
	/*
	 * Return if the node has no child
	 */
	public boolean hasChild() {
		return children != null && children.size() > 0;
	}
	
	/*
	 * Return if the node is operator
	 */
	public boolean isOperator() {
		return TokenUtils.isOperator(token);
	}
	
	/*
	 * Add child node
	 */
	public void addChild(CSTNode child) {
		children.add(child);
		child.setParent(this);
		System.out.println("Add a child From " + debugID + " " + child.debugID);
	}
	
	public void addChild(String name) {
		addChild(new CSTNode(name));
	}
	
	public void addChild(Token token) {
		addChild(new CSTNode(token));
	}

	/*
	 * Clear the node
	 */
	public void clear() {
		name = null;
		token = null;
		parent = null;
		children.clear();
	}
	
	/*
	 * Return last child node
	 */
	public CSTNode getLastChild() {
		if (!hasChild()) {
			return null;
		} 
		return children.get(children.size()-1);
	}
	
	/*
	 * Return the child at the position
	 */
	public CSTNode getChild(int index) {
		assert(children.size() > index);
		return children.get(index);
	}
	
	public int getChildrenSize() {
		return children.size();
	}
	
	public String toString() {
		return "id: " + debugID + " content: " + name + " " + (token != null ? ("typeID: " + token.getType()) : "" );
	}
	
}
