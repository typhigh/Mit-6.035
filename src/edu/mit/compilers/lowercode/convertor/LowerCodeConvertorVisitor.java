package edu.mit.compilers.lowercode.convertor;

import edu.mit.compilers.ir.common.*;
import edu.mit.compilers.ir.decl.IRFieldDecl;
import edu.mit.compilers.ir.decl.IRMethodDecl;
import edu.mit.compilers.ir.expression.IRBinaryOpExpr;
import edu.mit.compilers.ir.expression.IRExpression;
import edu.mit.compilers.ir.expression.IRLocation;
import edu.mit.compilers.ir.expression.literal.IRLiteral;
import edu.mit.compilers.ir.statement.*;
import edu.mit.compilers.lowercode.*;
import edu.mit.compilers.utils.Literal;
import edu.mit.compilers.utils.OperatorUtils;

import java.util.ArrayList;
import java.util.HashMap;

public class LowerCodeConvertorVisitor extends IRVisitor<ThreeAddressCodeList> {

    private final HashMap<IRPlusAssignStmt, IRAssignStmt> replacer;
    public LowerCodeConvertorVisitor(HashMap<IRPlusAssignStmt, IRAssignStmt> replacer) {
        this.replacer = replacer;
    }

    // TODO: avoid visit unused ir
    // default visit func
    @Override
    public ThreeAddressCodeList visit(IR ir) {
        throw new RuntimeException("unsupported ir type: " + ir.getTag() + " for lower code convertor");
    }

    @Override
    public ThreeAddressCodeList visit(IRProgram ir) throws CloneNotSupportedException {
        // just convert method decl
        ThreeAddressCodeList ret = ir.getLowerCodes();
        for (IRMethodDecl decl : ir.getMethodDecls()) {
            ret.append(decl.accept(this));
        }
        return ret;
    }

    @Override
    public ThreeAddressCodeList visit(IRBlock ir) throws CloneNotSupportedException {
        ThreeAddressCodeList ret = ir.getLowerCodes();
        for (IRStatement stmt : ir.getStatements()) {
            ret.append(stmt.accept(this));
        }
        return ret;
    }

    /*
     * METHOD :
     *  popParam xxx
     *  popParam xxx
     *  block
     *  leave
     */
    @Override
    public ThreeAddressCodeList visit(IRMethodDecl ir) throws CloneNotSupportedException {
        ThreeAddressCodeList ret = ir.getLowerCodes();
        ret.init(ir.getParaList().accept(this));
        ret.append(ir.getBlock().accept(this));
        ret.append(new MethodLeaveCode());
        ret.setNeedLabelTrue();
        return ret;
    }

    /*
     * t1 = xxx
     * t2 = xxx
     * t3 = xxx
     * ...
     * pushParam t1
     * pushParam t2
     * pushParam t3
     */
    @Override
    public ThreeAddressCodeList visit(IRArgumentList ir) throws CloneNotSupportedException {
        ThreeAddressCodeList ret = ir.getLowerCodes();
        ArrayList<String> paramName = new ArrayList<>();
        for (IRExpression paraExpr : ir.getArgList()) {
            ret.append(paraExpr.accept(this));
            paramName.add(paraExpr.getNameInLowerCode());
        }
        for (String param : paramName) {
            ret.append(new PushParamCode(param));
        }
        return ret;
    }

    /* pop all parameters in FIFO order
     * popParam xxx
     * popParam xxx
     */
    @Override
    public ThreeAddressCodeList visit(IRParameterList ir) {
        ThreeAddressCodeList ret = ir.getLowerCodes();
        for (IRFieldDecl param : ir.getParaList()) {
            ret.append(new PopParamCode(param.getVariable().getName()));
        }
        return ret;
    }

    /*
     * left[location] = value
     * <=>
     * t1 = location
     * t2 = value
     * left[t1] = t2
     */
    @Override
    public ThreeAddressCodeList visit(IRAssignStmt ir) throws CloneNotSupportedException {
        ThreeAddressCodeList ret = ir.getLowerCodes();
        assert ret.isNull();
        IRLocation left = ir.getLocation();
        String leftVariable = left.getVariable().getName();
        String locationVariable = null;

        // t1 = location
        boolean isArray = left.isArrayLocation();
        if (isArray) {
            IRExpression location = left.getLocation();
            ret.append(location.accept(this));
            locationVariable = location.getNameInLowerCode();
        }

        // t2 = value
        IRExpression value = ir.getValue();
        ret.append(value.accept(this));
        String rightVariable = value.getNameInLowerCode();

        ret.append(new AssignSingleOperandCode(leftVariable, locationVariable, rightVariable, null));
        return ret;
    }

    /*
     * goto some
     */
    @Override
    public ThreeAddressCodeList visit(IRBreakStmt ir) {
        ThreeAddressCodeList ret = ir.getLowerCodes();
        GotoCode code = new GotoCode(ir.getLoopStmt().getNextStmtCodes());
        return ret.append(code);
    }

    /*
     * goto some
     */
    @Override
    public ThreeAddressCodeList visit(IRContinueStmt ir) {
        ThreeAddressCodeList ret = ir.getLowerCodes();
        IRLoopStmt loopStmt = ir.getLoopStmt();
        ThreeAddressCode code = new GotoCode(loopStmt.getLoopCodeList());
        return ret.append(code);
    }

    /*
     * left[...] = value
     * t = xxx
     * LOOP : ifFalse t goto L1
     *        ...
     *        step
     *        goto LOOP
     * L!
     */
    @Override
    public ThreeAddressCodeList visit(IRForStmt ir) throws CloneNotSupportedException {
        ThreeAddressCodeList ret = ir.getLowerCodes();
        ThreeAddressCodeList loopCodes = ir.getLoopCodeList();
        ret.init(ir.getInitializer().accept(this));
        loopCodes.init(convertLoopStmt(ir, ir.getStep()));
        return ret.append(loopCodes);
    }

    /*
     * t = xxx
     * ifFalse t goto FALSE
     * ...
     * goto NEXT
     * (FALSE :xxx
     *         xxx)
     * NEXT(FALSE) : xxx
     */
    @Override
    public ThreeAddressCodeList visit(IRIfStmt ir) throws CloneNotSupportedException {
        ThreeAddressCodeList ret = ir.getLowerCodes();
        IRBlock block = ir.getIfBlock();
        IRBlock elseBlock = ir.getElseBlock();
        boolean needElseBlock = elseBlock != null;
        ThreeAddressCodeList ifFalseNextStmtCodeList = needElseBlock ?
                elseBlock.getLowerCodes() : ir.getNextStmtCodes();
        ret.init(convertConditionAndBlock(ir.getCondition(), block, false,
                null, ifFalseNextStmtCodeList));
        if (needElseBlock) {
            ret.append(elseBlock.accept(this));
        }
        return ret;
    }

    @Override
    public ThreeAddressCodeList visit(IRMethodCallStmt ir) {
        ThreeAddressCodeList ret = ir.getLowerCodes();
        // TODO: without t = call_xxx , instead call_xxx
        ret.init(ir.getMethodCall().accept(this));
        return ret;
    }

    @Override
    public ThreeAddressCodeList visit(IRPlusAssignStmt ir) throws CloneNotSupportedException {
        ThreeAddressCodeList ret = ir.getLowerCodes();
        IRAssignStmt replace = replacer.get(ir);
        assert replace != null;
        return ret.init(replace.accept(this));
    }

    /*
     * t = xxx
     * set-result t
     */
    @Override
    public ThreeAddressCodeList visit(IRReturnStmt ir) throws CloneNotSupportedException {
        ThreeAddressCodeList ret = ir.getLowerCodes();
        IRExpression value = ir.getExpr();
        ret.init(value.accept(this));
        ret.append(new MethodSetResultCode(value.getNameInLowerCode()));
        return ret;
    }

    /*
     * t = xxx
     * LOOP : ifFalse t goto L1
     *        ...
     *        goto LOOP
     * L!
     */
    @Override
    public ThreeAddressCodeList visit(IRWhileStmt ir) throws CloneNotSupportedException {
        ThreeAddressCodeList ret = ir.getLoopCodeList();
        return ret.init(convertLoopStmt(ir, null));
    }

    /*
     * t = xxx
     */
    @Override
    public ThreeAddressCodeList visit(IRLiteral ir) {
        ThreeAddressCodeList ret = ir.getLowerCodes();
        return ret.init(new AssignSingleOperandCode(ir.getNameInLowerCode(), ir.getLiteralValue()));
    }

    /* for not-cond binary-op expression:
     * t1 = xxx
     * t2 = xxx
     * t3 = t1 op t2
     * otherwise, may short-cut, see convertCondExpression
     */
    @Override
    public ThreeAddressCodeList visit(IRBinaryOpExpr ir) throws CloneNotSupportedException {
        ThreeAddressCodeList ret = ir.getLowerCodes();
        String operator = ir.getOperator();
        assert OperatorUtils.isBinary(operator);
        IRExpression left = ir.getLeft();
        IRExpression right = ir.getRight();
        if (OperatorUtils.isCond(operator)) {
            // need short-cut
            return ret.init(convertCondExpression(ir));
        }

        ret.init(left.accept(this));
        ret.append(right.accept(this));
        ret.append(new AssignTwoOperandCode(ir.getNameInLowerCode(), left.getNameInLowerCode(),
                right.getNameInLowerCode(), operator));
        return ret;
    }

    private ThreeAddressCodeList convertLoopStmt(IRLoopStmt loopStmt,
                                                 IRStatement stepStmt) throws CloneNotSupportedException {
        return convertConditionAndBlock(loopStmt.getCondition(), loopStmt.getBlock(),
                true, stepStmt, loopStmt.getNextStmtCodes());
    }

    private ThreeAddressCodeList convertConditionAndBlock(IRExpression condition,
                                                          IRBlock block,
                                                          boolean needLoop,
                                                          IRStatement stepStmt,
                                                          ThreeAddressCodeList nextStmtCodeList)
            throws CloneNotSupportedException {
        // step use IRStatement for extend
        assert condition != null;
        boolean needStep = stepStmt != null;
        ThreeAddressCodeList ret = new ThreeAddressCodeList();

        // t = xxx
        ret.init(condition.accept(this));

        // ifFlase t goto NEXT
        ThreeAddressCode gotoCode = new GotoCode(nextStmtCodeList, condition.getNameInLowerCode(), false);
        ret.append(gotoCode);

        // BLOCK ... step , goto LOOP
        ret.append(block.accept(this));
        if (needStep) {
            assert needLoop;
            ret.append(stepStmt.accept(this));
        }

        if (needLoop) {
            ret.append(new GotoCode(ret));
        }
        return ret;
    }

    private ThreeAddressCodeList convertCondExpression(IRBinaryOpExpr expr) throws CloneNotSupportedException {
        /* operator == "&&"
         * t1 = xxx
         * ifFalse goto FALSE
         * t2 = xxx
         * t = t1 && t2
         * goto END
         * FALSE: t = false
         * END
         *
         * operator == "||"
         * t1 = xxx
         * ifTrue goto True
         * t2 = xxx
         * t = t1 || t2
         * goto END
         * TRUE: t = true
         * END
         */
        IRExpression left = expr.getLeft();
        IRExpression right = expr.getRight();
        String operator = expr.getOperator();
        assert OperatorUtils.isCond(operator);
        ThreeAddressCodeList ret = new ThreeAddressCodeList();

        // t1 = xxx
        ret.append(left.accept(this));

        // FALSE(TRUE) : ... END
        boolean isAndCond = operator.equals("&&");
        String label = isAndCond ? "false" : "true";
        ThreeAddressCode ifCondDo = new AssignLiteralCode(expr.getNameInLowerCode(), new Literal<Boolean>(label));

        // TODO: end code

        return null;
    }

}
