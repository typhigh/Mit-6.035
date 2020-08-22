package edu.mit.compilers.semantic;

import edu.mit.compilers.ir.common.IR;
import edu.mit.compilers.ir.decl.IRMemberDecl;

public interface EnvStack {
	
	/*
	 * Push IRFieldDecl to the EnvStack 
	 */
	public boolean pushMemberDecl(IRMemberDecl decl);
	
	/*
	 * Pop field or method decl by identifier from top to down
	 */
	public boolean popMemberDecl(IRMemberDecl decl);
	
	/*
	 * Pop field or method decl with given count
	 */
	public boolean popMemberDecl(int count);
	
	/*
	 * Clear the env stack
	 */
	public void clear();
	
	/*
	 * Seek the member decl by id
	 * If contain the identifier, return the "ir" (method or field decl) from top to down
	 * Otherwise, return null
	 */
	public IR seek(String identifier);
	
	/*
	 * Whether contain the id
	 */
	public boolean contain(String identifier);
	
	/*
	 * Push new env (maybe global or local block)
	 */
	public void pushEnv();
	
	/*
	 * Pop old env
 	 */
	public void popEnv();
}
