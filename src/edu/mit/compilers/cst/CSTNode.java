package edu.mit.compilers.cst;
import java.util.ArrayList;
import antlr.Token;
import edu.mit.compilers.grammar.DecafParserTokenTypes;
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
	 * Return if the node is operator
	 */
	public boolean isOperator() {
		int type = token.getType();
	    return type == DecafParserTokenTypes.OP_AND || 
	    		type == DecafParserTokenTypes.OP_ASSIGN ||
	    		type == DecafParserTokenTypes.OP_ASSIGN_DIV ||
	    		type == DecafParserTokenTypes.OP_ASSIGN_MINUS ||
	    		type == DecafParserTokenTypes.OP_ASSIGN_MOD ||
	    		type == DecafParserTokenTypes.OP_ASSIGN_MUL ||
	    		type == DecafParserTokenTypes.OP_ASSIGN_PLUS ||
	    		type == DecafParserTokenTypes.OP_DEC ||
	    		type == DecafParserTokenTypes.OP_DIV ||
	    		type == DecafParserTokenTypes.OP_EQ ||
	    		type == DecafParserTokenTypes.OP_GE ||
	    		type == DecafParserTokenTypes.OP_GT ||
	    		type == DecafParserTokenTypes.OP_INC ||
	    		type == DecafParserTokenTypes.OP_LE ||
	    		type == DecafParserTokenTypes.OP_LT ||
	    		type == DecafParserTokenTypes.OP_MINUS ||
	    		type == DecafParserTokenTypes.OP_MOD ||
	    		type == DecafParserTokenTypes.OP_MUL ||
	    		type == DecafParserTokenTypes.OP_NE ||
	    		type == DecafParserTokenTypes.OP_NOT ||
	    		type == DecafParserTokenTypes.OP_OR ||
	    		type == DecafParserTokenTypes.OP_PLUS;
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
		if (children == null || children.isEmpty()) {
			return null;
		} 
		return children.get(children.size()-1);
	}
	
	public String toString() {
		return "id: " + debugID + " content: " + name;
	}

}
