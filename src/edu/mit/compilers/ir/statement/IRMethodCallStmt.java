package edu.mit.compilers.ir.statement;

import java.util.ArrayList;

import edu.mit.compilers.ir.common.IR;
import edu.mit.compilers.ir.common.IRArgumentList;
import edu.mit.compilers.ir.common.IRVariable;
import edu.mit.compilers.ir.common.IRVisitor;
import edu.mit.compilers.ir.expression.IRMethodCall;

public class IRMethodCallStmt extends IRStatement {
		
	private IRMethodCall methodCall;
	
	public IRMethodCallStmt(IRVariable variable, IRArgumentList argList) {
		this(new IRMethodCall(variable, argList));
	}

	public IRMethodCallStmt(IRMethodCall methodCall) {
		super("IRMethodCallStmt");
		this.methodCall = methodCall;
	}
	
	public IRMethodCall getMethodCall() {
		return methodCall;
	}
	
	@Override
	public <T> T accept(IRVisitor<T> visitor) {
		return visitor.visit(this);
	}

	@Override
	public ArrayList<IR> getChildren() {
		ArrayList<IR> ret = new ArrayList<IR>();
		ret.add(methodCall);
		return ret;
	}
}
