package edu.mit.compilers.lowercode;

public class AssignSingleOperand extends ThreeAddressCode {
    private boolean isUnaryOp;
    private boolean isArray;
    private String left;
    private String unaryOp;

    // right may be method name
    // example. a = func1(), right is 'func1', unaryOp = 'call'
    private String right;

    // location, the index of the element
    private String location;

    public AssignSingleOperand(String left, String location, String right, String unaryOp) {
        this.left = left;
        this.location = location;
        this.right = right;
        this.unaryOp = unaryOp;
        this.isUnaryOp = unaryOp != null;
        this.isArray = location != null;
    }

    public AssignSingleOperand(String left, String right, String unaryOp) {
        this(left, null, right, unaryOp);
    }

    public AssignSingleOperand(String left, String right) {
        this(left, right, null);
    }

    @Override
    protected String getStringFroShow(String prefix) {
        String leftLiteral = left;
        if (isArray) {
            left += "[" + location + "]";
        }

        return prefix
                + left + " = "
                + (isUnaryOp ? (unaryOp + " ") : (""))
                + right;
    }


}
