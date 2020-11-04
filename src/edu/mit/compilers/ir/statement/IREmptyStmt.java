package edu.mit.compilers.ir.statement;

import edu.mit.compilers.utils.StringInfo;

public class IREmptyStmt extends IRStatement {

    public IREmptyStmt() {
        super("IREmptyStmt");
    }

    @Override
    public StringInfo getInfoForShow(String prefix) {
        // no need for show
        return StringInfo.getIgnoredInfo();
    }
}
