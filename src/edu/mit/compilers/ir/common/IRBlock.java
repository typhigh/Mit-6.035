package edu.mit.compilers.ir.common;

import edu.mit.compilers.ir.decl.IRFieldDecl;
import edu.mit.compilers.ir.statement.IRStatement;
import edu.mit.compilers.utils.IRCloneHelper;

import java.util.ArrayList;

public class IRBlock extends IR {
	
	private ArrayList<IRFieldDecl> fieldDecls = new ArrayList<>();
	private ArrayList<IRStatement> statements = new ArrayList<>();
	private boolean newScope = true;

	public IRBlock() {
		super("IRBlock");	
	}

	public boolean isNewScope() {
		return newScope;
	}

	public void setNewScope(boolean newScope) {
		this.newScope = newScope;
	}

	public void addFieldDecl(IRFieldDecl decl) {
		this.fieldDecls.add(decl);
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
		ArrayList<IR> ret = new ArrayList<>();
		ret.addAll(fieldDecls);
		ret.addAll(statements);
		return ret;
	}

	@Override
	public IRBlock clone() throws CloneNotSupportedException {
		IRBlock clone = (IRBlock) super.clone();
		clone.fieldDecls = IRCloneHelper.getArrayClone(fieldDecls);
		clone.statements = IRCloneHelper.getArrayClone(statements);
		return clone;
	}
}
