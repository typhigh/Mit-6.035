package edu.mit.compilers.lowercode.code;

public class MethodLeaveCode extends ThreeAddressCode {

    @Override
    protected String getStringFroShow(String prefix) {
        return prefix + "leave";
    }
}
