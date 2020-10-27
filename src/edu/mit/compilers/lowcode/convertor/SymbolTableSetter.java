package edu.mit.compilers.lowcode.convertor;

import edu.mit.compilers.ir.common.IR;
import edu.mit.compilers.ir.common.IRVisitor;
import edu.mit.compilers.ir.decl.IRMemberDecl;

public class SymbolTableSetter extends IRVisitor<Void> {

    private final SymbolTable symbolTable;

    public SymbolTableSetter(SymbolTable symbolTable) {
        this.symbolTable = symbolTable;
    }

    @Override
    public Void visit(IR ir) {
        return null;
    }

    @Override
    public Void visit(IRMemberDecl ir) {
        symbolTable.put(ir.getVariable().getName(), ir);
        return null;
    }
}
