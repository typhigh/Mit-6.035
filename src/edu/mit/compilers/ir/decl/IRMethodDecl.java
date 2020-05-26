package edu.mit.compilers.ir.decl;

import java.util.ArrayList;

import antlr.Token;
import edu.mit.compilers.ir.IRVisitor;
import edu.mit.compilers.ir.statement.IRBlock;
import edu.mit.compilers.semantic.BasicTypeDesc;
import edu.mit.compilers.semantic.Variable;

public class IRMethodDecl extends IRMemberDecl{
	
	private BasicTypeDesc returnType;
	private IRBlock block;
	private ArrayList<Variable> Variables;
	
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

	@Override
	public void showTreeImpl(String prefix, StringBuilder result) {
		String info = prefix + 
				" DebugID: " + getDebugID() +
				" Tag: " + getTag() + 
				" Return Type: " + returnType.toString() + 
				" Identifier : " + getName() + '\n';
		result.append(info);
		
		assert(Variables != null);
		// Variables 
		for (int i = 0; i < Variables.size(); ++i) {
			String subInfo = prefix + " " + Variables.get(i).toString() + '\n';
			result.append(subInfo);
		}
		
		// Block
		block.showTreeImpl(prefix + " ", result);
	}
	
}
