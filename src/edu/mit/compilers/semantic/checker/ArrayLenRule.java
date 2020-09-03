package edu.mit.compilers.semantic.checker;

import edu.mit.compilers.ir.expression.literal.IRIntLiteral;
import edu.mit.compilers.ir.type.IRArrayType;

/*
 *  4. The <int_literal> in an array declaration must be greater than 0
 */
public class ArrayLenRule extends SemanticRule {

    @Override
    public boolean doBefore() {
        return false;
    }

    @Override
    public SemanticError visit(IRArrayType ir) {
        SemanticError error;
        IRIntLiteral len = ir.getLen();
        int value = len.getValue();
        if (value <= 0) {
            error = new SemanticError();
            String info = "the <len> " + value + " in array declaration must be greater than 0";
            error.set(info, 4, len.getLine(), len.getColumn());
        } else {
            ir.setSize(value * ir.getBasicType().getSize());
            error = SemanticError.NoError;
        }
        return error;
    }
}
