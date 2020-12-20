package edu.mit.compilers.lowercode.code;

/*
 * like empty code ,the label is the method name
 */
public class MethodBeginCode extends ThreeAddressCode {

    @Override
    protected String getStringFroShow(String prefix) {
        return prefix;
    }

    public MethodBeginCode(String methodName) {
        setLabel(methodName);
    }

}
