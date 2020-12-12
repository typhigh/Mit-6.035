package edu.mit.compilers.lowercode;

import edu.mit.compilers.ir.common.IRVariable;

public class Variable extends Operand {
    private String name;

    private boolean isGlobal;

    // may be this variable is const value
    // otherwise, constValue = null
    private Value constValue;

    public Variable(IRVariable variable) {
        this(variable.getName(), variable.isGlobal());
    }

    public Variable(String name, boolean isGlobal) {
        this.name = name;
        this.isGlobal = isGlobal;
    }

    public boolean isGlobal() {
        return isGlobal;
    }

    public String getName() {
        return name;
    }

    public Value getConstValue() {
        return constValue;
    }

    public void setConstValue(Value constValue) {
        this.constValue = constValue;
    }

    @Override
    public int hashCode() {
        return getName().hashCode();
    }

    @Override
    public String getLiteralString() {
        return name;
    }
}
