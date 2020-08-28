package edu.mit.compilers.semantic.checker;

public class PushBlockRule extends SemanticRule {

    @Override
    public boolean doBefore() {
        return true;
    }
}
