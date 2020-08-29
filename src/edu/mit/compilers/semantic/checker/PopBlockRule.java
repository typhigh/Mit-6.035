package edu.mit.compilers.semantic.checker;

import edu.mit.compilers.ir.common.IRBlock;

public class PopBlockRule extends SemanticRule {

    @Override
    public boolean doBefore() {
        return false;
    }

    @Override
    public SemanticError visit(IRBlock ir) {
        getEnv().popBlock();
        return SemanticError.NoError;
    }
}
