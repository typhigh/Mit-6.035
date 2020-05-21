package edu.mit.compilers.ir.statement;

import java.util.ArrayList;

import edu.mit.compilers.ir.IR;
import edu.mit.compilers.ir.IRVisitor;
import edu.mit.compilers.ir.decl.IRFieldDecl;

public class IRBlock extends IR {
	
	private ArrayList<IRFieldDecl> fieldDecls;
	private ArrayList<IRStatement> statements;
	
	public IRBlock() {
		super("IRBlock");
		// TODO
		
	}

	@Override
	public <T> T accept(IRVisitor<T> visitor) {
		return visitor.visit(this);
	}

	@Override
	public void showTreeImpl(String prefix, StringBuilder result) {
		// TODO
	}
	
}
