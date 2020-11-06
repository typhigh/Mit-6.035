package edu.mit.compilers.ir.statement;

import edu.mit.compilers.ir.common.IR;
import edu.mit.compilers.ir.common.IRVisitor;
import edu.mit.compilers.utils.StringInfo;

import java.util.ArrayList;

public class IREmptyStmt extends IRStatement {

    public IREmptyStmt() {
        super("IREmptyStmt");
    }

    @Override
    public StringInfo getInfoForShow(String prefix) {
        // no need for show
        return StringInfo.getIgnoredInfo();
    }

    @Override
    public ArrayList<IR> getChildren() {
        return new ArrayList<>();
    }

    @Override
    public <T> T accept(IRVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
