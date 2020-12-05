package edu.mit.compilers.lowercode.convertor;

import edu.mit.compilers.ir.common.IR;
import edu.mit.compilers.ir.common.IRVisitor;
import edu.mit.compilers.ir.statement.IRAssignStmt;
import edu.mit.compilers.ir.statement.IRPlusAssignStmt;
import edu.mit.compilers.lowercode.ThreeAddressCodeList;

import java.util.ArrayList;
import java.util.HashMap;

public class LowerCodeConvertor {

    private final ThreeAddressCodesInfo info = new ThreeAddressCodesInfo();

    private final SymbolTable symbolTable = info.symbolTable;
    // replacer map : plus assign -> assign (a++ -> a = a + 1)
    private final HashMap<IRPlusAssignStmt, IRAssignStmt> replacer = new HashMap<>();
    private final SymbolTableSetter symbolTableSetter;
    private final LowerCodeConvertorVisitor visitor;
    private ThreeAddressCodeList result;

    public LowerCodeConvertor() {
        this.symbolTableSetter = new SymbolTableSetter(symbolTable);
        this.visitor = new LowerCodeConvertorVisitor(replacer);
    }

    public void convertToLowCode(IR tree) throws CloneNotSupportedException {
        assert tree.getParent() == null;

        // previous process before conversion
        ArrayList<IRVisitor<Void>> processors = new ArrayList<>();
        processors.add(new IRPlusAssignStmtReplacer(replacer));
        processors.add(new SymbolTableSetter(symbolTable));
        processors.add(new NextCodesSetter());
        PreProcessors preProcessors = new PreProcessors(processors);
        preProcessors.process(tree);

        // convert
        ConvertToLowCodeImpl(tree);
        result = tree.getLowerCodes();

        // fill label
        new CodeLabelSetter().process(result);
        result = new EmptyCodeFolder().process(result);
    }

    public SymbolTable getSymbolTable() {
        return symbolTable;
    }

    public ThreeAddressCodesInfo getInfo() {
        return info;
    }

    public ThreeAddressCodeList getResult() {
        return result;
    }

    private void ConvertToLowCodeImpl(IR ir) throws CloneNotSupportedException {
        ir.accept(visitor);
    }
}
