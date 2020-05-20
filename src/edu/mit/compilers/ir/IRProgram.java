package edu.mit.compilers.ir;

import java.util.ArrayList;

import edu.mit.compilers.ir.decl.IRFieldDecl;
import edu.mit.compilers.ir.decl.IRImportDecl;
import edu.mit.compilers.ir.decl.IRMethodDecl;

public class IRProgram extends IR {

	private ArrayList<IRImportDecl> importDecls;
	private ArrayList<IRFieldDecl> fieldDecls;
	private ArrayList<IRMethodDecl> methodDecls;
	
	public IRProgram() {
		super("IRProgram");
		importDecls = new ArrayList<IRImportDecl>();
		fieldDecls = new ArrayList<IRFieldDecl>();
		methodDecls = new ArrayList<IRMethodDecl>();
	}

	@Override
	public <T> T accept(IRVisitor<T> visitor) {
		// TODO Auto-generated method stub
		return visitor.visit(this);
	}
	
	public void addIRImportDecl(IRImportDecl decl) {
		importDecls.add(decl);
	}
	
	public void addIRFieldDecl(IRFieldDecl decl) {
		fieldDecls.add(decl);
	}
	
	public void addMethodDecls(IRMethodDecl decl) {
		methodDecls.add(decl);
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
}
