package edu.mit.compilers.assembly.x64;

import edu.mit.compilers.assembly.common.AssemblyCodesInfo;
import edu.mit.compilers.assembly.x64.codes.X64CodeList;

public class X64CodesInfo extends AssemblyCodesInfo {
    public X64CodeList codes;

    @Override
    public String show() {
        return codes.show();
    }
}
