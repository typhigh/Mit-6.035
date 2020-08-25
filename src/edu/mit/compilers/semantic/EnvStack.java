package edu.mit.compilers.semantic;

import edu.mit.compilers.ir.common.IRVariable;
import edu.mit.compilers.ir.decl.IRMemberDecl;

import java.util.ArrayList;

public interface EnvStack {

	/*
	 * Set global env by other env
	 * Must be invoked before pushing block
	 */
	public boolean SetGlobalEnv(EnvStack env);
	
	/*
	 * Set global env by decls directly
	 * Must be invoked before pushing block
	 */
	public boolean SetGlobalEnv(ArrayList<IRMemberDecl> decls);
	
	/*
	 * Push IRFieldDecl to the EnvStack 
	 */
	public boolean pushMemberDecl(IRMemberDecl decl);
	
	/*
	 * Clear the env stack
	 */
	public void clear();
	
	/*
	 * Seek the member decl by id
	 * If contain the identifier, return the "ir" (method or field decl) from top to down
	 * Otherwise, return null
	 */
	public IRMemberDecl seek(IRVariable identifier);
	
	/*
	 * Whether contain the id
	 */
	public boolean contain(IRVariable identifier);
	
	/*
	 * Push new block (maybe global or local block)
	 */
	public void pushBlock();
	
	/*
	 * Pop old env
 	 */
	public void popBlock();

	/*
	 * Get global decls
	 */
	public ArrayList<IRMemberDecl> getGlobalDecls();
}
