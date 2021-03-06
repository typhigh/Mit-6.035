package edu.mit.compilers.lowercode.code;

public class MethodCallCode extends ThreeAddressCode {
    private boolean assign;
    private String variable;
    private String methodName;

    public MethodCallCode(String methodName) {
        this(null, methodName);
    }

    public MethodCallCode(String variable, String methodName) {
        this.variable = variable;
        this.methodName = methodName;
        this.assign = variable != null;
    }

    @Override
    protected String getStringFroShow(String prefix) {
        if (assign) {
            prefix += variable + " = ";
        }
        return prefix + "call-method " + methodName;
    }
}
