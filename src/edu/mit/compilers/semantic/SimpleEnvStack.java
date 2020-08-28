package edu.mit.compilers.semantic;

import edu.mit.compilers.ir.common.IRVariable;
import edu.mit.compilers.ir.decl.IRMemberDecl;

import java.util.ArrayList;

public class SimpleEnvStack implements EnvStack {

	private final ArrayList<IRMemberDecl> stacks;
	private final ArrayList<Integer> blockBases;

	public SimpleEnvStack() {
		stacks = new ArrayList<>();
		blockBases = new ArrayList<>();
		blockBases.add(0);
	}

	@Override
	public boolean pushMemberDecl(IRMemberDecl decl) {
		assert blockBases.size() >= 1 : "must have at least one block";
		return stacks.add(decl);
	}

	@Override
	public void clear() {
		stacks.clear();
		blockBases.clear();
		blockBases.add(0);
	}

	@Override
	public IRMemberDecl seek(IRVariable identifier) {
		for (int i = stacks.size() - 1; i >= 0; --i) {
			// from top to down
			IRMemberDecl decl = stacks.get(i);
			if (decl.getVariable().hasSameName(identifier)) {
				return decl;
			}
		}
		return null;
	}

	@Override
	public boolean contain(IRVariable identifier) {
		return seek(identifier) != null;
	}

	@Override
	public void pushBlock() {
		blockBases.add(stacks.size());
	}

	@Override
	public void popBlock() {
		assert blockBases.size() >= 1 : "must have at least one block";
		int base = blockBases.get(blockBases.size() - 1);

		// Remove this block
		if (stacks.size() > base) {
			stacks.subList(base, stacks.size()).clear();
		}
		blockBases.remove(blockBases.size() - 1);
	}

	@Override
	public ArrayList<IRMemberDecl> getGlobalDecls() {
		// [0, end)
		int end = blockBases.size() == 1 ? stacks.size() : blockBases.get(1);
		ArrayList<IRMemberDecl> ret = new ArrayList<>();
		for (int i = 0; i < end; ++i) {
			ret.add(stacks.get(i));
		}
		return ret;
	}

	@Override
	public boolean setGlobalEnv(EnvStack env) {
		return setGlobalEnv(env.getGlobalDecls());
	}

	@Override
	public boolean setGlobalEnv(ArrayList<IRMemberDecl> decls) {
		assert blockBases.size() == 1 : "must have no block";
		clear();
		return stacks.addAll(decls);
	}
}