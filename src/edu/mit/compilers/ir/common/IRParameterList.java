package edu.mit.compilers.ir.common;

import edu.mit.compilers.ir.decl.IRFieldDecl;

import java.util.ArrayList;

public class IRParameterList extends IR {

	private ArrayList<IRFieldDecl> paraList;
	public IRParameterList(ArrayList<IRFieldDecl> paraList) {
		super("IRFormalParaList");
		this.paraList = paraList;
	}

	public ArrayList<IRFieldDecl> getParaList() {
		return paraList;
	}

	public IRFieldDecl get(int index) {
		return getParaList().get(index);
	}

	public int size() {
		return getParaList().size();
	}

	@Override
	public ArrayList<IR> getChildren() {
		ArrayList<IR> ret = new ArrayList<>(paraList);
		return ret;
	} 

	@Override
	public <T> T accept(IRVisitor<T> visitor) {
		return visitor.visit(this);
	}

}
