package edu.mit.compilers.ir.expression.literal;

import java.util.ArrayList;

import antlr.Token;
import edu.mit.compilers.ir.common.IR;
import edu.mit.compilers.ir.expression.IRExpression;

public abstract class IRLiteral<T> extends IRExpression {
	
	protected T value;
	
	public IRLiteral(String tag, T value, Token token) {
		super(tag);
		this.value = value;
		this.setLine(token.getLine());
		this.setColumn(token.getColumn());
	}
	
	public T getValue() {
		return value;
	}
	
	public ArrayList<IR> getChildren() {
		return getEmptyChildren();
	}

}
