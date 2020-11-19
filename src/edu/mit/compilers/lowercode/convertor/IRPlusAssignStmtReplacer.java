package edu.mit.compilers.lowercode.convertor;

import edu.mit.compilers.ir.common.IR;
import edu.mit.compilers.ir.common.IRHepler;
import edu.mit.compilers.ir.common.IRVisitor;
import edu.mit.compilers.ir.statement.IRAssignStmt;
import edu.mit.compilers.ir.statement.IRPlusAssignStmt;

import java.util.HashMap;

public class IRPlusAssignStmtReplacer extends IRVisitor<Void> {

    private final HashMap<IRPlusAssignStmt, IRAssignStmt> replacer;

    public IRPlusAssignStmtReplacer(HashMap<IRPlusAssignStmt, IRAssignStmt> replacer) {
        this.replacer = replacer;
    }

    @Override
    public Void visit(IR ir) {
        return null;
    }

    @Override
    public Void visit(IRPlusAssignStmt ir) throws CloneNotSupportedException {
        replacer.put(ir, IRHepler.convertToAssignStmt(ir));
        return null;
    }
}
