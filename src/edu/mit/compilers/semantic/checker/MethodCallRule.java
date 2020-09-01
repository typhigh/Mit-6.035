package edu.mit.compilers.semantic.checker;

import edu.mit.compilers.ir.common.IRArgumentList;
import edu.mit.compilers.ir.common.IRParameterList;
import edu.mit.compilers.ir.decl.IRMemberDecl;
import edu.mit.compilers.ir.decl.IRMethodDecl;
import edu.mit.compilers.ir.expression.IRMethodCall;
import edu.mit.compilers.ir.type.IRType;

/*
 * 5. The number and types of arguments in a method call (non-imports) must be the same as the
 * number and types of the formals, i.e., the signatures must be identical
 */
public class MethodCallRule extends SemanticRule {

    @Override
    public boolean doBefore() {
        return false;
    }

    @Override
    public SemanticError visit(IRMethodCall ir) {
        SemanticError error = new SemanticError();
        IRMemberDecl declaredFrom = ir.getVariable().getDeclaredFrom();
        if (declaredFrom == null
                || declaredFrom.getTag().equals("IRImportDecl")
                || declaredFrom.getTag().equals("IRFieldDecl")) {
            // do nothing (not its duty)
            return SemanticError.NoError;
        }

        IRParameterList paraList = ((IRMethodDecl) declaredFrom).getParaList();
        IRArgumentList argList = ir.getArgList();
        if (argList.size() != paraList.size()) {
            String info = "<method call> " + ir.getVariable().getName() +
                    " arguments have " + argList.size() + " element and parameters have " + paraList.size();
            error.set(info, 5, ir.getLine());
            return error;
        }

        for (int i = 0; i < argList.size(); ++i) {
            IRType argType = argList.get(i).getType();
            IRType paraType = paraList.get(i).getType();
            if (argType == null || paraType == null) {
                // do nothing
                continue;
            }

            if (!argType.equals(paraType)) {
                error.set(
                        "the " + i + "th <argument> not matched to the parameter",
                        5,
                        argList.get(i).getLine(),
                        argList.get(i).getColumn()
                );
                return error;
            }
        }
        return SemanticError.NoError;
    }
}
