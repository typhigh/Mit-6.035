package edu.mit.compilers.semantic;

import edu.mit.compilers.ir.common.IRVariable;
import edu.mit.compilers.ir.decl.IRMemberDecl;

import java.util.ArrayList;

public interface EnvStack {

	/*
	 * Set global env by other env
	 * Must be invoked before pushing block
	 */
	boolean setGlobalEnv(EnvStack env);
	
	/*
	 * Set global env by decls directly
	 * Must be invoked before pushing block
	 */
	boolean setGlobalEnv(ArrayList<IRMemberDecl> decls);
	
	/*
	 * Push IRFieldDecl to the EnvStack 
	 */
	boolean pushMemberDecl(IRMemberDecl decl);
	
	/*
	 * Clear the env stack
	 */
	void clear();
	
	/*
	 * Seek the member decl by id
	 * If contain the identifier, return the "ir" (method or field decl) from top to down
	 * Otherwise, return null
	 */
	IRMemberDecl seek(IRVariable identifier);

	/*
	 * If the identifier is declared in the current scope, return the last declare
	 * Otherwise return null
	 */
	IRMemberDecl lastDeclaredInCurrentBlock(IRVariable identifier);
	/*
	 * Whether contain the id
	 */
	boolean contain(IRVariable identifier);
	
	/*
	 * Push new block (maybe global or local block)
	 */
	void pushBlock();
	
	/*
	 * Pop old env
 	 */
	void popBlock();

	/*
	 * Get global decls
	 */
	ArrayList<IRMemberDecl> getGlobalDecls();
}
