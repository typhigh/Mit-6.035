package edu.mit.compilers.assembly.x64;

import edu.mit.compilers.assembly.common.AssemblyConvertor;
import edu.mit.compilers.lowercode.SymbolTable;
import edu.mit.compilers.lowercode.ThreeAddressCodesInfo;
import edu.mit.compilers.lowercode.code.ThreeAddressCodeList;

public class X64Convertor implements AssemblyConvertor {

    private X64CodesInfo result;
    private SymbolTable symbolTable;
    @Override
    public void convertToAssembly(ThreeAddressCodesInfo info) {
        ThreeAddressCodeList codes = info.codes;
        symbolTable = info.symbolTable;

    }

    @Override
    public X64CodesInfo getResult() {
        return result;
    }
}
