package edu.mit.compilers.semantic.checker;

import edu.mit.compilers.ir.common.IRVariable;
import edu.mit.compilers.ir.decl.IRFieldDecl;
import edu.mit.compilers.ir.decl.IRMemberDecl;
import edu.mit.compilers.ir.decl.IRMethodDecl;
import edu.mit.compilers.ir.expression.IRExpression;
import edu.mit.compilers.ir.type.IRBasicType;
import edu.mit.compilers.ir.type.IRType;
import edu.mit.compilers.semantic.EnvStack;

public class TypeHelper {

    /*
     * Check if the variable is method variable
     * If true, return its (basic) type
     * otherwise return null and fill error
     */
    public static IRBasicType checkMethodVariableAndReturnType(EnvStack env, SemanticError error, IRVariable variable) {
        IRMemberDecl decl = env.seek(variable);
        if (decl == null) {
            // do nothing
            return null;
        }

        if (!decl.getTag().equals("IRMethodDecl")) {
            error.line = variable.getLine();
            error.error = "<id> " + variable.toString() +
                    " must be an method variable but it is " + decl.getTag();
            return null;
        }

        return ((IRMethodDecl) decl).getType();
    }

    /*
     * Check if the variable is field variable
     * If true, return its type
     * otherwise return null and fill error
     */
    public static IRType checkFieldVariableAndReturnType(EnvStack env, SemanticError error, IRVariable variable) {
        IRMemberDecl decl = env.seek(variable);
        if (decl == null) {
            // do nothing
            return null;
        }

        if (!decl.getTag().equals("IRFieldDecl")) {
            error.line = variable.getLine();
            error.error = "<id> " + variable.toString() + " must be an variable but this is " + decl.getTag();
            return null;
        }

        return ((IRFieldDecl) decl).getType();
    }

    /*
     * Check if the variable is field variable && this type is expected
     * If true, return its type
     * otherwise return null and fill error
     */
    public static IRType checkFieldVariableAndReturnType(EnvStack env, SemanticError error,
                                                     IRVariable variable, boolean isArray)
    {
        IRType type = checkFieldVariableAndReturnType(env, error, variable);
        if (type == null) {
            return null;
        }

        String expectType = isArray ? "array-type" : "basic-type";
        if (type.isArrayType() != isArray) {
            error.line = variable.getLine();
            error.error = "<id> " + variable.toString() + " must be an variable with " + expectType +
                    " but this type is " + type.toString();
            return null;
        }
        return type;
    }

    public static boolean checkExpressionType(EnvStack env, SemanticError error, IRExpression expr, IRType expected) {
        assert expected != null;
        IRType type = expr.getType();
        if (type == null) {
            // do nothing
            return false;
        }

        if (type.equals(expected)) {
            error.line = expr.getLine();
            error.error = "<expression> should have type " + expected.toString() + " but not " + type.toString();
            return false;
        }
        return true;
    }

    public static boolean checkIfTypeEqual(EnvStack env, SemanticError error, IRExpression left, IRExpression right) {
        if (!left.getType().equals(right.getType())) {
            error.line = left.getLine();
            error.error = "<left> type is " + left.getType().toString() +
                    " and <right> type is " + right.getType().toString();
            return false;
        }
        return true;
    }
}
