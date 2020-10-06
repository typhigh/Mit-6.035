package edu.mit.compilers.lowcode;

public class AssignTwoOperand extends ThreeAddressCode {

    private String left;
    private String right1;
    private String right2;
    private String binaryOp;

    @Override
    protected String getStringFroShow(String prefix) {
        return prefix
                + left
                + " = "
                + right1
                + " " + binaryOp + " "
                + right2;
    }

    public AssignTwoOperand(String left, String right1, String right2, String binaryOp) {
        this.left = left;
        this.right1 = right1;
        this.right2 = right2;
        this.binaryOp = binaryOp;
    }
}
