package edu.mit.compilers.ir.expression.literal;

import antlr.Token;
import edu.mit.compilers.ir.common.IR;
import edu.mit.compilers.ir.expression.IRExpression;
import edu.mit.compilers.utils.Literal;
import edu.mit.compilers.utils.StringInfo;

import java.util.ArrayList;

public class IRLiteral<T> extends IRExpression {

	private Literal<T> literal;
	public IRLiteral(String tag, Token token) {
		super(tag);
		this.literal = new Literal(token.getText());
		this.setLine(token.getLine());
		this.setColumn(token.getColumn());
	}

	public Literal<T> getLiteral() {
		return literal;
	}

	public String getLiteralValue() {
		return literal.getLiteralValue();
	}

	public void setLiteralValue(String literalValue) {
		literal.setLiteralValue(literalValue);
	}

	public T getValue() {
		return literal.getValue();
	}

	public void setValue(T value) {
		literal.setValue(value);
	}

	@Override
	public ArrayList<IR> getChildren() {
		return getEmptyChildren();
	}

	@Override
	public StringInfo getInfoForShow(String prefix) {
		return super.getInfoForShow(prefix).addInfo("LiteralValue: " + getLiteralValue());
	}

	@Override
	public IRLiteral<T> clone() throws CloneNotSupportedException {
		return (IRLiteral<T>) super.clone();
	}
}
