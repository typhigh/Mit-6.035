package edu.mit.compilers.lowercode;

public class EmptyCode extends ThreeAddressCode {

    @Override
    protected String getStringFroShow(String prefix) {
        return prefix;
    }
}
