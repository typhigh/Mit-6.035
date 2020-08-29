package edu.mit.compilers.semantic.checker;

import edu.mit.compilers.ir.common.IRBlock;
import edu.mit.compilers.ir.decl.IRMethodDecl;

public class PushBlockRule extends SemanticRule {

    @Override
    public boolean doBefore() {
        return true;
    }

    @Override
    public SemanticError visit(IRBlock ir) {
        if (ir.isNewScope()) {
            getEnv().pushBlock();
        }
        return SemanticError.NoError;
    }

    @Override
    public SemanticError visit(IRMethodDecl ir) {
        getEnv().pushBlock();
        // avoid pushing block repeatedly
        ir.getBlock().setNewScope(false);
        return SemanticError.NoError;
    }
}
