package edu.mit.compilers.semantic.checker;


import edu.mit.compilers.ir.common.IRVariable;
import edu.mit.compilers.ir.decl.IRMemberDecl;

/*
 *  1. No identifier is declared twice in the same scope. This includes import identifiers, which
 *  exist in the global scope.
 *  2. No identifier is used before it is declared
 *  10. An <id> used as a <location> must name a declared local/global variable or formal parameter.
 */
public class DeclareRule extends SemanticRule {
    @Override
    public SemanticError visit(IRVariable ir) {
        SemanticError error = new SemanticError();
        IRMemberDecl declaredFrom = getEnv().seek(ir);
        if (declaredFrom == null) {
            String info = "<identifier> " + ir.getName() + " is used without been declared";
            error.set(info, 2, ir.getLine(), ir.getColumn());
            return error;
        }

        ir.setDeclaredFrom(declaredFrom);
        return SemanticError.NoError;
    }

    @Override
    public SemanticError visit(IRMemberDecl ir) {
        SemanticError error;
        IRMemberDecl decl = getEnv().lastDeclaredInCurrentBlock(ir.getVariable());
        if (decl != null) {
            // twice declared in one scope
            error = new SemanticError();
            String info = "<identifier> " + ir.getVariable().getName() + " is declared repeatedly in the same scope";
            error.set(info, 1, ir.getLine(), ir.getColumn());
        } else {
            getEnv().pushMemberDecl(ir);
            error = SemanticError.NoError;
        }
        return error;
    }

    @Override
    public boolean doBefore() {
        return true;
    }
}
