package edu.mit.compilers.ir;

public abstract class IR {
	private String name;
	
	public abstract<T> T accept(IRVisitor<T> visitor);

	public IR(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
