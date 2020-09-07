package edu.mit.compilers.ir.expression.literal;

import antlr.Token;
import edu.mit.compilers.ir.common.IR;
import edu.mit.compilers.ir.expression.IRExpression;
import edu.mit.compilers.utils.StringInfo;

import java.util.ArrayList;

public abstract class IRLiteral<T> extends IRExpression {

	protected String literalValue;

	// filled by LiteralValuerule
	protected T value;
	
	public IRLiteral(String tag, Token token) {
		super(tag);
		this.literalValue = token.getText();
		this.setLine(token.getLine());
		this.setColumn(token.getColumn());
	}

	public String getLiteralValue() {
		return literalValue;
	}

	public void setLiteralValue(String literalValue) {
		this.literalValue = literalValue;
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

	public ArrayList<IR> getChildren() {
		return getEmptyChildren();
	}

	@Override
	public StringInfo getInfoForShow(String prefix) {
		return super.getInfoForShow(prefix).addInfo("LiteralValue: " + getLiteralValue());
	}
}
