package edu.mit.compilers.ir.decl;

import edu.mit.compilers.ir.common.*;
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
		this.block.setNewScope(false);
		assert paraList != null;
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
	public <T> T accept(IRVisitor<T> visitor) throws CloneNotSupportedException {
		return visitor.visit(this);
	}

	@Override
	public IRMethodDecl clone() throws CloneNotSupportedException {
		IRMethodDecl clone = (IRMethodDecl) super.clone();
		if (type != null) {
			clone.type = type.clone();
		}

		if (paraList != null) {
			clone.paraList = paraList.clone();
		}

		if (block != null) {
			clone.block = block.clone();
		}
		return clone;
	}
}
