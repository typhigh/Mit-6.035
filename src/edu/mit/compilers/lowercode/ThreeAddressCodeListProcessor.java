package edu.mit.compilers.lowercode;

import edu.mit.compilers.lowercode.code.ThreeAddressCodeList;

public interface ThreeAddressCodeListProcessor<T> {
    public T process(ThreeAddressCodeList codes);

}
