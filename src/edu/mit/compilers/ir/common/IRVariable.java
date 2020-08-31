package edu.mit.compilers.ir.common;

import antlr.Token;
import edu.mit.compilers.ir.decl.IRMemberDecl;

import java.util.ArrayList;

public class IRVariable extends IR {
	private String name;

	// Helper info filled by semantic checker
	private IRMemberDecl declaredFrom = null;

	public IRVariable(String name, int line, int column) {
		super("IRVariable");
		this.name = name;
		setLine(line);
		setColumn(column);
	}
	
	public IRVariable (Token token) {
		this(token.getText(),token.getLine(), token.getColumn());
	}

	public String getName() {
		return name;
	}

	public IRMemberDecl getDeclaredFrom() {
		return declaredFrom;
	}

	public void setDeclaredFrom(IRMemberDecl declaredFrom) {
		this.declaredFrom = declaredFrom;
	}

	@Override
	public ArrayList<IR> getChildren() {
		return getEmptyChildren();
	}
	
	@Override
	public <T> T accept(IRVisitor<T> visitor) {
		return visitor.visit(this);
	}

	@Override
	public String getInfoForShow(String prefix) {
		return prefix +
				" DebugID: " + getDebugID() +
				" Tag: " + getTag() +
				" Name: " + getName() + '\n';
	}

	@Override
	public String toString() {
		return getName();
	}

	public boolean hasSameName(IRVariable variable) {
		return getName().equals(variable.getName());
    }
}
