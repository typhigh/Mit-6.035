package edu.mit.compilers.lowercode.convertor;

import edu.mit.compilers.ir.common.IR;
import edu.mit.compilers.ir.common.IRVisitor;
import edu.mit.compilers.lowercode.ThreeAddressCodeList;

import java.util.ArrayList;

public class LowerCodeConvertor {
    private final SymbolTable symbolTable = new SymbolTable();
    private final SymbolTableSetter symbolTableSetter;
    private final LowerCodeConvertorVisitor visitor;
    private ThreeAddressCodeList result;

    public LowerCodeConvertor() {
        this.symbolTableSetter = new SymbolTableSetter(symbolTable);
        this.visitor = new LowerCodeConvertorVisitor();
    }

    public void ConvertToLowCode(IR tree) {
        assert tree.getParent() == null;

        // previous process before convertion
        ArrayList<IRVisitor<Void>> processors = new ArrayList<>();
        processors.add(new SymbolTableSetter(symbolTable));
        processors.add(new NextStmtSetter());
        PreProcessor preProcessor = new PreProcessor(processors);
        preProcessor.process(tree);
        ConvertToLowCodeImpl(tree);
        result = tree.getLowerCodes();
    }

    public SymbolTable getSymbolTable() {
        return symbolTable;
    }

    public ThreeAddressCodeList getResult() {
        return result;
    }

    private void ConvertToLowCodeImpl(IR ir) {
        ir.accept(visitor);
    }
}
