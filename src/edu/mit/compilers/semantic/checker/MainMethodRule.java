package edu.mit.compilers.semantic.checker;

import edu.mit.compilers.ir.common.IRProgram;
import edu.mit.compilers.ir.decl.IRMethodDecl;
import edu.mit.compilers.ir.type.IRBasicType;

/*
 *  The program contains a definition for a method called main that has no parameters and returns void
 */
public class MainMethodRule extends SemanticRule {

    @Override
    public boolean doBefore() {
        return true;
    }

    @Override
    public SemanticError visit(IRProgram ir) {
        SemanticError error = new SemanticError();
        boolean hasMain = false;
        IRMethodDecl mainDecl = null;
        for (IRMethodDecl methodDecl : ir.getMethodDecls()) {
            if (methodDecl.getVariable().getName().equals("main")) {
                if (hasMain) {
                    error.line = methodDecl.getLine();
                    error.error = "main method has been declared repeatedly";
                    return error;
                }
                hasMain = true;
                mainDecl = methodDecl;
            }
        }

        if (!hasMain) {
            error.line = 0;
            error.error = "main method has not been declared";
            return error;
        }

        if (!mainDecl.getType().equals(IRBasicType.VoidType)) {
            error.line = mainDecl.getLine();
            error.error = "main method only supports void return type, not the " + mainDecl.getType().getTypeName();
            return error;
        }

        if (!mainDecl.getParaList().isEmpty()) {
            error.line = mainDecl.getLine();
            error.error = "main method only supports empty parameter list";
            return error;
        }

        return SemanticError.NoError;
    }
}
