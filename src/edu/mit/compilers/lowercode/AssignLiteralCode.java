package edu.mit.compilers.lowercode;

import edu.mit.compilers.utils.Literal;

public class AssignLiteralCode extends AssignCode {
    private Literal value;

    public AssignLiteralCode(String left, Literal value) {
        super(left);
        this.value = value;
    }

    @Override
    protected String getStringFroShow(String prefix) {
        return prefix + getName() + " = " + value.getLiteralValue();
    }
}
