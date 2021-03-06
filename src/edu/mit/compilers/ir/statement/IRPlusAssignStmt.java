package edu.mit.compilers.ir.statement;

import edu.mit.compilers.ir.common.IR;
import edu.mit.compilers.ir.common.IRVisitor;
import edu.mit.compilers.ir.expression.IRExpression;
import edu.mit.compilers.ir.expression.IRLocation;
import edu.mit.compilers.utils.StringInfo;

import java.util.ArrayList;

/*
 * two usage:
 * 1. a++, a--
 * 2. a += xxx, a -= xxx
 */
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
    public <T> T accept(IRVisitor<T> visitor) throws CloneNotSupportedException {
        return visitor.visit(this);
    }

    @Override
    public StringInfo getInfoForShow(String prefix) {
        return super.getInfoForShow(prefix).addInfo("Op: " + getOperator());
    }

    @Override
    public IRPlusAssignStmt clone() throws CloneNotSupportedException {
        IRPlusAssignStmt clone = (IRPlusAssignStmt) super.clone();
        if (location != null) {
            clone.location = location.clone();
        }
        if (value != null) {
            clone.value = value.clone();
        }
        return clone;
    }
}
