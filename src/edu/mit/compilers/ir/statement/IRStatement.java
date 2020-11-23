package edu.mit.compilers.ir.statement;

import edu.mit.compilers.ir.common.IR;
import edu.mit.compilers.lowercode.ThreeAddressCodeList;

public class IRStatement extends IR {

	// filled by nextCodesSetter (in lower code convertor)
	private ThreeAddressCodeList nextStmtCodes;

	public ThreeAddressCodeList getNextStmtCodes() {
		return nextStmtCodes;
	}

	public void setNextStmtCodes(ThreeAddressCodeList nextStmtCodes) {
		this.nextStmtCodes = nextStmtCodes;
	}

	public IRStatement(String name) {
		super(name);
	}

	@Override
	public IRStatement clone() throws CloneNotSupportedException {
		return (IRStatement) super.clone();
	}
}
