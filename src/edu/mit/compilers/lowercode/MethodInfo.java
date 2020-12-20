package edu.mit.compilers.lowercode;

import edu.mit.compilers.lowercode.code.ThreeAddressCodeList;

import java.util.ArrayList;

public class MethodInfo {
    private String methodName;

    // local field and temporary field
    private ArrayList<FieldInfo> fieldsInfo = new ArrayList<>();

    // filled by lower code convertor
    private ThreeAddressCodeList methodCodes;

    public MethodInfo(String methodName) {
        this.methodName = methodName;
    }

    public String getMethodName() {
        return methodName;
    }

    public ArrayList<FieldInfo> getFieldsInfo() {
        return fieldsInfo;
    }

    public ThreeAddressCodeList getMethodCodes() {
        return methodCodes;
    }

    public void setMethodCodes(ThreeAddressCodeList methodCodes) {
        this.methodCodes = methodCodes;
    }
}
