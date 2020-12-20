package edu.mit.compilers.lowercode.convertor;

import edu.mit.compilers.ir.common.IR;
import edu.mit.compilers.ir.common.IRVisitor;
import edu.mit.compilers.ir.statement.IRAssignStmt;
import edu.mit.compilers.ir.statement.IRPlusAssignStmt;
import edu.mit.compilers.lowercode.ThreeAddressCodesInfo;

import java.util.ArrayList;
import java.util.HashMap;

public class LowerCodeConvertor {

    private final ThreeAddressCodesInfo result = new ThreeAddressCodesInfo();

    private final HashMap<IRPlusAssignStmt, IRAssignStmt> replacer = new HashMap<>();
    private final SymbolTableSetter symbolTableSetter;
    private final LowerCodeConvertorVisitor visitor;


    public LowerCodeConvertor() {
        this.symbolTableSetter = new SymbolTableSetter(result.symbolTable);
        this.visitor = new LowerCodeConvertorVisitor(replacer);
    }

    public void convertToLowCode(IR tree) throws CloneNotSupportedException {
        assert tree.getParent() == null;

        // previous process before conversion
        ArrayList<IRVisitor<Void>> processors = new ArrayList<>();
        processors.add(new IRPlusAssignStmtReplacer(replacer));
        processors.add(new SymbolTableSetter(result.symbolTable));
        // processors.add(new NextCodesSetter());
        PreProcessors preProcessors = new PreProcessors(processors);
        preProcessors.process(tree);

        // convert
        ConvertToLowCodeImpl(tree);
        result.codes = tree.getLowerCodes();

        // fill label
        new CodeLabelSetter().process(result.codes);
        result.codes = new EmptyCodeFolder().process(result.codes);
    }

    public ThreeAddressCodesInfo getResult() {
        return result;
    }

    private void ConvertToLowCodeImpl(IR ir) throws CloneNotSupportedException {
        ir.accept(visitor);
    }
}
