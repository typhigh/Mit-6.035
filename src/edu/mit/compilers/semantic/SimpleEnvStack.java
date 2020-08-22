package edu.mit.compilers.semantic;
import edu.mit.compilers.ir.common.IR;
import edu.mit.compilers.ir.decl.IRMemberDecl;

public class SimpleEnvStack implements EnvStack {

	@Override
	public boolean pushMemberDecl(IRMemberDecl decl) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean popMemberDecl(IRMemberDecl decl) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean popMemberDecl(int count) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IR seek(String identifier) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean contain(String identifier) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void pushEnv() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void popEnv() {
		// TODO Auto-generated method stub
		
	}
	
}