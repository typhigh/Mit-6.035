package edu.mit.compilers.semantic.checker;

import edu.mit.compilers.ir.expression.*;
import edu.mit.compilers.ir.expression.literal.IRBoolLiteral;
import edu.mit.compilers.ir.expression.literal.IRCharLiteral;
import edu.mit.compilers.ir.expression.literal.IRIntLiteral;
import edu.mit.compilers.ir.expression.literal.IRStringLiteral;
import edu.mit.compilers.ir.type.IRArrayType;
import edu.mit.compilers.ir.type.IRBasicType;
import edu.mit.compilers.ir.type.IRType;
import edu.mit.compilers.utils.OperatorUtils;

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
            TypeHelper.checkExpressionType(getEnv(), error, left, IRBasicType.IntType);
            TypeHelper.checkExpressionType(getEnv(), error, right, IRBasicType.IntType);
            ir.setType(IRBasicType.IntType);
            return error;
        }

        if (OperatorUtils.isCond(op)) {
            TypeHelper.checkExpressionType(getEnv(), error, left, IRBasicType.BoolType);
            TypeHelper.checkExpressionType(getEnv(), error, right, IRBasicType.BoolType);
            ir.setType(IRBasicType.BoolType);
            return error;
        }

        if (OperatorUtils.isEq(op) || OperatorUtils.isRel(op)) {
            TypeHelper.checkIfTypeEqual(getEnv(), error, left, right);
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
        TypeHelper.checkFieldVariableAndReturnType(getEnv(), error, ir.getVariable(), true);
        return error;
    }

    @Override
    public SemanticError visit(IRLocation ir) {
        SemanticError error = new SemanticError();
        IRType type = TypeHelper.checkFieldVariableAndReturnType(
                getEnv(), error, ir.getVariable(), ir.isArrayLocation());
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
        ir.setType(TypeHelper.checkMethodVariableAndReturnType(getEnv(), error, ir.getVariable()));
        return error;
    }

    @Override
    public SemanticError visit(IRTernaryExpr ir) {
        IRExpression condition = ir.getCondition();
        IRExpression first = ir.getFirst();
        IRExpression second = ir.getSecond();
        SemanticError error = new SemanticError();

        ir.setType(null);
        if (!TypeHelper.checkExpressionType(getEnv(), error, condition, IRBasicType.BoolType)) {
            return error;
        }

        if (first.getType() == null || second.getType() == null) {
            return error;
        }

        if (!first.getType().equals(second.getType())) {
            error.line = first.getLine();
            error.error = "<first> type is " + first.getType().toString() +
                    " and <second> type is " + second.getType().toString();
            return error;
        }

        if (!first.getType().isBasicType()) {
            error.line = first.getLine();
            error.error = "<first> and <second> types are not basic type but" + first.getType().toString();
        }
        ir.setType(first.getType());
        return error;
    }

    @Override
    public SemanticError visit(IRUnaryOpExpr ir) {
        SemanticError error = new SemanticError();
        ir.setType(null);
        if (ir.getOperator().equals("!")) {
            TypeHelper.checkExpressionType(getEnv(), error, ir.getRight(), IRBasicType.BoolType);
            ir.setType(IRBasicType.BoolType);
            return error;
        }

        if (ir.getOperator().equals("-")) {
            TypeHelper.checkExpressionType(getEnv(), error, ir.getRight(), IRBasicType.IntType);
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
    public boolean doBefore() {
        return false;
    }
}
