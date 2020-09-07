package edu.mit.compilers.semantic.checker;

import edu.mit.compilers.ir.expression.literal.IRBoolLiteral;
import edu.mit.compilers.ir.expression.literal.IRCharLiteral;
import edu.mit.compilers.ir.expression.literal.IRIntLiteral;

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
        String text = ir.getLiteralValue();
        long value;
        try {
            String rewrite = text.toLowerCase();
            int radix = 10;
            for (int i = 0; i + 1 < rewrite.length(); ++i) {
                if (rewrite.charAt(i) == '0' && rewrite.charAt(i+1) == 'x') {
                    radix = 16;
                    rewrite = rewrite.substring(0, i) + rewrite.substring(i+2);
                    break;
                }
            }
            value = Long.parseLong(rewrite, radix);
        } catch (Exception e) {
            SemanticError error = new SemanticError();
            String info = "<Int literal value> " + text +
                    " should be fit in 64bits (−9223372036854775808 - 9223372036854775807)";
            error.set(info, 20, ir.getLine(), ir.getColumn());
            return error;
        }
        ir.setValue(value);
        return SemanticError.NoError;
    }

    @Override
    public SemanticError visit(IRCharLiteral ir) {
        String line = ir.getLiteralValue();
        char value = line.charAt(0);
        if (value == '\\') {
            switch (line) {
                case "\\n":
                    value = '\n';
                    break;
                case "\\r":
                    value = '\r';
                    break;
                case "\\t":
                    value = '\t';
                    break;
                default:
                    value = line.charAt(1);
                    break;
            }
        }
        ir.setValue(value);
        return SemanticError.NoError;
    }

    @Override
    public SemanticError visit(IRBoolLiteral ir) {
        ir.setValue(Boolean.valueOf(ir.getLiteralValue()));
        return SemanticError.NoError;
    }
}
