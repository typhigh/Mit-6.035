package edu.mit.compilers.semantic.checker;

import edu.mit.compilers.ir.common.IRArgumentList;
import edu.mit.compilers.ir.decl.IRMemberDecl;
import edu.mit.compilers.ir.expression.IRExpression;
import edu.mit.compilers.ir.expression.IRMethodCall;
import edu.mit.compilers.ir.type.IRBasicType;
import edu.mit.compilers.ir.type.IRType;

/*
 * 7. String literals and array variables may not be used as arguments to non-import methods.
 */
public class ArgumentRule extends SemanticRule {

    @Override
    public boolean doBefore() {
        return false;
    }

    @Override
    public SemanticError visit(IRArgumentList ir) {
        SemanticError error = new SemanticError();
        IRMemberDecl declaredFrom = ((IRMethodCall) ir.getParent()).getVariable().getDeclaredFrom();
        if (declaredFrom.getTag().equals("IRImportDecl")) {
            return SemanticError.NoError;
        }

        for (int i = 0; i < ir.getArgList().size(); ++i) {
            IRExpression arg = ir.getArgList().get(i);
            IRType type = arg.getType();
            if (type.equals(IRBasicType.StringType) || type.isArrayType()) {
                error.line = arg.getLine();
                error.error = "the " + i + "th <argument> type is not support(string, array)";
                return error;
            }
        }

        return SemanticError.NoError;
    }
}
