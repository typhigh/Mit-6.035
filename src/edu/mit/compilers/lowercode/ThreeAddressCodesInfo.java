package edu.mit.compilers.lowercode;

import edu.mit.compilers.lowercode.code.ThreeAddressCodeList;
import edu.mit.compilers.utils.Showable;

public class ThreeAddressCodesInfo implements Showable {
    public SymbolTable symbolTable = new SymbolTable();
    public ThreeAddressCodeList codes;

    public String show() {
        assert symbolTable != null && codes != null;
        StringBuilder builder = new StringBuilder();
        builder.append(symbolTable.show());
        builder.append(codes.show());
        return builder.toString();
    }
}
