package edu.mit.compilers.semantic.checker;

import edu.mit.compilers.ir.common.IR;
import edu.mit.compilers.ir.common.IRVisitor;
import edu.mit.compilers.semantic.EnvStack;

public class SemanticRule extends IRVisitor<SemanticError> {
	
	private EnvStack env = null;
	
	@Override
	public SemanticError visit(IR ir) {
		return SemanticError.NoError;
	}

	public EnvStack getEnv() {
		return env;
	}

	public void setEnv(EnvStack env) {
		this.env = env;
	}
	
	
}
