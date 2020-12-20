package edu.mit.compilers.assembly.x64.codes;

public class X64GlobalDataCode extends X64Code {
    private String name;
    private long size;
    private static final long ALIGN_SIZE = 16;

    public X64GlobalDataCode(String name, long size) {
        this.name = name;
        this.size = size;
    }


}
