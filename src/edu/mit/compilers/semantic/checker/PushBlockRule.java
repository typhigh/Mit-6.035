package edu.mit.compilers.semantic.checker;

import edu.mit.compilers.ir.common.IRBlock;
import edu.mit.compilers.ir.common.IRParameterList;
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
    public SemanticError visit(IRParameterList ir) {
        // use IRParameterList to make sure (push method decl) before now
        getEnv().pushBlock();
        // avoid pushing block repeatedly
        ((IRMethodDecl) ir.getParent()).getBlock().setNewScope(false);
        return SemanticError.NoError;
    }
}
