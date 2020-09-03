package edu.mit.compilers.semantic.checker;

import edu.mit.compilers.ir.expression.literal.IRBoolLiteral;
import edu.mit.compilers.ir.expression.literal.IRCharLiteral;
import edu.mit.compilers.ir.expression.literal.IRIntLiteral;
import edu.mit.compilers.utils.StringUtils;

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
        int value;
        try {
            if (text.contains("0x") || text.contains("0X")) {
                String subText = text.substring(2);
                value = StringUtils.parseHexInt(subText);
            } else {
                value = Integer.valueOf(text);
            }
        } catch (Exception e) {
            SemanticError error = new SemanticError();
            String info = "Int literal value should be fit in 64bits (−9223372036854775808 - 9223372036854775807";
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
