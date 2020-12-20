package edu.mit.compilers.assembly.x64.codes;

public class X64ConstStringCode extends X64Code {
    private String name;
    private String constString;

    public X64ConstStringCode(String name, String constString) {
        this.name = name;
        this.constString = constString;
    }
}
