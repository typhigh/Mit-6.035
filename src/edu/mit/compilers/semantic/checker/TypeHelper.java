package edu.mit.compilers.semantic.checker;

import edu.mit.compilers.ir.common.IRVariable;
import edu.mit.compilers.ir.decl.IRFieldDecl;
import edu.mit.compilers.ir.decl.IRImportDecl;
import edu.mit.compilers.ir.decl.IRMemberDecl;
import edu.mit.compilers.ir.decl.IRMethodDecl;
import edu.mit.compilers.ir.expression.IRExpression;
import edu.mit.compilers.ir.type.IRBasicType;
import edu.mit.compilers.ir.type.IRType;
import edu.mit.compilers.semantic.EnvStack;

import java.util.ArrayList;

public class TypeHelper {

    /*
     * Check if the variable is method variable
     * If true, return its (basic) type
     * otherwise return null and fill error
     */
    public static IRBasicType checkMethodVariableAndGetType(EnvStack env, SemanticError error, int ruleId, IRVariable variable) {
        IRMemberDecl decl = env.seek(variable);
        if (decl == null) {
            // do nothing
            return null;
        }

        if (!(decl instanceof IRMethodDecl) && !(decl instanceof IRImportDecl)) {
            String info = "<id> " + variable.toString() +
                    " must be an method (or import) variable but it is " + decl.getTag();
            error.set(info, ruleId, variable.getLine(), variable.getColumn());
            return null;
        }

        // TODO : support import
        if (decl.getTag().equals("IRImportDecl")) {
            return null;
        }
        return ((IRMethodDecl) decl).getType();
    }

    /*
     * Check if the variable is field variable
     * If true, return its type
     * otherwise return null and fill error
     */
    public static IRType checkFieldVariableAndGetType(EnvStack env, SemanticError error,
                                                         int ruleId, IRVariable variable) {
        IRMemberDecl decl = env.seek(variable);
        if (decl == null) {
            // do nothing
            return null;
        }

        if (!(decl instanceof IRFieldDecl)) {
            String info = "<id> " + variable.toString() + " must be an variable but this is " + decl.getTag();
            error.set(info, ruleId, variable.getLine(), variable.getColumn());
            return null;
        }

        return ((IRFieldDecl) decl).getType();
    }

    /*
     * Check if the variable is field variable && this type is expected
     * If true, return its type
     * otherwise return null and fill error
     */
    public static IRType checkFieldVariableAndGetType(EnvStack env, SemanticError error, int ruleId,
                                                     IRVariable variable, boolean isArray)
    {
        IRType type = checkFieldVariableAndGetType(env, error, ruleId, variable);
        if (type == null) {
            return null;
        }

        String expectType = isArray ? "array-type" : "basic-type";
        if (type.isArrayType() != isArray) {
            String info = "<id> " + variable.toString() + " must be an variable with " + expectType +
                    " but this type is " + type.toString();
            error.set(info, ruleId, variable.getLine(), variable.getColumn());
            return null;
        }
        return type;
    }

    /*
     * Check if the type of expression is expected
     */
    public static boolean checkExpressionType(EnvStack env, SemanticError error, int ruleId,
                                              IRExpression expr, IRType expected) {
        assert expected != null;
        IRType type = expr.getType();
        if (type == null) {
            // do nothing
            return false;
        }

        if (!type.equals(expected)) {
            String info = "<expression> should have type " + expected.toString() + " but not " + type.toString();
            error.set(info, ruleId, expr.getLine());
            return false;
        }
        return true;
    }

    public static boolean checkExpressionType(EnvStack env, SemanticError error, int ruleId,
                                              IRExpression expr, ArrayList<IRType> expected) {
        assert expected != null;
        IRType type = expr.getType();
        if (type == null) {
            // do nothing
            return false;
        }

        if (!expected.contains(type)) {
            String info = "<expression> should have type " + expected.toString() + " but not " + type.toString();
            error.set(info, ruleId, expr.getLine());
            return false;
        }
        return true;
    }

    /*
     * Check if the types of two expression are equal
     */
    public static boolean checkIfTypeEqual(EnvStack env, SemanticError error, int ruleId,
                                           IRExpression left, IRExpression right) {
        if (left.getType() == null || right.getType() == null) {
            // do nothing
            return true;
        }

        if (!left.getType().equals(right.getType())) {
            String info = "<left> type is " + left.getType().toString() +
                    " and <right> type is " + right.getType().toString();
            error.set(info, ruleId, left.getLine());
            return false;
        }
        return true;
    }
}
