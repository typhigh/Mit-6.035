package edu.mit.compilers.ir.statement;

import edu.mit.compilers.ir.IRVisitor;
import edu.mit.compilers.ir.expression.IRExpression;
import edu.mit.compilers.semantic.Identifier;

public class IRForStmt extends IRStatement {
	
	private final Identifier identifier;
	private final String operator1;
	private final IRExpression expr1;
	private final IRExpression expr2;
	private final IRLocation location;
	private final String operator2;
	private final IRExpression expr3;
	private final IRBlock block;
	
	public IRForStmt(Identifier identifier, String operator1, IRExpression expr1, IRExpression expr2, IRLocation location, 
			String operator2, IRExpression expr3, IRBlock block) {
		super("IRForStmt");
		this.identifier = identifier;
		this.operator1 = operator1;
		this.expr1 = expr1;
		this.expr2 = expr2;
		this.location = location;
		this.operator2 = operator2;
		this.expr3 = expr3;
		this.block = block;
	}

	public IRForStmt(Identifier identifier, String operator1, IRExpression expr1, IRExpression expr2, IRLocation location,
			String operator2, IRBlock block) {
		this(identifier, operator1, expr1, expr2, location, operator2, null, block);
	}

	@Override
	public <T> T accept(IRVisitor<T> visitor) {
		return visitor.visit(this);
	}

	@Override
	public void showTreeImpl(String prefix, StringBuilder result) {
		// TODO Auto-generated method stub
		
	}

}
