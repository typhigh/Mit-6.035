package edu.mit.compilers.lowercode;

public class AssignSingleOperandCode extends AssignCode {
    private boolean isUnaryOp;

    // unary op like "-" "!", may be null
    private String unaryOp;

    // right is a variable, neither literal value nor method
    private Operand right;

    public AssignSingleOperandCode(Variable left, Operand location, Operand right, String unaryOp) {
        super(left, location);
        this.right = right;
        this.unaryOp = unaryOp;
        this.isUnaryOp = unaryOp != null;
    }

    public AssignSingleOperandCode(Variable left, Operand right, String unaryOp) {
        this(left, null, right, unaryOp);
    }

    public AssignSingleOperandCode(Variable left, Operand right) {
        this(left, right, null);
    }

    @Override
    protected String getStringFroShow(String prefix) {
        return prefix
                + getLeftString() + " = "
                + (isUnaryOp ? (unaryOp + " ") : (""))
                + right.getLiteralString();
    }


}
