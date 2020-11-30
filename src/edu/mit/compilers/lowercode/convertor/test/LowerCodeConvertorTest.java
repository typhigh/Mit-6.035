package edu.mit.compilers.lowercode.convertor.test;

import JunitTest.TestTool.IRMaker;
import edu.mit.compilers.ir.common.IRProgram;
import edu.mit.compilers.ir.decl.IRFieldDecl;
import edu.mit.compilers.ir.statement.IRAssignStmt;
import edu.mit.compilers.ir.statement.IRStatement;
import edu.mit.compilers.utils.MainController;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class LowerCodeConvertorTest {

    @Test
    public void testAssignStmt() throws CloneNotSupportedException {
        {
            /*
             * int a;
             * a = 1
             */
            IRFieldDecl a = IRMaker.makeIRFieldDecl("int", "a");
            IRStatement s1 = new IRAssignStmt(a.getVariable(), IRMaker.makeIRIntLiteral("1"));
            ArrayList<IRFieldDecl> fields = new ArrayList<>();
            ArrayList<IRStatement> stmts = new ArrayList<>();
            fields.add(a);
            stmts.add(s1);
            IRProgram program = IRMaker.makeSimpleIRProgram(fields, stmts);
            process(program, true);
        }
    }

    private void process(IRProgram program, boolean debug) throws CloneNotSupportedException {
        MainController controller = new MainController(debug);
        controller.setProgram(program, false);
        controller.process(MainController.State.LOWERCODE_GENED);
    }
}
