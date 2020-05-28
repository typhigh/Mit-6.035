package edu.mit.compilers.semantic;

import edu.mit.compilers.ir.IR;
import edu.mit.compilers.ir.decl.IRMemberDecl;

public interface EnvStack {
	/*
	 * Push IRFieldDecl to the EnvStack 
	 */
	public boolean pushMemberDecl(String identifier, IRMemberDecl decl);
	
	/*
	 * Pop field or method decl by identifier from top to down
	 */
	public boolean popMemberDecl(String identifier);
	
	/*
	 * Pop field or method decl with given count
	 */
	public boolean popMemberDecl(int count);
	
	/*
	 * Clear the env stack
	 */
	public void clear();
	
	/*
	 * Whether contain the identifier
	 * If contain the identifier, return the "ir" (method or field decl) from top to down
	 * Otherwise, return null
	 */
	public IR contain(String identifier);
}
