package edu.mit.compilers.utils;

import antlr.Token;
import edu.mit.compilers.grammar.DecafParserTokenTypes;

public class TokenUtils {

	public static boolean isOperator(Token token) {
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
	
	public static boolean isUseless(Token token) {
		if (token == null) {
			return false;
		}
		
		int type = token.getType();
		return type == DecafParserTokenTypes.COLON ||
				type == DecafParserTokenTypes.COMMA || 
				type == DecafParserTokenTypes.EOF ||
				type == DecafParserTokenTypes.ESC ||
				type == DecafParserTokenTypes.LBRACK ||
				type == DecafParserTokenTypes.LCURLY || 
				type == DecafParserTokenTypes.LPAREN || 
				type == DecafParserTokenTypes.RBRACK || 
				type == DecafParserTokenTypes.RCURLY || 
				type == DecafParserTokenTypes.RPAREN || 
				type == DecafParserTokenTypes.SEMI ||
				type == DecafParserTokenTypes.TK_break || 
				type == DecafParserTokenTypes.TK_continue || 
				type == DecafParserTokenTypes.TK_else ||
				type == DecafParserTokenTypes.TK_for ||
				type == DecafParserTokenTypes.TK_if ||
				type == DecafParserTokenTypes.TK_import ||
				type == DecafParserTokenTypes.TK_len ||
				type == DecafParserTokenTypes.TK_return ||
				type == DecafParserTokenTypes.TK_while;
	}
}
