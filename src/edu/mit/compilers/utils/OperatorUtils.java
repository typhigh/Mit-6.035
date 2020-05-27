package edu.mit.compilers.utils;

public class OperatorUtils {
	
	public static boolean IsUnary(String operator) {
		return operator == "-" || operator == "!";
	}
	
	public static boolean IsBinary(String operator) {
		return IsRel(operator) 
				|| IsEq(operator)
				|| IsArith(operator)
				|| IsCond(operator);
	}
	
	public static boolean IsRel(String operator) {
		return operator == "<"
				|| operator == ">"
				|| operator == "<="
				|| operator == ">=";
	}
	
	public static boolean IsEq(String operator) {
		return operator == "==" || operator == "!=";
	}
	
	public static boolean IsArith(String operator) {
		return operator == "+" 
				|| operator == "-" 
				|| operator == "*"
				|| operator == "/"
				|| operator == "%";
	}
	
	public static boolean IsCond(String operator) {
		return operator == "&&" || operator == "||";
	}
}