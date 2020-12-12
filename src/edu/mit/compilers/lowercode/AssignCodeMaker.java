package edu.mit.compilers.lowercode;

import edu.mit.compilers.utils.Literal;

public class AssignCodeMaker {

    public static AssignSingleOperandCode makeAssign(Variable variable, Operand operand) {
        return new AssignSingleOperandCode(variable, operand);
    }

    // maybe location is null
    public static AssignSingleOperandCode makeAssignWithLeftLocation(Variable variable,
                                                                     Operand location,
                                                                     Operand right) {
        return new AssignSingleOperandCode(variable, location, right, null);
    }

    public static AssignSingleOperandCode makeAssignWithRightLocation(Variable variable,
                                                                      Variable right,
                                                                      Operand location) {
        return new AssignSingleOperandCode(variable, null, new LocationVariable(right, location), null);
    }

    public static AssignSingleOperandCode makeAssignLiteralCode(Variable variable, Literal value) {
        return new AssignSingleOperandCode(variable, new Value(value));
    }

    public static AssignSingleOperandCode makeAssignWithUnaryOp(Variable variable, Operand operand, String op) {
        return new AssignSingleOperandCode(variable, operand, op);
    }

    public static AssignTwoOperandCode makeAssignWithBinaryOp(Variable variable,
                                                              Operand operand1,
                                                              Operand operand2,
                                                              String op) {
        return new AssignTwoOperandCode(variable, operand1, operand2, op);
    }
}
