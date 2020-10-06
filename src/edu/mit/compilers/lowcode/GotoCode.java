package edu.mit.compilers.lowcode;

public class GotoCode extends ThreeAddressCode {
    private boolean isConditional;
    private boolean isIfTrue;
    private String condition;
    private String label;

    @Override
    protected String getStringFroShow(String prefix) {
        if (!isConditional) {
            return prefix + "goto " + label;
        }

        return prefix
                + (isIfTrue ? "if" : "ifFalse")
                + " " + condition + " "
                + "goto " + label;
    }

    // if/ifFalse condition goto label
    // when isIfTrue is false, mean ifFalse condition goto label <=> if !condition goto label
    public GotoCode(String label, String condition, boolean isIfTrue) {
        this.label = label;
        this.condition = condition;
        this.isIfTrue = isIfTrue;
        this.isConditional = condition != null;
    }

    // if condition goto label
    public GotoCode(String label, String condition) {
        this(label, condition, true);
    }

    // goto label
    public GotoCode(String label) {
        this(label, null);
    }
}
