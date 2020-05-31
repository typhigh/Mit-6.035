package edu.mit.compilers.ir.expression;

import java.util.ArrayList;

import edu.mit.compilers.ir.IR;
import edu.mit.compilers.ir.IRVisitor;
import edu.mit.compilers.semantic.Identifier;

public class IRMethodCall extends IRExpression {

	private Identifier identifier;
	private ArrayList<IRExpression> args;

	public IRMethodCall(Identifier identifier, ArrayList<IRExpression> args) {
		super("IRMethodCall");
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

	public ArrayList<IRExpression> getArgs() {
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

	@Override
	public ArrayList<IR> getChildren() {
		ArrayList<IR> ret = new ArrayList<IR>();
		ret.addAll(args);
		return ret;
	}

}
