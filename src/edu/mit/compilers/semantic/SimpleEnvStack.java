package edu.mit.compilers.semantic;

import java.util.ArrayList;

import edu.mit.compilers.ir.common.IR;
import edu.mit.compilers.ir.common.IRVariable;
import edu.mit.compilers.ir.decl.IRMemberDecl;

public class SimpleEnvStack implements EnvStack {

	@Override
	public boolean pushMemberDecl(IRMemberDecl decl) {
		// TODO Auto-generated method stub
		assert(false);
		return false;
	}

	@Override
	public boolean popMemberDecl(IRMemberDecl decl) {
		// TODO Auto-generated method stub
		assert(false);
		return false;
	}

	@Override
	public boolean popMemberDecl(int count) {
		// TODO Auto-generated method stub
		assert(false);
		return false;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		assert(false);

	}

	@Override
	public IR seek(IRVariable identifier) {
		// TODO Auto-generated method stub
		assert(false);
		return null;
	}

	@Override
	public boolean contain(IRVariable identifier) {
		// TODO Auto-generated method stub
		assert(false);
		return false;
	}

	@Override
	public void pushEnv() {
		// TODO Auto-generated method stub
		assert(false);

	}

	@Override
	public void popEnv() {
		// TODO Auto-generated method stub
		assert(false);
	}

	@Override
	public boolean SetGlobalEnv(EnvStack env) {
		// TODO Auto-generated method stub
		assert(false);
		return false;
	}

	@Override
	public boolean SetGlobalEnc(ArrayList<IRMemberDecl> decls) {
		// TODO Auto-generated method stub
		assert(false);
		return false;
	}
	
}