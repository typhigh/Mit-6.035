package edu.mit.compilers.lowercode.convertor;

import edu.mit.compilers.ir.common.IR;
import edu.mit.compilers.ir.common.IRVariable;
import edu.mit.compilers.ir.common.IRVisitor;
import edu.mit.compilers.ir.decl.IRFieldDecl;
import edu.mit.compilers.ir.decl.IRMemberDecl;
import edu.mit.compilers.ir.expression.IRExpression;

public class SymbolTableSetter extends IRVisitor<Void> {

    private final SymbolTable symbolTable;
    int tempId;

    public SymbolTableSetter(SymbolTable symbolTable) {
        this.symbolTable = symbolTable;
    }

    @Override
    public Void visit(IR ir) {
        return null;
    }

    /*
     * put decl
     */
    @Override
    public Void visit(IRMemberDecl ir) {
        symbolTable.put(ir.getVariable().getName(), ir);
        return null;
    }

    /*
     * set expression name like "t2" and put it to symbolTable
     */
    @Override
    public Void visit(IRExpression ir) {
        tempId++;
        String name = getExpressionName(tempId);
        ir.setNameInLowerCode(name);
        IRFieldDecl fieldDecl = new IRFieldDecl(ir.getType(), new IRVariable(name, -1, -1));
        symbolTable.put(name, fieldDecl);
        return null;
    }

    private String getExpressionName(int tempIid) {
        return "t" + tempId;
    }
}
