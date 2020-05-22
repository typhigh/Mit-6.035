package edu.mit.compilers.ir.statement;

import java.util.ArrayList;

import edu.mit.compilers.ir.IR;
import edu.mit.compilers.ir.IRVisitor;
import edu.mit.compilers.ir.decl.IRFieldDecl;

public class IRBlock extends IR {
	
	private ArrayList<IRFieldDecl> fieldDecls = new ArrayList<IRFieldDecl>();
	private ArrayList<IRStatement> statements = new ArrayList<IRStatement>();
	
	public IRBlock() {
		super("IRBlock");	
	}
	
	public void addFieldDecls(ArrayList<IRFieldDecl> decls) {
		this.fieldDecls.addAll(decls);
	}
	
	public ArrayList<IRFieldDecl> getFieldDecls() {
		return fieldDecls;
	}
	
	public ArrayList<IRStatement> getStatements() {
		return statements;
	}
	
	public void addStatement(IRStatement statement) {
		statements.add(statement);
	}	
	
	@Override
	public <T> T accept(IRVisitor<T> visitor) {
		return visitor.visit(this);
	}

	@Override
	public void showTreeImpl(String prefix, StringBuilder result) {
		String info = prefix + 
				" DebugID: " + getDebugID() +
				" Tag: " + getTag() + '\n';
		result.append(info);
		
		for (int i = 0; i < fieldDecls.size(); ++i) {
			fieldDecls.get(i).showTreeImpl(prefix + " ", result);
		}
		
		for (int i = 0; i < statements.size(); ++i) {
			statements.get(i).showTreeImpl(prefix + " ", result);
		}
	}
	
}
