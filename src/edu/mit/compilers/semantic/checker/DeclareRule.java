package edu.mit.compilers.semantic.checker;


import edu.mit.compilers.ir.common.IRVariable;
import edu.mit.compilers.ir.decl.IRMemberDecl;
import edu.mit.compilers.ir.expression.literal.IRIntLiteral;
import edu.mit.compilers.ir.type.IRArrayType;

/*
    1. No identifier is declared twice in the same scope. This includes import identifiers, which
    exist in the global scope.
    2. No identifier is used before it is declared
    4. The <int_literal> in an array declaration must be greater than 0
 */
public class DeclareRule extends SemanticRule {
    @Override
    public SemanticError visit(IRVariable ir) {
        SemanticError error = new SemanticError();
        IRMemberDecl declaredFrom = getEnv().seek(ir);
        if (declaredFrom == null) {
            error.line = ir.getLine();
            error.error = "<id> " + ir.toString() + " should have been declared";
            return error;
        }

        ir.setDeclaredFrom(declaredFrom);
        return SemanticError.NoError;
    }

    @Override
    public SemanticError visit(IRMemberDecl ir) {
        SemanticError error;
        IRMemberDecl decl = getEnv().lastDeclaredInCurrentBlock(ir.getVariable());
        if (decl != null) {
            // twice declared in one scope
            error = new SemanticError();
            error.line = ir.getLine();
            error.error = "<id> " + ir.getVariable().toString() + " has been declared repeatedly";
        } else {
            getEnv().pushMemberDecl(ir);
            error = SemanticError.NoError;
        }
        return error;
    }

    @Override
    public SemanticError visit(IRArrayType ir) {
        SemanticError error;
        IRIntLiteral len = ir.getLen();
        int value = len.getValue();
        if (value <= 0) {
            error = new SemanticError();
            error.line = ir.getLine();
            error.error = "the <len> " + value + " in array declaration must be greater than 0";
        } else {
            error = SemanticError.NoError;
        }
        return error;
    }

    @Override
    public boolean doBefore() {
        return true;
    }
}
