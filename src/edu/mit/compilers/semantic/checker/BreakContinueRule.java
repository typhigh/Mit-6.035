package edu.mit.compilers.semantic.checker;

import edu.mit.compilers.ir.common.IR;
import edu.mit.compilers.ir.statement.IRBreakStmt;
import edu.mit.compilers.ir.statement.IRContinueStmt;
import edu.mit.compilers.ir.statement.IRStatement;

/*
 * 21. All break and continue statements must be contained within the body of a for or a while.
 */
public class BreakContinueRule extends SemanticRule {

    @Override
    public boolean doBefore() {
        return true;
    }

    @Override
    public SemanticError visit(IRBreakStmt ir) {
        IRStatement loopStmt = findLoopStmt(ir);
        if (loopStmt == null) {
            SemanticError error = new SemanticError();
            String info = "<BreakStatement> should be covered by a loop statement like for or while";
            error.set(info, 21, ir.getLine(), ir.getColumn());
            return error;
        }
        ir.setLoopStmt(loopStmt);
        return SemanticError.NoError;
    }

    @Override
    public SemanticError visit(IRContinueStmt ir) {
        IRStatement loopStmt = findLoopStmt(ir);
        if (loopStmt == null) {
            SemanticError error = new SemanticError();
            String info = "<ContinueStatement> should be covered by a loop statement like for or while";
            error.set(info, 21, ir.getLine(), ir.getColumn());
            return error;
        }
        ir.setLoopStmt(loopStmt);
        return SemanticError.NoError;
    }

    private IRStatement findLoopStmt(IR ir) {
        IR cur = ir;
        while (cur != null && !cur.getTag().equals("IRForStmt") && !cur.getTag().equals("IRWhile")) {
            cur = cur.getParent();
        }
        return (IRStatement) cur;
    }
}
