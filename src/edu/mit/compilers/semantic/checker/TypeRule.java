package edu.mit.compilers.semantic.checker;

import edu.mit.compilers.ir.expression.*;
import edu.mit.compilers.ir.expression.literal.IRBoolLiteral;
import edu.mit.compilers.ir.expression.literal.IRCharLiteral;
import edu.mit.compilers.ir.expression.literal.IRIntLiteral;
import edu.mit.compilers.ir.expression.literal.IRStringLiteral;
import edu.mit.compilers.ir.statement.*;
import edu.mit.compilers.ir.type.IRArrayType;
import edu.mit.compilers.ir.type.IRBasicType;
import edu.mit.compilers.ir.type.IRType;
import edu.mit.compilers.utils.OperatorUtils;

/*
 * 10. An <id> used as a <location> must name a declared local/global variable or formal parameter.
 * 11. The identifier in a method statement must be a declared method or import.
 * 12. For all locations of the form <id>[<expr>]
 *  (a) <id> must be an array variable, and
 *  (b) the type of expr must be int.
 * 13. The argument of the len operator must be an array variable, and it must return an int
 * 14. The <expr> in an if or a while statement must have type bool, as well as the second expression
 * of a for statement.
 * 15. The first <expr> in a ternary conditional expression (?:) must have type bool. Each alternative
 * in the expr must be of the same type in which it’s context expects the expression to be
 * 16. The operands of the unary minus, <arith_op>s, and <rel_op>s must have type int.
 * 17. The operands of <eq_op>s must have the same type, either int or bool.
 * 18. The operands of <cond_op>s and the operand of logical not (!) must have type bool.
 * 19. The <location> and the <expr> in an assignment, <location> = <expr>, must have the same type.
 * 20. The <location> and the <expr> in an incrementing/decrementing assignment, <location> += <expr>
 * and <location> -= <expr>, must be of type int. The same is true of <location> for ++ and
- - .
 */
public class TypeRule extends SemanticRule {

    @Override
    public SemanticError visit(IRExpression ir) {
        throw new RuntimeException("Not implement this expression type : " + ir.getType());
    }

    @Override
    public SemanticError visit(IRBinaryOpExpr ir) {
        SemanticError error = new SemanticError();
        IRExpression left = ir.getLeft();
        IRExpression right = ir.getRight();
        String op = ir.getOperator();

        assert OperatorUtils.isBinary(op);
        if (OperatorUtils.isArith(op)) {
            ir.setType(IRBasicType.IntType);
            TypeHelper.checkExpressionType(getEnv(), error, 16, left, IRBasicType.IntType);
            if (error.hasError()) {
                // only report one error for each rule on each node
                return error;
            }
            TypeHelper.checkExpressionType(getEnv(), error, 16, right, IRBasicType.IntType);
            return error;
        }

        if (OperatorUtils.isCond(op)) {
            ir.setType(IRBasicType.BoolType);
            TypeHelper.checkExpressionType(getEnv(), error, 18, left, IRBasicType.BoolType);
            if (error.hasError()) {
                // only report one error for each rule on each node
                return error;
            }
            TypeHelper.checkExpressionType(getEnv(), error, 18, right, IRBasicType.BoolType);
            return error;
        }

        if (OperatorUtils.isEq(op) || OperatorUtils.isRel(op)) {
            TypeHelper.checkIfTypeEqual(getEnv(), error, 17, left, right);
            ir.setType(IRBasicType.BoolType);
            return error;
        }

        throw new RuntimeException("Not supported operator " + op);
    }

    @Override
    public SemanticError visit(IREmptyExpr ir) {
        ir.setType(IRBasicType.VoidType);
        return SemanticError.NoError;
    }

    @Override
    public SemanticError visit(IRLenExpr ir) {
        SemanticError error = new SemanticError();
        ir.setType(IRBasicType.IntType);
        TypeHelper.checkFieldVariableAndGetType(getEnv(), error, 13, ir.getVariable(), true);
        return error;
    }

    @Override
    public SemanticError visit(IRLocation ir) {
        SemanticError error = new SemanticError();
        IRType type = TypeHelper.checkFieldVariableAndGetType(
                getEnv(), error, 12, ir.getVariable(), ir.isArrayLocation());
        ir.setType(null);

        if (type == null) {
            // something wrong
            return error;
        }

        if (ir.isArrayLocation()) {
            ir.setType(((IRArrayType) type).getBasicType());
        } else {
            ir.setType((IRBasicType) type);
        }
        return error;
    }

    @Override
    public SemanticError visit(IRMethodCall ir) {
        SemanticError error = new SemanticError();
        ir.setType(TypeHelper.checkMethodVariableAndGetType(getEnv(), error, 11, ir.getVariable()));
        return error;
    }

    @Override
    public SemanticError visit(IRTernaryExpr ir) {
        IRExpression condition = ir.getCondition();
        IRExpression first = ir.getFirst();
        IRExpression second = ir.getSecond();
        SemanticError error = new SemanticError();

        ir.setType(null);
        if (!TypeHelper.checkExpressionType(getEnv(), error, 15, condition, IRBasicType.BoolType)) {
            return error;
        }

        if (first.getType() == null || second.getType() == null) {
            return error;
        }

        if (!TypeHelper.checkIfTypeEqual(getEnv(), error, 15, first, second)) {
            return error;
        }

        // not check type, just set type (push error up)
        ir.setType(first.getType());
        return SemanticError.NoError;
    }

    @Override
    public SemanticError visit(IRUnaryOpExpr ir) {
        SemanticError error = new SemanticError();
        ir.setType(null);
        if (ir.getOperator().equals("!")) {
            TypeHelper.checkExpressionType(getEnv(), error, 18, ir.getRight(), IRBasicType.BoolType);
            ir.setType(IRBasicType.BoolType);
            return error;
        }

        if (ir.getOperator().equals("-")) {
            TypeHelper.checkExpressionType(getEnv(), error, 16, ir.getRight(), IRBasicType.IntType);
            ir.setType(IRBasicType.IntType);
            return error;
        }

        throw new RuntimeException("Unknown operator " + ir.getOperator() + ".Only support ! and -");
    }

    @Override
    public SemanticError visit(IRBoolLiteral ir) {
        ir.setType(IRBasicType.BoolType);
        return SemanticError.NoError;
    }

    @Override
    public SemanticError visit(IRCharLiteral ir) {
        ir.setType(IRBasicType.CharType);
        return SemanticError.NoError;
    }

    @Override
    public SemanticError visit(IRIntLiteral ir) {
        ir.setType(IRBasicType.IntType);
        return SemanticError.NoError;
    }

    @Override
    public SemanticError visit(IRStringLiteral ir) {
        ir.setType(IRBasicType.StringType);
        return SemanticError.NoError;
    }

    @Override
    public SemanticError visit(IRIfStmt ir) {
        SemanticError error = new SemanticError();
        TypeHelper.checkExpressionType(getEnv(), error, 14, ir.getCondition(), IRBasicType.BoolType);
        return error;
    }

    @Override
    public SemanticError visit(IRWhileStmt ir) {
        SemanticError error = new SemanticError();
        TypeHelper.checkExpressionType(getEnv(), error, 14, ir.getCondition(), IRBasicType.BoolType);
        return error;
    }

    @Override
    public SemanticError visit(IRForStmt ir) {
        SemanticError error = new SemanticError();
        TypeHelper.checkExpressionType(getEnv(), error, 14, ir.getCondition(), IRBasicType.BoolType);
        return error;
    }

    @Override
    public SemanticError visit(IRAssignStmt ir) {
        SemanticError error = new SemanticError();
        TypeHelper.checkIfTypeEqual(getEnv(), error, 19, ir.getLocation(), ir.getValue());
        return error;
    }

    @Override
    public SemanticError visit(IRPlusAssignStmt ir) {
        SemanticError error = new SemanticError();
        TypeHelper.checkExpressionType(getEnv(), error, 20, ir.getLocation(), IRBasicType.IntType);
        if (error.hasError()) {
            return error;
        }
        TypeHelper.checkExpressionType(getEnv(), error, 20, ir.getValue(), IRBasicType.IntType);
        return error;
    }

    @Override
    public boolean doBefore() {
        return false;
    }
}
