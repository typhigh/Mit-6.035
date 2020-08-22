package edu.mit.compilers.ir.common;

import java.util.ArrayList;

public class IRParameterList extends IR {

	private ArrayList<IRParameter> paraList;
	public IRParameterList(ArrayList<IRParameter> paraList) {
		super("IRFormalParaList");
		this.paraList = paraList;
	}

	public ArrayList<IRParameter> getParaList() {
		return paraList;
	}
	
	@Override
	public ArrayList<IR> getChildren() {
		ArrayList<IR> ret = new ArrayList<IR>(paraList);
		for (IR para : paraList) {
			ret.add(para);
		}
		return ret;
	} 

	@Override
	public <T> T accept(IRVisitor<T> visitor) {
		return visitor.visit(this);
	}

}
