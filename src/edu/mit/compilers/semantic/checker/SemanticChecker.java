package edu.mit.compilers.semantic.checker;

import edu.mit.compilers.ir.IR;
import edu.mit.compilers.ir.IRVisitor;
import edu.mit.compilers.semantic.EnvStack;

public class SemanticChecker extends IRVisitor<SemanticError>{

	private EnvStack env = null;
	
	/*
	 * Default check
	 */
	@Override
	public SemanticError visit(IR ir) {
		// TODO Auto-generated method stub
		return SemanticError.NoError;
	}

	public EnvStack getEnv() {
		return env;
	}

	public void setEnv(EnvStack env) {
		this.env = env;
	}

}
