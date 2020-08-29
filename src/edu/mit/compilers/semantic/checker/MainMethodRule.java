package edu.mit.compilers.semantic.checker;

import edu.mit.compilers.ir.common.IRProgram;
import edu.mit.compilers.ir.decl.IRMethodDecl;

public class MainMethodRule extends SemanticRule {

    @Override
    public boolean doBefore() {
        return true;
    }

    @Override
    public SemanticError visit(IRProgram ir) {
        SemanticError error = new SemanticError();
        boolean hasMain = false;
        for (IRMethodDecl methodDecl : ir.getMethodDecls()) {
            if (methodDecl.getVariable().getName().equals("main")) {
                if (hasMain) {
                    error.line = methodDecl.getLine();
                    error.error = "main method has been declared repeatedly";
                    return error;
                }
                hasMain = true;
            }
        }

        if (!hasMain) {
            error.line = 0;
            error.error = "main method has not been declared";
            return error;
        }
        return SemanticError.NoError;
    }
}
