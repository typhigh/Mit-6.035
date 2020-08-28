package edu.mit.compilers.ir.decl;

import edu.mit.compilers.ir.common.IR;
import edu.mit.compilers.ir.common.IRParameterList;
import edu.mit.compilers.ir.common.IRVariable;
import edu.mit.compilers.ir.common.IRVisitor;
import edu.mit.compilers.ir.common.IRBlock;
import edu.mit.compilers.ir.type.IRBasicType;

import java.util.ArrayList;

public class IRMethodDecl extends IRMemberDecl{

	// Return type
	private IRBasicType type;
	private IRBlock block;
	private IRParameterList paraList;
	
	public IRMethodDecl(IRVariable variable, IRBasicType type, IRParameterList paraList, IRBlock block) {
		super("IRMethodDecl", variable);
		this.type = type;
		this.block = block;
		this.paraList = paraList;
	}
	
	public IRBasicType getType() {
		return type;
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
		ret.add(type);
		ret.add(paraList);
		ret.add(block);
		return ret;
	}
	
	@Override
	public <T> T accept(IRVisitor<T> visitor) {
		return visitor.visit(this);
	}
}
