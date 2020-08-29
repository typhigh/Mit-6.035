package edu.mit.compilers.semantic.checker;

import edu.mit.compilers.ir.expression.IRMethodCall;

public class MethodCallRule extends SemanticRule {

    @Override
    public boolean doBefore() {
        return true;
    }

    @Override
    public SemanticError visit(IRMethodCall ir) {
        assert false;
        return null;
    }
}
