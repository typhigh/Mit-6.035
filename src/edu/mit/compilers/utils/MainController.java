package edu.mit.compilers.utils;

import edu.mit.compilers.cst.CST;
import edu.mit.compilers.cst.CSTParser;
import edu.mit.compilers.ir.common.IRProgram;
import edu.mit.compilers.lowercode.code.ThreeAddressCodeList;
import edu.mit.compilers.lowercode.convertor.LowerCodeConvertor;
import edu.mit.compilers.lowercode.SymbolTable;
import edu.mit.compilers.semantic.Renamer;
import edu.mit.compilers.semantic.checker.SemanticChecker;
import edu.mit.compilers.semantic.checker.SemanticError;

import java.util.ArrayList;

/*
 * a state machine that controls the compile control flow
 */
public class MainController {

    public enum State {
        UNKNOWN(0),
        PARSED(1),
        INTERED(2),
        SEMANTIC_CHECKED(3),
        RENAMED(4),
        LOWERCODE_GENED(5),
        OPTIMIZED(6);

        private int value;

        private State(int value) {
            this.value = value;
        }

        protected int getValue() {
            return value;
        }
    }

    private State state;

    private boolean debug;

    // need optimization in codegen
    private boolean needOptimized = true;

    // cst tree
    private CST cstTree;

    // program without renaming
    private IRProgram programNotRenamed;

    // current program, maybe be equal with programNotRenamed
    private IRProgram program;

    // three address code current (after codegen)
    private ThreeAddressCodeList codes;

    // three address code not optimized
    private ThreeAddressCodeList codesNotOptimized;

    public MainController() {
        this(false);
    }

    public MainController(boolean debug) {
        this.debug = debug;
        this.state = State.UNKNOWN;
    }

    public void setNeedOptimized(boolean needOptimized) {
        this.needOptimized = needOptimized;
    }

    public CST getCstTree() {
        return cstTree;
    }

    public void setCstTree(CST cstTree) {
        this.cstTree = cstTree;
        updateStateTo(State.PARSED);
    }

    public IRProgram getProgram() {
        return program;
    }

    public State setProgram(IRProgram program, boolean semanticChecked) {
        this.program = program;
        this.programNotRenamed = program;
        return updateStateTo(semanticChecked ? State.SEMANTIC_CHECKED : State.INTERED);
    }

    public State setProgramRenamed(IRProgram program) {
        this.program = program;
        return updateStateTo(State.RENAMED);
    }

    public IRProgram getProgramNotRenamed() {
        return programNotRenamed;
    }

    public ThreeAddressCodeList getCodes() {
        return codes;
    }

    public State setCodes(ThreeAddressCodeList codes, boolean optimized) {
        this.codes = codes;
        if (!optimized) {
            codesNotOptimized = codes;
        }
        return updateStateTo(optimized ? State.OPTIMIZED : State.LOWERCODE_GENED);
    }

    private State updateStateTo(State newState) {
        State oldState = state;
        this.state = newState;
        System.out.println("update main controller state from [" + oldState + "] to [" +  newState + "]");
        return state;
    }

    public State nextStep() throws CloneNotSupportedException {
        switch (state) {
            case PARSED:            return doGenInterCode(cstTree);
            case INTERED:           return doCheckSemantic(program);
            case SEMANTIC_CHECKED:  return doRenamed(program);
            case RENAMED:           return doGenLowerCode(program);
            case LOWERCODE_GENED:   return doOptimize(codes);
            case OPTIMIZED:         return doGenAssembly(codes);
            default:
                throw new RuntimeException("unexpected state " + state);
        }
    }


    private State doGenInterCode(CST cstTree) {
        // prun the tree
        cstTree.PrunTree();
        if (debug) {
            System.out.println(cstTree.show());
        }

        // parse
        IRProgram program = CSTParser.parseIRProgram(cstTree);
        if (debug) {
            System.out.println(program.show());
        }

        return setProgram(program, false);
    }

    private State doCheckSemantic(IRProgram program) throws CloneNotSupportedException {
        SemanticChecker checker = new SemanticChecker();
        checker.init();
        ArrayList<SemanticError> errors = checker.check(program);

        if (debug) {
            System.out.println(program.show());
        }

        if (!errors.isEmpty()) {
            checker.reportErrors();
            System.exit(1);
        }
        return setProgram(program, true);
    }

    private State doRenamed(IRProgram program) throws CloneNotSupportedException {
        Renamer renamer = new Renamer();
        program = (IRProgram) renamer.rename(program.clone());
        if (debug) {
            System.out.println(program.show());
        }
        return setProgramRenamed(program);
    }

    private State doGenLowerCode(IRProgram program) throws CloneNotSupportedException {
        LowerCodeConvertor convertor = new LowerCodeConvertor();
        convertor.convertToLowCode(program);
        ThreeAddressCodeList codes = convertor.getResult().codes;
        SymbolTable symbolTable = convertor.getResult().symbolTable;

        if (debug) {
            System.out.println(symbolTable.show());
            System.out.println(codes.show());
        }
        return setCodes(codes, false);
    }

    private State doOptimize(ThreeAddressCodeList codes) {
        if (!needOptimized) {
            // do nothing
            return setCodes(codes, true);
        }

        // TODO:
        return null;
    }

    private State doGenAssembly(ThreeAddressCodeList codes) {
        // TODO:
        return null;
    }

    public void process(State toState) throws CloneNotSupportedException {
        assert state.getValue() <= toState.getValue();
        int times = toState.getValue() - state.getValue();
        for (int i = 0; i < times; ++i) {
            nextStep();
        }
    }

}
