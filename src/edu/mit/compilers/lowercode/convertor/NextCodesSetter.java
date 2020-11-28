package edu.mit.compilers.lowercode.convertor;

import edu.mit.compilers.ir.common.IR;
import edu.mit.compilers.ir.common.IRBlock;
import edu.mit.compilers.ir.common.IRVisitor;
import edu.mit.compilers.ir.decl.IRMethodDecl;
import edu.mit.compilers.ir.statement.IRStatement;
import edu.mit.compilers.lowercode.ThreeAddressCodeList;

import java.util.ArrayList;

// set next stmt for every stmt and expersion
public class NextCodesSetter extends IRVisitor<Void> {

    @Override
    public Void visit(IR ir) {
        return null;
    }

    /*
     * visit block and set statements' next codes
     */
    @Override
    public Void visit(IRBlock ir) {
        ThreeAddressCodeList blockNextCodes = null;
        IR parent = ir.getParent();

        // for stmt / while stmt
        if (parent instanceof IRStatement) {
            blockNextCodes = ((IRStatement) parent).getNextCodes();
        } else {
            assert parent instanceof IRMethodDecl;
            // do nothing
        }

        ArrayList<IRStatement> stmts = ir.getStatements();
        int count = stmts.size();
        for (int i = 0; i < count; ++i) {
            IRStatement stmt = stmts.get(i);
            if (i + 1 == count) {
                stmt.setNextCodes(blockNextCodes);
            } else {
                stmt.setNextCodes(stmts.get(i+1).getLowerCodes());
            }
        }
        return null;
    }
}