package edu.mit.compilers.ir.decl;

import java.util.ArrayList;

import antlr.Token;
import edu.mit.compilers.ir.IR;
import edu.mit.compilers.ir.IRVisitor;
import edu.mit.compilers.semantic.TypeDesc;

public class IRFieldDecl extends IRMemberDecl {
	private TypeDesc type;
	public IRFieldDecl(TypeDesc type, Token token) {
		super("IRFieldDecl", token);
		this.type = type;
	}

	@Override
	public <T> T accept(IRVisitor<T> visitor) {
		return visitor.visit(this);
	}


	@Override
	public void showTreeImpl(String prefix, StringBuilder result) {
		String info = prefix + 
				" DebugID: " + getDebugID() +
				" Tag: " + getTag() + 
				" Type: " + type.toString() + 
				" Identifier : " + getName() + '\n';
		result.append(info);
	}

	@Override
	public ArrayList<IR> getChildren() {
		return null;
	}

}