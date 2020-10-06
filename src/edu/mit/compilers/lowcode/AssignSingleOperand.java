package edu.mit.compilers.lowcode;

public class AssignSingleOperand extends ThreeAddressCode {
    private boolean isUnaryOp;
    private String left;
    private String unaryOp;

    // right may be method name
    // example. a = func1(), right is 'func1', unaryOp = 'call'
    private String right;

    public AssignSingleOperand(String left, String right, String unaryOp) {
        this.left = left;
        this.right = right;
        this.unaryOp = unaryOp;
        this.isUnaryOp = unaryOp != null;
    }

    public AssignSingleOperand(String left, String right) {
        this(left, right, null);
    }

    @Override
    protected String getStringFroShow(String prefix) {
        return prefix
                + left + " = "
                + (isUnaryOp ? (unaryOp + " ") : (""))
                + right;
    }


}
