package edu.mit.compilers.lowcode;

public class GotoCode extends ThreeAddressCode {
    private boolean isConditional;
    private boolean isIfTrue;
    private String condition;
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
    public GotoCode(String gotoLabel, String condition, boolean isIfTrue) {
        this.gotoLabel = gotoLabel;
        this.condition = condition;
        this.isIfTrue = isIfTrue;
        this.isConditional = condition != null;
    }

    // if condition goto label
    public GotoCode(String gotoLabel, String condition) {
        this(gotoLabel, condition, true);
    }

    // goto label
    public GotoCode(String gotoLabel) {
        this(gotoLabel, null);
    }
}
