package edu.mit.compilers.lowcode.convertor;

import edu.mit.compilers.ir.common.IR;
import edu.mit.compilers.ir.common.IRBlock;
import edu.mit.compilers.ir.common.IRVisitor;
import edu.mit.compilers.ir.decl.IRMethodDecl;
import edu.mit.compilers.ir.statement.IRStatement;

import java.util.ArrayList;

// set next stmt for every stmt
public class NextStmtSetter extends IRVisitor<Void> {

    @Override
    public Void visit(IR ir) {
        return null;
    }

    @Override
    public Void visit(IRBlock ir) {
        IRStatement blockNextStmt = null;
        IR parent = ir.getParent();
        if (parent instanceof IRStatement) {
            // for stmt or while stmt
            blockNextStmt = ((IRStatement) parent).getNextStmt();
        } else if (parent instanceof IRMethodDecl) {
            blockNextStmt = ((IRMethodDecl) parent).getEmptyEndLabel();
        } else {
            assert false;
        }

        ArrayList<IRStatement> stmts = ir.getStatements();
        for (int i = 0; i < stmts.size(); ++i) {
            IRStatement stmt = stmts.get(i);
            if (i + 1 < stmts.size()) {
                stmt.setNextStmt(stmts.get(i+1));
            } else {
                stmt.setNextStmt(blockNextStmt);
            }
        }
        return null;
    }
}
