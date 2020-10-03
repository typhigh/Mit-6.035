package edu.mit.compilers.semantic.checker;

import edu.mit.compilers.ir.common.IR;
import edu.mit.compilers.ir.decl.IRMethodDecl;

public class SetCoveredMethodHelper extends SemanticRule {

    @Override
    public boolean doBefore() {
        return true;
    }

    @Override
    public SemanticError visit(IR ir) {
        if (ir instanceof IRMethodDecl) {
            ir.setCoveredByWhichMethod((IRMethodDecl) ir);
        }

        IRMethodDecl coveredBy = ir.getCoveredByWhichMethod();
        if (coveredBy == null) {
            return SemanticError.NoError;
        }

        for (IR child : ir.getChildren()) {
            child.setCoveredByWhichMethod(coveredBy);
        }

        return SemanticError.NoError;
    }
}
