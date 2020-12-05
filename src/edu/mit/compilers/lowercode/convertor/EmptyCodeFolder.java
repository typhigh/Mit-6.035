package edu.mit.compilers.lowercode.convertor;

import edu.mit.compilers.lowercode.ThreeAddressCodeList;
import edu.mit.compilers.lowercode.ThreeAddressCodeListProcessor;

public class EmptyCodeFolder implements ThreeAddressCodeListProcessor<Void> {

    @Override
    public Void process(ThreeAddressCodeList codes) {
        return null;
    }
}
