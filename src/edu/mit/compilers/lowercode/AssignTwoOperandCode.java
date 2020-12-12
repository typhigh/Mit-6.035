package edu.mit.compilers.lowercode;

public class AssignTwoOperandCode extends AssignCode {

    private Operand right1;
    private Operand right2;
    private String binaryOp;

    @Override
    protected String getStringFroShow(String prefix) {
        return prefix
                + getLeftString()
                + " = "
                + right1
                + " " + binaryOp + " "
                + right2;
    }

    public AssignTwoOperandCode(Variable variable, Operand right1, Operand right2, String binaryOp) {
        super(variable);
        this.right1 = right1;
        this.right2 = right2;
        this.binaryOp = binaryOp;
    }
}
