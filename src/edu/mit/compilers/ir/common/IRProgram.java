package edu.mit.compilers.ir.common;

import edu.mit.compilers.ir.decl.IRFieldDecl;
import edu.mit.compilers.ir.decl.IRImportDecl;
import edu.mit.compilers.ir.decl.IRMethodDecl;
import edu.mit.compilers.utils.IRCloneHelper;

import java.util.ArrayList;

public class IRProgram extends IR {

	private ArrayList<IRImportDecl> importDecls = new ArrayList<>();
	private ArrayList<IRFieldDecl> fieldDecls = new ArrayList<>();
	private ArrayList<IRMethodDecl> methodDecls = new ArrayList<>();
	
	public IRProgram() {
		super("IRProgram");
	}
	
	public void addIRImportDecl(IRImportDecl decl) {
		decl.setGlobal(true);
		importDecls.add(decl);
	}
	
	public void addMethodDecl(IRMethodDecl decl) {
		decl.setGlobal(true);
		methodDecls.add(decl);
	}

	public void addIRFieldDecl(IRFieldDecl decl) {
		decl.setGlobal(true);
		fieldDecls.add(decl);
	}

	public void addIRFieldDecls(ArrayList<IRFieldDecl> decls) {
		for (IRFieldDecl decl : decls) {
			decl.setGlobal(true);
		}
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
	public IRProgram clone() throws CloneNotSupportedException {
		IRProgram clone = (IRProgram) super.clone();
		clone.importDecls = IRCloneHelper.getArrayClone(importDecls);
		clone.fieldDecls = IRCloneHelper.getArrayClone(fieldDecls);
		clone.methodDecls = IRCloneHelper.getArrayClone(methodDecls);
		return clone;
	}

	@Override
	public <T> T accept(IRVisitor<T> visitor) throws CloneNotSupportedException {
		return visitor.visit(this);
	}
}
