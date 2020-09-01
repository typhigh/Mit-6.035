package edu.mit.compilers.semantic.checker;


import edu.mit.compilers.ir.common.IRVariable;
import edu.mit.compilers.ir.decl.IRMemberDecl;
import edu.mit.compilers.ir.expression.literal.IRIntLiteral;
import edu.mit.compilers.ir.type.IRArrayType;

/*
 *  1. No identifier is declared twice in the same scope. This includes import identifiers, which
 *  exist in the global scope.
 *  2. No identifier is used before it is declared
 *  4. The <int_literal> in an array declaration must be greater than 0
 */
public class DeclareRule extends SemanticRule {
    @Override
    public SemanticError visit(IRVariable ir) {
        SemanticError error = new SemanticError();
        IRMemberDecl declaredFrom = getEnv().seek(ir);
        if (declaredFrom == null) {
            error.set(
                    "<identifier> " + ir.getName() + " is used without been declared",
                    2,
                    ir.getLine(),
                    ir.getColumn()
            );
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
            error.set(
                    "<identifier> " + ir.getVariable().getName() + " is declared repeatedly in the same scope",
                    1,
                    ir.getLine()
            );
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
            error.set(
                    "the <len> " + value + " in array declaration must be greater than 0",
                    4,
                    ir.getLine()
            );
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
