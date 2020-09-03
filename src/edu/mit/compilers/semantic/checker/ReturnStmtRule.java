package edu.mit.compilers.semantic.checker;

import edu.mit.compilers.ir.decl.IRMethodDecl;
import edu.mit.compilers.ir.statement.IRReturnStmt;

/*
 * 9. The expression in a return statement must have the same type as the declared result type
 * of the enclosing method definition.
 */
public class ReturnStmtRule extends SemanticRule {

    @Override
    public boolean doBefore() {
        return false;
    }

    @Override
    public SemanticError visit(IRReturnStmt ir) {
        SemanticError error = new SemanticError();
        IRMethodDecl method = ir.getCoveredByWhichMethod();
        TypeHelper.checkExpressionType(getEnv(), error, 9, ir.getExpr(), method.getType());
        return error;
    }
}
