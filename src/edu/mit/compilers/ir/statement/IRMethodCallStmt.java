package edu.mit.compilers.ir.statement;

import java.util.ArrayList;

import edu.mit.compilers.ir.IRVisitor;
import edu.mit.compilers.ir.expression.IRMethodCall;
import edu.mit.compilers.semantic.Identifier;

public class IRMethodCallStmt extends IRStatement {
		
	private IRMethodCall methodCall;
	
	public IRMethodCallStmt(Identifier identifier, ArrayList<IRImportArg> args) {
		this(new IRMethodCall(identifier, args));
	}

	public IRMethodCallStmt(IRMethodCall methodCall) {
		super("IRMethodCallStmt");
		this.methodCall = methodCall;
	}

	public Identifier getIdentifier() {
		return methodCall.getIdentifier();
	}
	
	public String getName() {
		return methodCall.getName();
	}

	public ArrayList<IRImportArg> getArgs() {
		return methodCall.getArgs();
	}

	@Override
	public <T> T accept(IRVisitor<T> visitor) {
		return visitor.visit(this);
	}

	@Override
	public void showTreeImpl(String prefix, StringBuilder result) {
		String info = prefix + 
				" DebugID: " + getDebugID() + 
				" Tag: " + getTag() + '\n';
		result.append(info);
		methodCall.showTreeImpl(prefix + " ", result);
	}
}
