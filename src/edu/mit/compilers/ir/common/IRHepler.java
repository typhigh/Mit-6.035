package edu.mit.compilers.ir.common;

import JunitTest.TestTool.IRMaker;
import edu.mit.compilers.ir.expression.IRBinaryOpExpr;
import edu.mit.compilers.ir.expression.IRExpression;
import edu.mit.compilers.ir.expression.IRLocation;
import edu.mit.compilers.ir.expression.literal.IRIntLiteral;
import edu.mit.compilers.ir.statement.IRAssignStmt;
import edu.mit.compilers.ir.statement.IRPlusAssignStmt;
import edu.mit.compilers.utils.OperatorUtils;

public class IRHepler {
    /*
     * convert IRPlusAssignStmt to IRAssignStmt in equal way
     */
    public static IRAssignStmt convertToAssignStmt(IRPlusAssignStmt ir) throws CloneNotSupportedException {
        String operator = OperatorUtils.convertPlusOpToBinaryOp(ir.getOperator());
        IRLocation location = ir.getLocation();
        boolean isSelfAssign = ir.isSelfAssign();
        IRExpression right = null;
        try {
            // location use clone for a[get()] = a[get()] + 1, two get() may return different value
            if (isSelfAssign) {
                right = new IRBinaryOpExpr(location.clone(), operator, getDefaultPlusValue());
            } else {
                right = new IRBinaryOpExpr(location.clone(), operator, ir.getValue());
            }
        } catch (CloneNotSupportedException e) {
            System.out.println(e.getMessage());
        } finally {
            return new IRAssignStmt(location, right);
        }
    }

    private static IRIntLiteral getDefaultPlusValue() {
        return IRMaker.makeIRIntLiteral("1");
    }
}
