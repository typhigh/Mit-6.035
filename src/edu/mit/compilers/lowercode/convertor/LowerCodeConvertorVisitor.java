package edu.mit.compilers.lowercode.convertor;

import edu.mit.compilers.ir.common.*;
import edu.mit.compilers.ir.decl.IRFieldDecl;
import edu.mit.compilers.ir.decl.IRMethodDecl;
import edu.mit.compilers.ir.expression.*;
import edu.mit.compilers.ir.expression.literal.IRLiteral;
import edu.mit.compilers.ir.statement.*;
import edu.mit.compilers.lowercode.*;
import edu.mit.compilers.utils.Literal;
import edu.mit.compilers.utils.OperatorUtils;

import java.util.ArrayList;
import java.util.HashMap;

/*
 * convert every stmt / expression to three address codes
 * note : visit just right-value expression
 */
public class LowerCodeConvertorVisitor extends IRVisitor<ThreeAddressCodeList> {

    private final HashMap<IRPlusAssignStmt, IRAssignStmt> replacer;
    public LowerCodeConvertorVisitor(HashMap<IRPlusAssignStmt, IRAssignStmt> replacer) {
        this.replacer = replacer;
    }

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
        ret.init(new MethodBeginCode(ir.getVariable().getName()));
        ret.append(ir.getParaList().accept(this));
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
        IRVariable leftVariable = left.getVariable();
        Variable variable = new Variable(leftVariable);

        Operand locationOperand = null;

        // t1 = location
        boolean isArray = left.isArrayLocation();
        if (isArray) {
            IRExpression location = left.getLocation();
            ret.append(location.accept(this));
            locationOperand = new Variable(location.getNameInLowerCode(), false);
        }

        // t2 = value
        IRExpression value = ir.getValue();
        ret.append(value.accept(this));
        Variable rightVariable = new Variable(value.getNameInLowerCode(), false);

        // left[t1] = t2
        ret.append(AssignCodeMaker.makeAssignWithLeftLocation(variable, locationOperand, rightVariable));
        return ret;
    }

    /*
     * goto some
     */
    @Override
    public ThreeAddressCodeList visit(IRBreakStmt ir) {
        ThreeAddressCodeList ret = ir.getLowerCodes();
        GotoCode code = new GotoCode(ir.getLoopStmt().getNextCodes());
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
     * LOOP : ifFalse t goto END
     *        ...
     *        step
     *        goto LOOP
     * END
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
     * goto END
     * (FALSE :xxx
     *         xxx)
     * END
     */
    @Override
    public ThreeAddressCodeList visit(IRIfStmt ir) throws CloneNotSupportedException {
        ThreeAddressCodeList ret = ir.getLowerCodes();
        ThreeAddressCodeList end = new ThreeAddressCodeList(new EmptyCode());
        IRBlock block = ir.getIfBlock();
        IRBlock elseBlock = ir.getElseBlock();
        boolean needElseBlock = elseBlock != null;
        ThreeAddressCodeList ifFalseNextStmtCodeList = needElseBlock ?
                elseBlock.getLowerCodes() : end;
        ret.init(convertConditionAndBlock(ir.getCondition(), block, false,
                null, ifFalseNextStmtCodeList));
        if (needElseBlock) {
            ret.append(elseBlock.accept(this));
        }
        ret.append(end);
        return ret;
    }

    /*
     * push args ...
     * call method
     */
    @Override
    public ThreeAddressCodeList visit(IRMethodCallStmt ir) throws CloneNotSupportedException {
        ThreeAddressCodeList ret = ir.getLowerCodes();
        return ret.init(convertMethodCall(ir.getMethodCall(), false));
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
     * L1
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
        return ret.init(AssignCodeMaker.makeAssignLiteralCode(
                new Variable(ir.getNameInLowerCode(), false),
                ir.getLiteral()
        ));
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
        IRExpression right1 = ir.getLeft();
        IRExpression right2 = ir.getRight();
        if (OperatorUtils.isCond(operator)) {
            // need short-cut
            return ret.init(convertCondExpression(ir));
        }

        // t1 = xxx
        ret.init(right1.accept(this));

        // t2 = xxx
        ret.append(right2.accept(this));

        // t3 = t1 op t2
        ret.append(AssignCodeMaker.makeAssignWithBinaryOp(
                new Variable(ir.getNameInLowerCode(), false),
                new Variable(right1.getNameInLowerCode(), false),
                new Variable(right2.getNameInLowerCode(), false),
                operator
        ));
        return ret;
    }

    /*
     * t = sizeof(expr)
     * sizeof(expr) can be converted into literal
     */
    @Override
    public ThreeAddressCodeList visit(IRLenExpr ir) {
        ThreeAddressCodeList ret = ir.getLowerCodes();
        long len = ((IRFieldDecl) ir.getVariable().getDeclaredFrom()).getType().getSize();
        ret.append(AssignCodeMaker.makeAssignLiteralCode(
                new Variable(ir.getNameInLowerCode(), false),
                new Literal<Long>(len)
        ));
        return ret;
    }

    /* just for right-value location (location in right side of expression)
     * len = xxx
     * t = a[len]
     */
    @Override
    public ThreeAddressCodeList visit(IRLocation ir) throws CloneNotSupportedException {
        ThreeAddressCodeList ret = ir.getLowerCodes();
        ThreeAddressCode assign;
        String variable = ir.getVariable().getName();
        if (ir.isArrayLocation()) {
            IRExpression len = ir.getLocation();
            ret.append(len.accept(this));
            assign = AssignCodeMaker.makeAssignWithRightLocation(
                    new Variable(ir.getNameInLowerCode(), false),
                    new Variable(ir.getVariable()),
                    new Variable(len.getNameInLowerCode(), false)
            );
        } else {
            assign = AssignCodeMaker.makeAssign(
                    new Variable(ir.getNameInLowerCode(), false),
                    new Variable(ir.getVariable())
            );
        }
        ret.append(assign);
        return ret;
    }

    /*
     * push args ...
     * t = call method
     */
    @Override
    public ThreeAddressCodeList visit(IRMethodCall ir) throws CloneNotSupportedException {
        ThreeAddressCodeList ret = ir.getLowerCodes();
        return ret.init(convertMethodCall(ir, true));
    }

    /*
     * t1 = xxx
     * ifFalse t1 goto COND
     * t2 = value1...
     * t = t2
     * goto END
     * COND: t3 = value2
     *       t = t3
     * END
     */
    @Override
    public ThreeAddressCodeList visit(IRTernaryExpr ir) throws CloneNotSupportedException {
        ThreeAddressCodeList ret = ir.getLowerCodes();
        ThreeAddressCodeList ifCond = new ThreeAddressCodeList();
        ThreeAddressCodeList ifCondNot = new ThreeAddressCodeList();
        ThreeAddressCodeList end = new ThreeAddressCodeList(new EmptyCode());
        IRExpression condition = ir.getCondition();
        IRExpression value1 = ir.getFirst();
        IRExpression value2 = ir.getSecond();
        // t1 = xxx
        ret.append(condition.accept(this));

        // ifFalse t1 goto COND
        ret.append(new GotoCode(ifCond, condition.getNameInLowerCode(), false));

        // if Cond not
        ifCondNot.append(value1.accept(this));
        ifCondNot.append(AssignCodeMaker.makeAssign(
                new Variable(ir.getNameInLowerCode(), false),
                new Variable(value1.getNameInLowerCode(), false)
        ));
        ifCondNot.append(new GotoCode(end));
        ret.append(ifCondNot);

        // if Cond
        ifCond.append(value2.accept(this));
        ifCondNot.append(AssignCodeMaker.makeAssign(
                new Variable(ir.getNameInLowerCode(), false),
                new Variable(value2.getNameInLowerCode(), false)
        ));
        ret.append(ifCond);

        // end
        return ret.append(end);
    }

    /*
     * t1 = xxx
     * t = -t1
     */
    @Override
    public ThreeAddressCodeList visit(IRUnaryOpExpr ir) throws CloneNotSupportedException {
        ThreeAddressCodeList ret = ir.getLowerCodes();
        IRExpression expr = ir.getRight();
        ret.append(expr.accept(this));
        ret.append(AssignCodeMaker.makeAssignWithUnaryOp(
                new Variable(ir.getNameInLowerCode(), false),
                new Variable(expr.getNameInLowerCode(), false),
                ir.getOperator()
        ));
        return ret;
    }


    private ThreeAddressCodeList convertLoopStmt(IRLoopStmt loopStmt,
                                                 IRStatement stepStmt) throws CloneNotSupportedException {
        // nextCodes always be an empty code
        ThreeAddressCodeList end = new ThreeAddressCodeList(new EmptyCode());
        ThreeAddressCodeList ret = convertConditionAndBlock(loopStmt.getCondition(),
                loopStmt.getBlock(), true, stepStmt, end);
        ret.append(end);
        return ret;
    }

    private ThreeAddressCodeList convertConditionAndBlock(IRExpression condition,
                                                          IRBlock block,
                                                          boolean needLoop,
                                                          IRStatement stepStmt,
                                                          ThreeAddressCodeList nextStmtCodeList)
            throws CloneNotSupportedException {
        // step use IRStatement for extend
        assert condition != null;
        assert nextStmtCodeList != null;

        // nextStmtCodeList can't be empty, let it has empty code
        if (nextStmtCodeList.isNull()) {
            nextStmtCodeList.init(new EmptyCode());
        }

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
         * ifFalse goto COND
         * t2 = xxx
         * t = t1 && t2
         * goto END
         * COND: t = false
         * END
         *
         * operator == "||"
         * t1 = xxx
         * ifTrue goto COND
         * t2 = xxx
         * t = t1 || t2
         * goto END
         * COND: t = true
         * END
         */
        IRExpression left = expr.getLeft();
        IRExpression right = expr.getRight();
        String operator = expr.getOperator();
        assert OperatorUtils.isCond(operator);

        ThreeAddressCodeList ret = new ThreeAddressCodeList();
        ThreeAddressCodeList ifCond = new ThreeAddressCodeList();
        ThreeAddressCodeList ifCondNot = new ThreeAddressCodeList();
        ThreeAddressCodeList end = new ThreeAddressCodeList(new EmptyCode());

        // t1 = xxx
        ret.append(left.accept(this));

        // ifCond t1 goto COND
        ret.append(new GotoCode(ifCond, left.getNameInLowerCode(), operator.equals("||")));

        // ifCondNot
        ifCondNot.append(right.accept(this));
        ifCondNot.append(AssignCodeMaker.makeAssignWithBinaryOp(
                new Variable(expr.getNameInLowerCode(), false),
                new Variable(left.getNameInLowerCode(), false),
                new Variable(right.getNameInLowerCode(), false),
                operator
        ));
        ifCondNot.append(new GotoCode(end));
        ret.append(ifCondNot);

        // ifCond
        ifCond.append(AssignCodeMaker.makeAssignLiteralCode(
                new Variable(expr.getNameInLowerCode(), false),
                new Literal<Boolean>(operator.equals("||"))
        ));
        ret.append(ifCond);

        // end
        ret.append(end);
        return ret;
    }

    /*
     * push args ...
     * (t = ) call method
     */
    private ThreeAddressCodeList convertMethodCall(IRMethodCall methodCall, boolean hasValue)
            throws CloneNotSupportedException {
        ThreeAddressCodeList ret = new ThreeAddressCodeList();
        ret.append(methodCall.getArgList().accept(this));

        // call method
        ThreeAddressCode methodCallCode;
        String methodName = methodCall.getVariable().getName();
        if (hasValue) {
            methodCallCode = new MethodCallCode(methodCall.getNameInLowerCode(), methodName);
        } else {
            methodCallCode = new MethodCallCode(methodName);
        }
        return ret.append(methodCallCode);
    }
}
