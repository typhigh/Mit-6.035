package edu.mit.compilers.lowcode;

import edu.mit.compilers.ir.common.IR;
import edu.mit.compilers.ir.common.IRVisitor;

public class LowerCodeConvertor {

    private class ConvertorVisitor extends IRVisitor<ThreeAddressCodeList> {

        @Override
        public ThreeAddressCodeList visit(IR ir) {
            return null;
        }
    }
}
