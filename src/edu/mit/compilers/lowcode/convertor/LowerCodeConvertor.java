package edu.mit.compilers.lowcode.convertor;

import edu.mit.compilers.ir.common.IR;

public class LowerCodeConvertor {
    private final SymbolTable symbolTable = new SymbolTable();
    private final SymbolTableSetter symbolTableSetter;
    private final LowerCodeConvertorVisitor visitor;

    public LowerCodeConvertor() {
        this.symbolTableSetter = new SymbolTableSetter(symbolTable);
        this.visitor = new LowerCodeConvertorVisitor();
    }

    public void ConvertToLowCode(IR tree) {
        assert tree.getParent() == null;
        ConvertToLowCodeImpl(tree);
    }

    private void ConvertToLowCodeImpl(IR ir) {
        symbolTableSetter.visit(ir);
        for (IR child : ir.getChildren()) {
            ConvertToLowCodeImpl(child);
        }
        visitor.visit(ir);
    }
}
