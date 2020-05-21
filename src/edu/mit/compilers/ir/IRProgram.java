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
	
	public void addMethodDecl(IRMethodDecl decl) {
		methodDecls.add(decl);
	}
	
	public void addIRFieldDecls(ArrayList<IRFieldDecl> parseIRFieldDecls) {
		// TODO Auto-generated method stub
		fieldDecls.addAll(parseIRFieldDecls);
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
	protected void showTreeImpl(String prefix, StringBuilder result) {
		
		String info = prefix + " Tag: " + this.getTag() + '\n';
		result.append(info);
//		System.out.println("done1");
		
		for (int i = 0; i < importDecls.size(); ++i) {
			importDecls.get(i).showTreeImpl(prefix + " ", result);
		}
		
//		System.out.println("done2");
		for (int i = 0; i < fieldDecls.size(); ++i) {
			fieldDecls.get(i).showTreeImpl(prefix + " ", result);
		}
		
//		System.out.print("done3");
		for (int i = 0; i < methodDecls.size(); ++i) {
			methodDecls.get(i).showTreeImpl(prefix + " ", result);
		}
		
//		System.out.print("done4");
	}
}
