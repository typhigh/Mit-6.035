package JunitTest;

import JunitTest.TestTool.IRMaker;
import edu.mit.compilers.semantic.EnvStack;
import edu.mit.compilers.semantic.SimpleEnvStack;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class SemanticCheckerTest {

    @Test
    void testSimpleCase() {
        /*
            int a;
            bool b;
            int c[2];
            void d();
         */
        EnvStack env = new SimpleEnvStack();
        env.setGlobalEnv(new ArrayList<>());
        env.pushBlock();
        env.pushMemberDecl(IRMaker.makeIRFieldDecl("int", "a"));
        env.pushMemberDecl(IRMaker.makeIRFieldDecl("bool", "b"));
        env.pushMemberDecl(IRMaker.makeIRFieldDecl("int", true, 2, "c"));
        env.pushMemberDecl(IRMaker.makeIRMethodDecl("void", "d"));
        //TODO
    }
}
