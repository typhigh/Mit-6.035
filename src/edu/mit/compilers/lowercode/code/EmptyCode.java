package edu.mit.compilers.lowercode.code;

public class EmptyCode extends ThreeAddressCode {

    @Override
    protected String getStringFroShow(String prefix) {
        return prefix;
    }
}
