package edu.mit.compilers.assembly.common;

import edu.mit.compilers.lowercode.ThreeAddressCodeList;
import edu.mit.compilers.lowercode.convertor.ThreeAddressCodesInfo;

public interface AssemblyConvertor {

    public void convertToAssembly(ThreeAddressCodeList lowerCodes, ThreeAddressCodesInfo info);

    public AssemblyCodeList getResult();

}
