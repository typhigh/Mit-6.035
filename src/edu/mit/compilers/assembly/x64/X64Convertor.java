package edu.mit.compilers.assembly.x64;

import edu.mit.compilers.assembly.common.AssemblyConvertor;
import edu.mit.compilers.lowercode.ThreeAddressCodeList;
import edu.mit.compilers.lowercode.convertor.ThreeAddressCodesInfo;

public class X64Convertor implements AssemblyConvertor {

    private X64CodeList result;

    @Override
    public void convertToAssembly(ThreeAddressCodeList lowerCodes, ThreeAddressCodesInfo info) {

    }

    @Override
    public X64CodeList getResult() {
        return result;
    }
}
