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
	private ArrayList<CSTNode> childrens;
	private String name;
	private Token token;
	private int line;
	private int column;
	
	public CSTNode(Token token, String name) {
		this.token = token;
		this.name = name;
		this.line = token.getLine();
		this.column = token.getColumn();
		this.childrens = null;
		this.parent = null;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<CSTNode> getChildrens() {
		return childrens;
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
		childrens.add(child);
	}
	
	/*
	 * Clear the node
	 */
	public void clear() {
		this.childrens = null;
		this.name = null;
		this.token = null;
		this.parent = null;
	}

}
