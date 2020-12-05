package edu.mit.compilers.lowercode.convertor;

import edu.mit.compilers.ir.common.IR;
import edu.mit.compilers.ir.common.IRVisitor;

import java.util.ArrayList;

public class PreProcessors {
    private final ArrayList<IRVisitor<Void>> processors;

    public PreProcessors(ArrayList<IRVisitor<Void>> processors) {
        assert processors != null;
        this.processors = processors;
    }

    public void process(IR tree) throws CloneNotSupportedException {
        processImpl(tree);
    }

    private void processImpl(IR ir) throws CloneNotSupportedException {
        for (IRVisitor<Void> processor : processors) {
            ir.accept(processor);
        }

        for (IR child : ir.getChildren()) {
            processImpl(child);
        }
    }
}
