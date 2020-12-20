package edu.mit.compilers.lowercode.code;

public class PushParamCode extends ThreeAddressCode {

    private String variable;

    public PushParamCode(String variable) {
        this.variable = variable;
    }

    public String getVariable() {
        return variable;
    }

    @Override
    protected String getStringFroShow(String prefix) {
        return prefix + "push param " + variable;
    }
}
