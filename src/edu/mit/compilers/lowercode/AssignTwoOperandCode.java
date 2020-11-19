package edu.mit.compilers.lowercode;

public class AssignTwoOperandCode extends AssignCode {

    private String right1;
    private String right2;
    private String binaryOp;

    @Override
    protected String getStringFroShow(String prefix) {
        return prefix
                + getName()
                + " = "
                + right1
                + " " + binaryOp + " "
                + right2;
    }

    public AssignTwoOperandCode(String left, String right1, String right2, String binaryOp) {
        super(left);
        this.right1 = right1;
        this.right2 = right2;
        this.binaryOp = binaryOp;
    }
}
