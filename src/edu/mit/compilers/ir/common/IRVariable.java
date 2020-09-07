package edu.mit.compilers.ir.common;

import antlr.Token;
import edu.mit.compilers.ir.decl.IRMemberDecl;
import edu.mit.compilers.utils.StringInfo;

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
	public StringInfo getInfoForShow(String prefix) {
		StringInfo ret = super.getInfoForShow(prefix).addInfo("Name: " + getName());
		if (getDeclaredFrom() != null) {
			ret.addInfo("DeclaredID: " + getDeclaredFrom().getDebugID());
		}
		return ret;
	}

	@Override
	public String toString() {
		return getName();
	}

	public boolean hasSameName(IRVariable variable) {
		return getName().equals(variable.getName());
    }
}
