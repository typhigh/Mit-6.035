package edu.mit.compilers.lowcode.convertor;

import edu.mit.compilers.ir.common.IR;
import edu.mit.compilers.ir.common.IRVisitor;
import edu.mit.compilers.lowcode.ThreeAddressCodeList;

public class LowerCodeConvertorVisitor extends IRVisitor<ThreeAddressCodeList> {

    // TODO: avoid visit unused ir
    // default visit func
    @Override
    public ThreeAddressCodeList visit(IR ir) {
        ThreeAddressCodeList ret = new ThreeAddressCodeList();
        for (IR child : ir.getChildren()) {
            ret.append(visit(child));
        }
        return ret;
    }
}
