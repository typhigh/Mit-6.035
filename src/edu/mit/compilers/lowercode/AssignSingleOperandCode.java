package edu.mit.compilers.lowercode;

public class AssignSingleOperandCode extends AssignCode {
    private boolean isUnaryOp;
    // unary op like "-" "!", may be null
    private String unaryOp;

    // right is a variable, neither literal value nor method
    private String right;

    public AssignSingleOperandCode(String left, String location, String right, String unaryOp) {
        super(left, location);
        this.right = right;
        this.unaryOp = unaryOp;
        this.isUnaryOp = unaryOp != null;
    }

    public AssignSingleOperandCode(String left, String right, String unaryOp) {
        this(left, null, right, unaryOp);
    }

    public AssignSingleOperandCode(String left, String right) {
        this(left, right, null);
    }

    @Override
    protected String getStringFroShow(String prefix) {
        return prefix
                + getName() + " = "
                + (isUnaryOp ? (unaryOp + " ") : (""))
                + right;
    }


}
