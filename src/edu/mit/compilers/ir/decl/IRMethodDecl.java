package edu.mit.compilers.ir.decl;

import edu.mit.compilers.ir.common.IR;
import edu.mit.compilers.ir.common.IRParameterList;
import edu.mit.compilers.ir.common.IRVariable;
import edu.mit.compilers.ir.common.IRVisitor;
import edu.mit.compilers.ir.common.IRBlock;
import edu.mit.compilers.ir.statement.IREmptyStmt;
import edu.mit.compilers.ir.statement.IRStatement;
import edu.mit.compilers.ir.type.IRBasicType;

import java.util.ArrayList;

public class IRMethodDecl extends IRMemberDecl{

	// Return type
	private IRBasicType type;
	private IRBlock block;
	private IRParameterList paraList;

	// end statement following return statement used for lower code convertor
	// just a empty statement
	private IRStatement emptyEndLabel = new IREmptyStmt();

	public IRMethodDecl(IRVariable variable, IRBasicType type, IRParameterList paraList, IRBlock block) {
		super("IRMethodDecl", variable);
		this.type = type;
		this.block = block;
		this.paraList = paraList;
		this.block.setNewScope(false);
	}

	public IRStatement getEmptyEndLabel() {
		return emptyEndLabel;
	}

	public void setEmptyEndLabel(IRStatement emptyEndLabel) {
		this.emptyEndLabel = emptyEndLabel;
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
		ret.add(emptyEndLabel);
		return ret;
	}
	
	@Override
	public <T> T accept(IRVisitor<T> visitor) {
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
		clone.emptyEndLabel = emptyEndLabel.clone();
		return clone;
	}
}
