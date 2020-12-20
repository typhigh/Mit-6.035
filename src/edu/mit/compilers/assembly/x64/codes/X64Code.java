package edu.mit.compilers.assembly.x64.codes;

import edu.mit.compilers.assembly.common.AssemblyCode;
import edu.mit.compilers.utils.StringUtils;

public abstract class X64Code implements AssemblyCode {
    public static final int PREFIX_LIMIT = 20;
    private String label;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public boolean hasLabel() {
        return label != null;
    }

    public abstract boolean isEnableLabel();

    @Override
    public void getStringForShow(String prefix, StringBuilder ret) {
        if (label != null) {
            assert isEnableLabel();
            prefix += label + ": ";
        }
        assert prefix.length() <= PREFIX_LIMIT : "prefix must be less than" + PREFIX_LIMIT;
        prefix += StringUtils.repeat(new String(" "), PREFIX_LIMIT - prefix.length());
        ret.append(getStringFroShow(prefix));
    }

    protected abstract String getStringFroShow(String prefix);

}

