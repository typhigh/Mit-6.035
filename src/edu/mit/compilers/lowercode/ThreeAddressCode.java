package edu.mit.compilers.lowercode;

import edu.mit.compilers.utils.StringUtils;

public abstract class ThreeAddressCode {

    public final static int PREFIX_LIMIT = 20;
    private String label;
    private ThreeAddressCode gotoCode;

    // if need set label
    private boolean isNeedLabel = false;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public ThreeAddressCode getGotoCode() {
        return gotoCode;
    }

    protected abstract String getStringFroShow(String prefix);

    public void getStringForShow(String prefix, StringBuilder ret) {
        if (label != null) {
            prefix += label + ": ";
        }
        assert prefix.length() <= PREFIX_LIMIT : "prefix must be less than" + PREFIX_LIMIT;
        prefix += StringUtils.repeat(new String(" "), PREFIX_LIMIT - prefix.length());
        ret.append(getStringFroShow(prefix));
    }

    public void setNeedLabelTrue() {
        isNeedLabel = true;
    }

    public boolean isNeedLabel() {
        return isNeedLabel;
    }
}
