package edu.mit.compilers.lowercode.code;

import edu.mit.compilers.lowercode.Operand;
import edu.mit.compilers.lowercode.Variable;

public class AssignTwoOperandCode extends AssignCode {

    private Operand right1;
    private Operand right2;
    private String binaryOp;

    @Override
    protected String getStringFroShow(String prefix) {
        return prefix
                + getLeftString()
                + " = "
                + right1.getLiteralString()
                + " " + binaryOp + " "
                + right2.getLiteralString();
    }

    public AssignTwoOperandCode(Variable variable, Operand right1, Operand right2, String binaryOp) {
        super(variable);
        this.right1 = right1;
        this.right2 = right2;
        this.binaryOp = binaryOp;
    }
}
