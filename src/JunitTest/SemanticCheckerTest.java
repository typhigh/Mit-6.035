package JunitTest;

import JunitTest.TestTool.IRMaker;
import edu.mit.compilers.ir.common.IRBlock;
import edu.mit.compilers.ir.common.IRProgram;
import edu.mit.compilers.ir.decl.IRMethodDecl;
import edu.mit.compilers.semantic.checker.SemanticChecker;
import edu.mit.compilers.semantic.checker.SemanticError;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SemanticCheckerTest {

    @Test
    void testSimpleCase() {
        /*
            import x;
            int a;
            bool b;
            int c[2];
            void d();
         */

        IRProgram program = new IRProgram();
        program.addIRImportDecl(IRMaker.makeIRImportDecl("x"));
        program.addIRFieldDecl(IRMaker.makeIRFieldDecl("int", "a"));
        program.addIRFieldDecl(IRMaker.makeIRFieldDecl("bool", "b"));
        program.addIRFieldDecl(IRMaker.makeIRFieldDecl("int", true, 2, "c"));
        program.addMethodDecl(IRMaker.makeIRMethodDecl("void", "d", null, null));

        IRBlock block = new IRBlock();
        block.addFieldDecl(IRMaker.makeIRFieldDecl("int", "c"));
        IRMethodDecl main = IRMaker.makeMainMethod(block);
        program.addMethodDecl(main);

        /*
         * void main() {
         *      int c;
         *      int c;
         * }
         */
        SemanticChecker checker = new SemanticChecker();
        {
            checker.init("test");
            ArrayList<SemanticError> errors = checker.check(program);
            assertTrue(errors.isEmpty());
            checker.reportErrors();
        }

        {
            block.addFieldDecl(IRMaker.makeIRFieldDecl("int", "c"));
            checker.init("test");
            ArrayList<SemanticError> errors = checker.check(program);
            assertFalse(errors.isEmpty());
            checker.reportErrors();
        }
    }
}
