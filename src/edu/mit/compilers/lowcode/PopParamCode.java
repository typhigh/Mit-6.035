package edu.mit.compilers.lowcode;

public class PopParamCode extends ThreeAddressCode {

    private String variable;

    public PopParamCode(String variable) {
        this.variable = variable;
    }

    public String getVariable() {
        return variable;
    }

    @Override
    protected String getStringFroShow(String prefix) {
        return prefix + "pop param " + variable;
    }
}
