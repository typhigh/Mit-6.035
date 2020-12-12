package edu.mit.compilers.lowercode;

import edu.mit.compilers.utils.Literal;

public class Value extends Operand {
    public Literal value;

    public Value(Literal value) {
        this.value = value;
    }

    @Override
    public String getLiteralString() {
        return value.getLiteralValue();
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
