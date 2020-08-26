package edu.mit.compilers.ir.decl;

import edu.mit.compilers.ir.common.IR;
import edu.mit.compilers.ir.common.IRParameterList;
import edu.mit.compilers.ir.common.IRVariable;
import edu.mit.compilers.ir.common.IRVisitor;
import edu.mit.compilers.ir.statement.IRBlock;
import edu.mit.compilers.ir.type.IRBasicType;

import java.util.ArrayList;

public class IRMethodDecl extends IRMemberDecl{
	
	private IRBasicType returnType;
	private IRBlock block;
	private IRParameterList paraList;
	
	public IRMethodDecl(IRVariable variable, IRBasicType type, IRParameterList paraList, IRBlock block) {
		super("IRMethodDecl", variable);
		this.returnType = type;
		this.block = block;
		this.paraList = paraList;
	}
	
	public IRBasicType getReturnType() {
		return returnType;
	}

	public IRBlock getBlock() {
		return block;
	}

	public IRParameterList getParaList() {
		return paraList;
	}	

	@Override
	public ArrayList<IR> getChildren() {
		ArrayList<IR> ret = new ArrayList<>();
		ret.add(getVariable());
		ret.add(returnType);
		ret.add(paraList);
		ret.add(block);
		return ret;
	}
	
	@Override
	public <T> T accept(IRVisitor<T> visitor) {
		return visitor.visit(this);
	}
}
