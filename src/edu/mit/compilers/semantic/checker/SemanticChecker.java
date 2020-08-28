package edu.mit.compilers.semantic.checker;

import edu.mit.compilers.ir.common.IR;
import edu.mit.compilers.semantic.EnvStack;
import edu.mit.compilers.semantic.SimpleEnvStack;

import java.util.ArrayList;

public class SemanticChecker {

    private EnvStack env = null;
    private String fileName = null;
    private ArrayList<SemanticRule> rulesDoneBefore = new ArrayList<>();
    private ArrayList<SemanticRule> rulesDoneAfter = new ArrayList<>();
    private ArrayList<SemanticError> errors = new ArrayList<>();

    /*
        Must invoked before check
     */
    public void init(String file) {
        assert file != null;
        env = new SimpleEnvStack();
        fileName = file;
        errors.clear();

        rulesDoneBefore.clear();
        rulesDoneBefore.add(new PushBlockRule());
        rulesDoneBefore.add(new DeclareRule());

        rulesDoneAfter.clear();
        rulesDoneAfter.add(new TypeRule());
        rulesDoneAfter.add(new PopBlockRule());
    }

    public ArrayList<SemanticError> check(IR root) {
        checkImpl(root);
        return errors;
    }

    private void checkImpl(IR node) {
        // Top-down check
        ArrayList<IR> children = node.getChildren();
        for (SemanticRule rule : rulesDoneBefore) {
            SemanticError error = node.accept(rule);
            if (error.hasError()) {
                errors.add(error);
            }
        }

        for (IR child : children) {
            checkImpl(child);
        }

        for (SemanticRule rule : rulesDoneAfter) {
            SemanticError error = node.accept(rule);
            if (error.hasError()) {
                errors.add(error);
            }
        }
    }

}
