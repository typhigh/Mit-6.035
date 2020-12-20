package edu.mit.compilers.assembly.x64.codes;

import edu.mit.compilers.assembly.common.AssemblyCodeList;
import edu.mit.compilers.lowercode.FieldInfo;
import edu.mit.compilers.lowercode.SymbolTable;
import edu.mit.compilers.lowercode.code.ThreeAddressCodeList;

public class X64CodeList implements AssemblyCodeList {

    @Override
    public String show() {
        return null;
    }

    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public X64Code next() {
        return null;
    }

    public void generateDataArea(SymbolTable symbolTable) {
        // init global field
        for (FieldInfo fieldInfo : symbolTable.getGlobalFieldsInfo()) {
            if (fieldInfo.isDeleted()) {
                continue;
            }
            long size = fieldInfo.getSize();

        }
    }

    public void generateStringArea(SymbolTable symbolTable) {
        // init (const) string field
        for (FieldInfo fieldInfo : symbolTable.getConstStringInfo()) {

        }
    }

    public void generateTextArea(ThreeAddressCodeList codes) {

    }
}
