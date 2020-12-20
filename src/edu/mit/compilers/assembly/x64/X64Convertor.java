package edu.mit.compilers.assembly.x64;

import edu.mit.compilers.assembly.common.AssemblyConvertor;
import edu.mit.compilers.assembly.x64.codes.X64CodeList;
import edu.mit.compilers.assembly.x64.codes.X64ConstStringCode;
import edu.mit.compilers.assembly.x64.codes.X64GlobalDataCode;
import edu.mit.compilers.lowercode.FieldInfo;
import edu.mit.compilers.lowercode.SymbolTable;
import edu.mit.compilers.lowercode.ThreeAddressCodesInfo;
import edu.mit.compilers.lowercode.code.ThreeAddressCodeList;

public class X64Convertor implements AssemblyConvertor {

    private X64CodesInfo result = new X64CodesInfo();
    private SymbolTable symbolTable;
    private X64CodeList codes;

    @Override
    public void convertToAssembly(ThreeAddressCodesInfo info) {
        convertToAssemblyImpl(info.symbolTable, info.codes);
    }

    @Override
    public X64CodesInfo getResult() {
        return result;
    }

    private void convertToAssemblyImpl(SymbolTable symbolTable, ThreeAddressCodeList lowerCodes) {
        codes = new X64CodeList();
        generateDataArea(symbolTable);
        generateStringArea(symbolTable);
        generateTextArea(lowerCodes);
        result.codes = codes;
    }

    private void generateDataArea(SymbolTable symbolTable) {
        // init global field
        for (FieldInfo fieldInfo : symbolTable.getGlobalFieldsInfo()) {
            assert !fieldInfo.isDeleted();
            long size = fieldInfo.getSize();
            codes.append(new X64GlobalDataCode(fieldInfo.getName(), size));
        }
    }

    private void generateStringArea(SymbolTable symbolTable) {
        // init (const) string field
        for (FieldInfo fieldInfo : symbolTable.getConstStringInfo()) {
            assert !fieldInfo.isDeleted();
            assert fieldInfo.getVariable().hasConstValue();
            codes.append(new X64ConstStringCode(
                    fieldInfo.getName(),
                    fieldInfo.getVariable().getConstValue().getLiteralString()));
        }
    }

    private void generateTextArea(ThreeAddressCodeList codes) {

    }

}
