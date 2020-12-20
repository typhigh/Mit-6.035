package edu.mit.compilers.lowercode.code;

public class GotoCode extends ThreeAddressCode {
    private boolean isConditional;
    private boolean isIfTrue;
    private String condition;

    // first visit, goto stmt
    private ThreeAddressCodeList gotoStmtCodes;

    // second visit, gotoStmt -> gotoLabel
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
    public GotoCode(ThreeAddressCodeList gotoStmtCodes, String condition, boolean isIfTrue) {
        this.gotoStmtCodes = gotoStmtCodes;
        this.condition = condition;
        this.isIfTrue = isIfTrue;
        this.isConditional = condition != null;
        assert gotoStmtCodes != null;
        gotoStmtCodes.setNeedLabelTrue();
    }

    // if condition goto label
    public GotoCode(ThreeAddressCodeList gotoStmtCodes, String condition) {
        this(gotoStmtCodes, condition, true);
    }

    // goto label
    public GotoCode(ThreeAddressCodeList gotoStmtCodes) {
        this(gotoStmtCodes, null);
    }

    public void setGotoStmtCodes(ThreeAddressCodeList gotoStmtCodes) {
        this.gotoStmtCodes = gotoStmtCodes;
    }

    public String getGotoLabel() {
        return gotoLabel;
    }

    public void setGotoLabel(String gotoLabel) {
        this.gotoLabel = gotoLabel;
    }

    public void fillGotoLabel() {
        if (gotoLabel != null) {
            return;
        }

        assert !gotoStmtCodes.isNull();
        gotoLabel = gotoStmtCodes.front().getLabel();
    }

}
