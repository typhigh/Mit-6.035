package edu.mit.compilers.semantic.checker;


import edu.mit.compilers.ir.common.IRVariable;
import edu.mit.compilers.ir.decl.IRMemberDecl;

/*
    1. No identifier is declared twice in the same scope. This includes import identifiers, which
    exist in the global scope.
    2. No identifier is used before it is declared
 */
public class DeclareRule extends SemanticRule {
    @Override
    public SemanticError visit(IRVariable ir) {
        SemanticError error = new SemanticError();
        if (!getEnv().contain(ir)) {
            error.line = ir.getLine();
            error.error = "<id> " + ir.toString() + " should have been declared";
            return error;
        }
        return SemanticError.NoError;
    }

    @Override
    public SemanticError visit(IRMemberDecl ir) {
        getEnv().pushMemberDecl(ir);
        return SemanticError.NoError;
    }

    @Override
    public boolean doBefore() {
        return true;
    }
}
