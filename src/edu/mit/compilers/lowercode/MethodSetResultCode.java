package edu.mit.compilers.lowercode;

public class MethodSetResultCode extends ThreeAddressCode {

    private String variable;

    public MethodSetResultCode(String variable) {
        this.variable = variable;
    }

    @Override
    protected String getStringFroShow(String prefix) {
        return prefix + "set-result " + variable;
    }
}
