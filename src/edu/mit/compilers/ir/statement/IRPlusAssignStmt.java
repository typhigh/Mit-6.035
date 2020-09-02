package edu.mit.compilers.ir.statement;

import edu.mit.compilers.ir.common.IR;
import edu.mit.compilers.ir.common.IRVisitor;
import edu.mit.compilers.ir.expression.IRExpression;
import edu.mit.compilers.ir.expression.IRLocation;

import java.util.ArrayList;

public class IRPlusAssignStmt extends IRStatement {

    private IRLocation location;
    private String operator;
    private IRExpression value;

    public IRPlusAssignStmt(IRLocation location, String operator, IRExpression value) {
        super("IRPlusAssignStmt");
        this.location = location;
        this.operator = operator;
        this.value = value;
    }

    public IRPlusAssignStmt(IRLocation location, String operator) {
        this(location, operator, null);
    }

    public boolean isSelfAssign() {
        return value == null;
    }

    public IRLocation getLocation() {
        return location;
    }

    public String getOperator() {
        return operator;
    }

    public IRExpression getValue() {
        return value;
    }

    @Override
    public ArrayList<IR> getChildren() {
        ArrayList<IR> children = new ArrayList<>();
        children.add(location);
        if (!isSelfAssign()) {
            children.add(value);
        }
        return children;
    }

    @Override
    public <T> T accept(IRVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String getInfoForShow(String prefix) {
        return prefix +
                " DebugID: " + getDebugID() +
                " Tag: " + getTag() +
                " Op: " + getOperator() + '\n';
    }
}
