package edu.mit.compilers.ir;

import edu.mit.compilers.ir.decl.IRFieldDecl;
import edu.mit.compilers.ir.decl.IRImportDecl;
import edu.mit.compilers.ir.decl.IRMemberDecl;
import edu.mit.compilers.ir.decl.IRMethodDecl;
import edu.mit.compilers.ir.expression.IRBinaryOpExpr;
import edu.mit.compilers.ir.expression.IREmptyExpr;
import edu.mit.compilers.ir.expression.IRExpression;
import edu.mit.compilers.ir.expression.IRLenExpr;
import edu.mit.compilers.ir.expression.IRLocation;
import edu.mit.compilers.ir.expression.IRMethodCall;
import edu.mit.compilers.ir.expression.IRTernaryExpr;
import edu.mit.compilers.ir.expression.IRUnaryOpExpr;
import edu.mit.compilers.ir.expression.literal.IRBoolLiteral;
import edu.mit.compilers.ir.expression.literal.IRCharLiteral;
import edu.mit.compilers.ir.expression.literal.IRIntLiteral;
import edu.mit.compilers.ir.expression.literal.IRStringLiteral;
import edu.mit.compilers.ir.statement.IRAssignStmt;
import edu.mit.compilers.ir.statement.IRBlock;
import edu.mit.compilers.ir.statement.IRBreakStmt;
import edu.mit.compilers.ir.statement.IRContinueStmt;
import edu.mit.compilers.ir.statement.IRForStmt;
import edu.mit.compilers.ir.statement.IRIfStmt;
import edu.mit.compilers.ir.statement.IRMethodCallStmt;
import edu.mit.compilers.ir.statement.IRReturnStmt;
import edu.mit.compilers.ir.statement.IRStatement;
import edu.mit.compilers.ir.statement.IRWhileStmt;

/*
 * IRVisit: for semantic-check and optimizer
 */
public abstract class IRVisitor<T> {
	
	public abstract T visit(IR ir);

	/*IR*/
	/**********************************/
	public T visit(IRProgram ir) {
		return visit((IR) ir);
	}
	
	public T visit(IRMemberDecl ir) {
		return visit((IR) ir);
	}
	
	public T visit(IRBlock ir) {
		return visit((IR) ir);
	}
	
	public T visit(IRExpression ir) {
		return visit((IR) ir);
	}
	
	/*IRMemberDecl*/
	/**********************************/
	public T visit(IRFieldDecl ir) {
		return visit((IRMemberDecl) ir);
	}
	
	public T visit(IRImportDecl ir) {
		return visit((IRMemberDecl) ir);
	}
	
	public T visit(IRMethodDecl ir) {
		return visit((IRMemberDecl) ir);
	}
	
	public T visit(IRStatement ir) {
		return visit((IR) ir);
	}
	
	/*IRExpression*/
	/**********************************/
	public T visit(IRBinaryOpExpr ir) {
		return visit((IRExpression) ir);
	}
	
	public T visit(IREmptyExpr ir) {
		return visit((IRExpression) ir);
	}
	
	public T visit(IRLenExpr ir) {
		return visit((IRExpression) ir);
	}
	
	public T visit(IRLocation ir) {
		return visit((IRExpression) ir);
	}
	
	public T visit(IRMethodCall ir) {
		return visit((IRExpression) ir);
	}
	
	public T visit(IRTernaryExpr ir) {
		return visit((IRExpression) ir);
	}
	
	public T visit(IRUnaryOpExpr ir) { 
		return visit((IRExpression) ir);
	}
	
	public T visit(IRBoolLiteral ir) {
		return visit((IRExpression) ir);
	}
	
	public T visit(IRCharLiteral ir) {
		return visit((IRExpression) ir);
	}
	
	public T visit(IRIntLiteral ir) {
		return visit((IRExpression) ir);
	}
	
	public T visit(IRStringLiteral ir) {
		return visit((IRExpression) ir);
	}
	
	/*IRStatement*/
	/**********************************/
	public T visit(IRAssignStmt ir) {
		return visit((IRStatement) ir);
	}
	
	public T visit(IRBreakStmt ir) {
		return visit((IRStatement) ir);
	}
	
	public T visit(IRContinueStmt ir) {
		return visit((IRStatement) ir);
	}
	
	public T visit(IRForStmt ir) {
		return visit((IRStatement) ir);
	}
	
	public T visit(IRIfStmt ir) {
		return visit((IRStatement) ir);
	}
	
	public T visit(IRMethodCallStmt ir) {
		return visit((IRStatement) ir);
	}
	
	public T visit(IRReturnStmt ir) {
		return visit((IRStatement) ir);
	}
	
	public T visit(IRWhileStmt ir) {
		return visit((IRStatement) ir);
	}
}

