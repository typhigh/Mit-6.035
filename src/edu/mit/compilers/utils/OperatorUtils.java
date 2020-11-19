package edu.mit.compilers.utils;

public class OperatorUtils {
	
	public static boolean isUnary(String operator) {
		return operator.equals("-") || operator.equals("!");
	}
	
	public static boolean isBinary(String operator) {
		return isRel(operator)
				|| isEq(operator)
				|| isArith(operator)
				|| isCond(operator);
	}
	
	public static boolean isRel(String operator) {
		return operator.equals("<")
				|| operator.equals(">")
				|| operator.equals("<=")
				|| operator.equals(">=");
	}
	
	public static boolean isEq(String operator) {
		return operator.equals("==") || operator.equals("!=");
	}
	
	public static boolean isArith(String operator) {
		return operator.equals("+")
				|| operator.equals("-")
				|| operator.equals("*")
				|| operator.equals("/")
				|| operator.equals("%");
	}
	
	public static boolean isCond(String operator) {
		return operator.equals("&&") || operator.equals("||");
	}

	public static String convertPlusOpToBinaryOp(String operator) {
		switch (operator) {
			case "+="	: return "+";
			case "-=" 	: return "-";
			default		: {
				throw new RuntimeException(operator + " is not plus operator");
			}
		}
	}
}
