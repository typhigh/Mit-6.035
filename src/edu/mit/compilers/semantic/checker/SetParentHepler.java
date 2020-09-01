package edu.mit.compilers.semantic.checker;

import edu.mit.compilers.ir.common.IR;

public class SetParentHepler extends SemanticRule {

    @Override
    public boolean doBefore() {
        return true;
    }

    @Override
    public SemanticError visit(IR ir) {
        for (IR child : ir.getChildren()) {
            child.setParent(ir);
        }
        return SemanticError.NoError;
    }
}
