package edu.mit.compilers.ir;

public abstract class IRVisitor<T> {
	public abstract T visit(IR ir);
}
