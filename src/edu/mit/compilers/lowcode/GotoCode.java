package edu.mit.compilers.lowcode;

import edu.mit.compilers.ir.statement.IRStatement;

public class GotoCode extends ThreeAddressCode {
    private boolean isConditional;
    private boolean isIfTrue;
    private String condition;

    // first visit, goto stmt
    private IRStatement gotoStmt;

    // second visit, gotoSTmt -> gotoLabel
    private String gotoLabel;

    @Override
    protected String getStringFroShow(String prefix) {
        if (!isConditional) {
            return prefix + "goto " + gotoLabel;
        }

        return prefix
                + (isIfTrue ? "if" : "ifFalse")
                + " " + condition + " "
                + "goto " + gotoLabel;
    }

    // if/ifFalse condition goto label
    // when isIfTrue is false, mean ifFalse condition goto label <=> if !condition goto label
    public GotoCode(IRStatement gotoStmt, String condition, boolean isIfTrue) {
        this.gotoStmt = gotoStmt;
        this.condition = condition;
        this.isIfTrue = isIfTrue;
        this.isConditional = condition != null;
        assert gotoStmt != null;
        if (gotoStmt.getLowerCodes() != null && !gotoStmt.getLowerCodes().isEmpty()) {
            fillGotoLabel();
        }
    }

    // if condition goto label
    public GotoCode(IRStatement gotoStmt, String condition) {
        this(gotoStmt, condition, true);
    }

    // goto label
    public GotoCode(IRStatement gotoStmt) {
        this(gotoStmt, null);
    }

    public void setGotoStmt(IRStatement gotoStmt) {
        this.gotoStmt = gotoStmt;
    }

    public void fillGotoLabel() {
        if (gotoLabel != null) {
            return;
        }

        assert !gotoStmt.getLowerCodes().isEmpty();
        gotoLabel = gotoStmt.getLowerCodes().front().getLabel();
    }

}
