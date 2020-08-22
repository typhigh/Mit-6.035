package edu.mit.compilers.ir.statement;

import java.util.ArrayList;

import edu.mit.compilers.ir.common.IR;
import edu.mit.compilers.ir.common.IRVisitor;
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
	public ArrayList<IR> getChildren() {
		ArrayList<IR> ret = new ArrayList<IR>();
		ret.addAll(fieldDecls);
		ret.addAll(statements);
		return ret;
	}
	
}
