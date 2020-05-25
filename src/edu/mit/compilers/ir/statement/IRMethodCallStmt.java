package edu.mit.compilers.ir.statement;

import java.util.ArrayList;

import edu.mit.compilers.ir.IRVisitor;
import edu.mit.compilers.semantic.Identifier;

public class IRMethodCallStmt extends IRStatement {
	
	// Called function identifier
	private final Identifier identifier;
	private final ArrayList<IRImportArg> args;
	
	public IRMethodCallStmt(Identifier identifier, ArrayList<IRImportArg> args) {
		super("IRMethodCallStmt");
		this.identifier = identifier;
		this.args = args;
		assert(args != null);
	}

	public Identifier getIdentifier() {
		return identifier;
	}
	
	public String getName() {
		return identifier.name;
	}

	public ArrayList<IRImportArg> getArgs() {
		return args;
	}

	@Override
	public <T> T accept(IRVisitor<T> visitor) {
		return visitor.visit(this);
	}

	@Override
	public void showTreeImpl(String prefix, StringBuilder result) {
		String info = prefix + 
				" DebugID: " + getDebugID() + 
				" Tag: " + getTag() + 
				" Identifier: " + getName() + '\n';
		result.append(info);
		for (int i = 0; i < args.size(); ++i) {
			args.get(i).showTreeImpl(prefix + " ", result);
		}
	}
}
