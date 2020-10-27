package edu.mit.compilers.lowcode.convertor;

import edu.mit.compilers.ir.common.IR;
import edu.mit.compilers.ir.common.IRVisitor;
import edu.mit.compilers.lowcode.ThreeAddressCodeList;

public class LowerCodeConvertorVisitor extends IRVisitor<ThreeAddressCodeList> {

    @Override
    public ThreeAddressCodeList visit(IR ir) {
        return null;
    }


}
