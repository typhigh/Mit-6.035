package edu.mit.compilers.ir.statement;

import edu.mit.compilers.ir.common.IRBlock;
import edu.mit.compilers.ir.expression.IRExpression;
import edu.mit.compilers.lowercode.code.ThreeAddressCodeList;

public class
IRLoopStmt extends IRStatement {

    private IRExpression condition;
    private IRBlock block;

    // loop region statements tac code list (contains condition)
    private ThreeAddressCodeList loopCodeList = new ThreeAddressCodeList();

    public IRLoopStmt(String name, IRExpression condition, IRBlock block) {
        super(name);
        this.condition = condition;
        this.block = block;
    }

    public IRExpression getCondition() {
        return condition;
    }

    public IRBlock getBlock() {
        return block;
    }

    public ThreeAddressCodeList getLoopCodeList() {
        return loopCodeList;
    }

    @Override
    public IRStatement clone() throws CloneNotSupportedException {
        IRLoopStmt clone = (IRLoopStmt) super.clone();
        if (condition != null) {
            clone.condition = condition.clone();
        }
        if (block != null) {
            clone.block = block.clone();
        }
        return clone;
    }
}
