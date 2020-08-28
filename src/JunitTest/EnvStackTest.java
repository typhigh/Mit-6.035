package JunitTest;

import JunitTest.TestTool.IRMaker;
import edu.mit.compilers.ir.decl.IRFieldDecl;
import edu.mit.compilers.ir.decl.IRMemberDecl;
import edu.mit.compilers.semantic.EnvStack;
import edu.mit.compilers.semantic.SimpleEnvStack;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author typhigh
 *
 */
class EnvStackTest {

	@Test
	void testSimple() {
		/*
		 * global : {
		 *    int g[2];
		 * }
		 * int a;
		 * read(g);
		 * read(a);
		 * int b;
		 * {
		 *    int c;
		 *    read(b);
		 *    read(c);
		 *    read(d);
		 * }
		 * int d;
		 * read(d);
		 * read(c);
		 */

		EnvStack env = new SimpleEnvStack();

		// Set global env
		ArrayList<IRMemberDecl> decls = new ArrayList<>();
		decls.add(IRMaker.makeIRFieldDecl("int", true, 2, "g"));
		env.setGlobalEnv(decls);
		
		env.pushBlock();
		IRFieldDecl decl1 = IRMaker.makeIRFieldDecl("int", "a");
		env.pushMemberDecl(decl1);
		assertTrue(env.contain(IRMaker.makeIRVariable("a")));
		assertTrue(env.contain(IRMaker.makeIRVariable("g")));
		IRFieldDecl decl2 = IRMaker.makeIRFieldDecl("int", "b");
		env.pushMemberDecl(decl2);

		env.pushBlock();
		IRFieldDecl decl3 = IRMaker.makeIRFieldDecl("int", "c");
		env.pushMemberDecl(decl3);
		assertTrue(env.contain(IRMaker.makeIRVariable("b")));
		assertTrue(env.contain(IRMaker.makeIRVariable("c")));
		assertFalse(env.contain(IRMaker.makeIRVariable("d")));
		env.popBlock();

		IRFieldDecl decl4 = IRMaker.makeIRFieldDecl("int", "d");
		env.pushMemberDecl(decl4);
		assertTrue(env.contain(IRMaker.makeIRVariable("d")));
		assertFalse(env.contain(IRMaker.makeIRVariable("c")));
		env.popBlock();
	}

}
