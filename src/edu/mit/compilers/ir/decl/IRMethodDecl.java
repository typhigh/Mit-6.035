package edu.mit.compilers.ir.decl;

import java.util.ArrayList;

import antlr.Token;
import edu.mit.compilers.ir.IRVisitor;
import edu.mit.compilers.ir.statement.IRBlock;
import edu.mit.compilers.semantic.BasicTypeDesc;
import edu.mit.compilers.semantic.Variable;

public class IRMethodDecl extends IRMemberDecl{
	
	private final BasicTypeDesc returnType;
	private final IRBlock block;
	private final ArrayList<Variable> Variables;
	
	public IRMethodDecl(Token type, Token name, ArrayList<Variable> Variables, IRBlock block) {
		super("IRMethodDecl", name);
		this.returnType = BasicTypeDesc.GetInstance(type.getText());
		this.block = block;
		this.Variables = Variables;
	}

	@Override
	public <T> T accept(IRVisitor<T> visitor) {
		return visitor.visit(this);
	}

	public BasicTypeDesc getReturnType() {
		return returnType;
	}

	public IRBlock getBlock() {
		return block;
	}

	public ArrayList<Variable> getVariables() {
		return Variables;
	}
	
}
