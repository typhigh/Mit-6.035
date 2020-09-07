package edu.mit.compilers.semantic.checker;

// TODO: maybe do it
/*
 * Push negtive op "-" to IntLiteral x if x is not negtive
 * - 111  => -111
 *  We do this to make literal value rule work
 * − 9223372036854775808 can't accept the rule but −9223372036854775808 can
 */
public class PushNegToIntLiteralHelper extends SemanticRule {

    @Override
    public boolean doBefore() {
        return true;
    }

}
