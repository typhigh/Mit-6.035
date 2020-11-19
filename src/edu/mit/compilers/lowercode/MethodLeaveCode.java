package edu.mit.compilers.lowercode;

public class MethodLeaveCode extends ThreeAddressCode {

    @Override
    protected String getStringFroShow(String prefix) {
        return prefix + "leave";
    }
}
