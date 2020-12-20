package edu.mit.compilers.lowercode.convertor;

import edu.mit.compilers.ir.common.IR;
import edu.mit.compilers.ir.common.IRVisitor;
import edu.mit.compilers.ir.decl.IRFieldDecl;
import edu.mit.compilers.ir.decl.IRMethodDecl;
import edu.mit.compilers.ir.expression.IRExpression;
import edu.mit.compilers.ir.expression.literal.IRStringLiteral;
import edu.mit.compilers.lowercode.*;
import edu.mit.compilers.utils.LiteralHelper;

public class SymbolTableSetter extends IRVisitor<Void> {

    private final SymbolTable symbolTable;
    private MethodInfo currentMethodInfo;
    int tempId;
    int constStringId;

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
    public Void visit(IRMethodDecl ir) {
        String methodName = ir.getVariable().getName();
        currentMethodInfo = new MethodInfo(methodName);
        symbolTable.putMethodInfo(methodName, currentMethodInfo);
        return null;
    }

    @Override
    public Void visit(IRFieldDecl ir) {
        FieldInfo fieldInfo = new FieldInfo(ir.getType(), new Variable(ir.getVariable()));
        putFieldInfo(fieldInfo);
        return null;
    }

    /*
     * set expression name like "t2" and put it to symbolTable
     */
    @Override
    public Void visit(IRExpression ir) {
        tempId++;
        ir.setNameInLowerCode(getExpressionName(tempId));
        FieldInfo fieldInfo = new FieldInfo(ir.getType(), new Variable(ir.getNameInLowerCode(), false));
        putFieldInfo(fieldInfo);
        return null;
    }

    /*
     * get the
     */
    @Override
    public Void visit(IRStringLiteral ir) {
        constStringId++;
        ir.setNameInLowerCode(getStringName(constStringId));
        Variable variable = new Variable(ir.getNameInLowerCode(), true);

        // set const value, because in decaf language, string field is always const
        variable.setConstValue(new Value(LiteralHelper.makeLiteralByValue(ir.getLiteralValue())));
        FieldInfo fieldInfo = new FieldInfo(ir.getType(), variable);
        putConstStringInfo(fieldInfo);
        return null;
    }

    private String getStringName(int constStringId) {
        return "s" + constStringId;
    }

    private String getExpressionName(int tempIid) {
        return "t" + tempId;
    }

    private void putFieldInfo(FieldInfo fieldInfo) {
        // either in method area or global area
        if (currentMethodInfo != null) {
            currentMethodInfo.getFieldsInfo().add(fieldInfo);
        } else {
            symbolTable.putGlobalFieldInfo(fieldInfo);
        }
    }

    private void putConstStringInfo(FieldInfo fieldInfo) {
        symbolTable.putConstStringInfo(fieldInfo);
    }
}
