package edu.mit.compilers.semantic.checker;

import edu.mit.compilers.ir.expression.literal.IRBoolLiteral;
import edu.mit.compilers.ir.expression.literal.IRCharLiteral;
import edu.mit.compilers.ir.expression.literal.IRIntLiteral;
import edu.mit.compilers.utils.LiteralHelper;

/*
 * All integer literals must be in the range −9223372036854775808 ≤ x ≤ 9223372036854775807
 * (64 bits).
 */
public class LiteralValueRule extends SemanticRule {

    @Override
    public boolean doBefore() {
        return true;
    }

    @Override
    public SemanticError visit(IRIntLiteral ir) {
        String literalValue = ir.getLiteralValue();
        long value;
        try {
            value = LiteralHelper.parseIntLiteral(literalValue);
        } catch (Exception e) {
            SemanticError error = new SemanticError();
            String info = "<Int literal value> " + literalValue +
                    " should be fit in 64bits (−9223372036854775808 - 9223372036854775807)";
            error.set(info, 20, ir.getLine(), ir.getColumn());
            return error;
        }
        ir.setValue(value);
        return SemanticError.NoError;
    }

    @Override
    public SemanticError visit(IRCharLiteral ir) {
        String literalValue = ir.getLiteralValue();
        char value = LiteralHelper.parseCharLiteral(literalValue);
        ir.setValue(value);
        return SemanticError.NoError;
    }

    @Override
    public SemanticError visit(IRBoolLiteral ir) {
        ir.setValue(Boolean.valueOf(ir.getLiteralValue()));
        return SemanticError.NoError;
    }
}
