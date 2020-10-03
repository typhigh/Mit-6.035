package edu.mit.compilers.semantic;

import edu.mit.compilers.ir.common.IR;
import edu.mit.compilers.ir.common.IRVariable;
import edu.mit.compilers.ir.common.IRVisitor;
import edu.mit.compilers.ir.decl.IRFieldDecl;
import edu.mit.compilers.ir.decl.IRImportDecl;
import edu.mit.compilers.ir.decl.IRMemberDecl;
import edu.mit.compilers.ir.decl.IRMethodDecl;

import java.util.ArrayList;

public class Renamer {

    private final ArrayList<String> names = new ArrayList<>();
    private static int nameId = 0;

    // Maybe called more than once
    public IR Rename(IR ir) {
        RenameVisitor visitor = new RenameVisitor(this);
        RenameImpl(ir, visitor);
        return ir;
    }

    private void RenameImpl(IR ir, RenameVisitor visitor) {
        // top first and down second
        ir.accept(visitor);
        for (IR child : ir.getChildren()) {
            RenameImpl(child, visitor);
        }
    }

    private class RenameVisitor extends IRVisitor<Boolean> {
        private final ArrayList<String> names;

        public RenameVisitor(Renamer renamer) {
            this.names = renamer.names;
        }

        @Override
        public Boolean visit(IR ir) {
            return false;
        }

        @Override
        public Boolean visit(IRVariable ir) {
            IR declaredFrom = ir.getDeclaredFrom();
            assert declaredFrom instanceof IRMemberDecl;
            System.out.println(((IRMemberDecl) declaredFrom).getVariable().getName());
            ir.setName(((IRMemberDecl) declaredFrom).getVariable().getName());
            return true;
        }

        /*
         * visit method decl and rename its name
         */
        @Override
        public Boolean visit(IRMethodDecl ir) {
            IRVariable variable = ir.getVariable();
            variable.setName("Method" + nameId + "_" + variable.getName());
            PutNewName(variable.getName());

            // params will be renamed at visit(IRFieldDecl)
            return true;
        }

        /*
         * visit field decl and rename its name
         */
        @Override
        public Boolean visit(IRFieldDecl ir) {
            // We see parameter as local field
            IRVariable variable = ir.getVariable();
            variable.setName("Variable" + nameId + "_" + variable.getName());
            PutNewName(variable.getName());
            return true;
        }

        /*
         * visit import decl and rename its name
         */

        @Override
        public Boolean visit(IRImportDecl ir) {
            IRVariable variable = ir.getVariable();
            variable.setName("Import" + "_" + variable.getName());
            return true;
        }

        private void PutNewName(String name) {
            names.add(name);
            nameId++;
        }
    }
}
