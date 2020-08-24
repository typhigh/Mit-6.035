package edu.mit.compilers.ir.expression.literal;

import antlr.Token;
import edu.mit.compilers.ir.common.IRVisitor;
import edu.mit.compilers.utils.StringUtils;

public class IRIntLiteral extends IRLiteral<Integer> {
	
	public IRIntLiteral(Token token) {
		this(parseTokenValue(token), token);
	}

	/*
	* For test
	*/
	private IRIntLiteral(Integer value, Token token) {
		super("IntLiteral", value, token);
	}

	private static Integer parseTokenValue(Token token) {
		String text = token.getText();
		if (text.contains("0x") || text.contains("0X")) {
			String value = text.substring(2);
			return StringUtils.parseHexInt(value);
		} else {
			return Integer.valueOf(text);
		}
	}

	@Override
	public <T> T accept(IRVisitor<T> visitor) {
		return visitor.visit(this);
	}

}
