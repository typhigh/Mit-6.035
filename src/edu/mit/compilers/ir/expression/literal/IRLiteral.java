package edu.mit.compilers.ir.expression.literal;

import antlr.Token;
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
	
	public void showTreeImpl(String prefix, StringBuilder result) {
		String info = prefix + 
				" DebugID: " + getDebugID() +
				" Tag: " + getTag() + 
				" Value: " + value + "\n";
		result.append(info);
	}
}
