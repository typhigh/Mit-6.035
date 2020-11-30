package edu.mit.compilers.semantic.checker;

import edu.mit.compilers.ir.common.IR;
import edu.mit.compilers.semantic.EnvStack;
import edu.mit.compilers.semantic.SimpleEnvStack;

import java.util.ArrayList;

public class SemanticChecker {

    private EnvStack env = null;
    private String fileName = null;
    private String output = null;
    private ArrayList<SemanticRule> rulesDoneBefore = new ArrayList<>();
    private ArrayList<SemanticRule> rulesDoneAfter = new ArrayList<>();
    private ArrayList<SemanticError> errors = new ArrayList<>();

    /*
     *   Must invoked before check
     */

    public void init() {
        init("");
    }

    public void init(String file) {
        assert file != null;
        env = new SimpleEnvStack();
        fileName = file;
        errors.clear();

        rulesDoneBefore.clear();
        rulesDoneBefore.add(new SetParentHepler());
        rulesDoneBefore.add(new SetCoveredMethodHelper());

        rulesDoneBefore.add(new BreakContinueRule());
        rulesDoneBefore.add(new DeclareRule());
        rulesDoneBefore.add(new MainMethodRule());
        rulesDoneBefore.add(new PushBlockRule());
        rulesDoneBefore.add(new LiteralValueRule());

        rulesDoneAfter.clear();
        rulesDoneAfter.add(new ArgumentRule());
        rulesDoneAfter.add(new TypeRule());
        rulesDoneAfter.add(new PopBlockRule());
        rulesDoneAfter.add(new MethodCallRule());
        rulesDoneAfter.add(new ArrayLenRule());
        rulesDoneAfter.add(new ReturnStmtRule());

        for (SemanticRule rule : rulesDoneBefore) {
            rule.setEnv(env);
        }

        for (SemanticRule rule : rulesDoneAfter) {
            rule.setEnv(env);
        }
    }

    public void reportErrors() {
        for (SemanticError error : errors) {
            if (output == null) {
                System.out.println(fileName + ":" + error.toString());
            } else {
                // TODO : support file output
                assert false;
            }
        }
    }

    public ArrayList<SemanticError> check(IR root) throws CloneNotSupportedException {
        checkImpl(root);
        return errors;
    }

    private void checkImpl(IR node) throws CloneNotSupportedException {
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
