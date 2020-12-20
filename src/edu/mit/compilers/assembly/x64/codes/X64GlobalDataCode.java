package edu.mit.compilers.assembly.x64.codes;

public class X64GlobalDataCode extends X64Code {
    private String name;
    private long size;
    private static final long ALIGN_SIZE = 8;

    public X64GlobalDataCode(String name, long size) {
        this.name = name;
        this.size = size;
    }

    @Override
    public boolean isEnableLabel() {
        return false;
    }

    @Override
    protected String getStringFroShow(String prefix) {
        return prefix + ".comm " + name + ", " + size + ", " + ALIGN_SIZE + "\n";
    }
}
