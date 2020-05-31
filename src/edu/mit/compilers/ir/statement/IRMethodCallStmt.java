package edu.mit.compilers.ir.statement;

import java.util.ArrayList;

import edu.mit.compilers.ir.IR;
import edu.mit.compilers.ir.IRVisitor;
import edu.mit.compilers.ir.expression.IRExpression;
import edu.mit.compilers.ir.expression.IRMethodCall;
import edu.mit.compilers.semantic.Identifier;

public class IRMethodCallStmt extends IRStatement {
		
	private IRMethodCall methodCall;
	
	public IRMethodCallStmt(Identifier identifier, ArrayList<IRExpression> args) {
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

	public ArrayList<IRExpression> getArgs() {
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

	@Override
	public ArrayList<IR> getChildren() {
		ArrayList<IR> ret = new ArrayList<IR>();
		ret.add(methodCall);
		return ret;
	}
}
