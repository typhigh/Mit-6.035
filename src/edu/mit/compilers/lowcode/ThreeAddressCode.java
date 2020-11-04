package edu.mit.compilers.lowcode;

public abstract class ThreeAddressCode {

    private String label;
    private ThreeAddressCode gotoCode;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public ThreeAddressCode getGotoCode() {
        return gotoCode;
    }

    public void setGotoCode(ThreeAddressCode gotoCode) {
        this.gotoCode = gotoCode;
    }

    protected abstract String getStringFroShow(String prefix);

    public void getStringForShow(String prefix, StringBuilder ret) {
        if (label != null) {
            prefix += label + ": ";
        }
        ret.append(getStringFroShow(prefix));
    }
}
