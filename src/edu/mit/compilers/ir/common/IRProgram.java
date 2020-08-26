package edu.mit.compilers.ir.common;

import edu.mit.compilers.ir.decl.IRFieldDecl;
import edu.mit.compilers.ir.decl.IRImportDecl;
import edu.mit.compilers.ir.decl.IRMethodDecl;

import java.util.ArrayList;

public class IRProgram extends IR {

	private ArrayList<IRImportDecl> importDecls = new ArrayList<>();
	private ArrayList<IRFieldDecl> fieldDecls = new ArrayList<>();
	private ArrayList<IRMethodDecl> methodDecls = new ArrayList<>();
	
	public IRProgram() {
		super("IRProgram");
	}
	
	public void addIRImportDecl(IRImportDecl decl) {
		importDecls.add(decl);
	}
	
	public void addMethodDecl(IRMethodDecl decl) {
		methodDecls.add(decl);
	}
	
	public void addIRFieldDecls(ArrayList<IRFieldDecl> decls) {
		// TODO Auto-generated method stub
		fieldDecls.addAll(decls);
	}
	
	public ArrayList<IRFieldDecl> getFieldDecls() {
		return fieldDecls;
	}

	public ArrayList<IRMethodDecl> getMethodDecls() {
		return methodDecls;
	}

	public ArrayList<IRImportDecl> getImportDecls() {
		return importDecls;
	}

	@Override
	public ArrayList<IR> getChildren() {
		ArrayList<IR> ret = new ArrayList<>();
		ret.addAll(importDecls);
		ret.addAll(fieldDecls);
		ret.addAll(methodDecls);
		return ret;
	}
	
	@Override
	public <T> T accept(IRVisitor<T> visitor) {
		return visitor.visit(this);
	}
}
