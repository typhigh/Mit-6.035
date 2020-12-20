package edu.mit.compilers.assembly.common;

import edu.mit.compilers.lowercode.ThreeAddressCodesInfo;

public interface AssemblyConvertor {

    // convertor three address codes into assembly codes
    public void convertToAssembly(ThreeAddressCodesInfo info);

    public AssemblyCodesInfo getResult();

}
