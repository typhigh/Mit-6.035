package edu.mit.compilers.lowercode;

import edu.mit.compilers.ir.type.IRType;

public class FieldInfo {
    private IRType type;
    private Variable variable;

    // deleted flag
    private boolean isDeleted;

    public FieldInfo(IRType type, Variable variable) {
        this.type = type;
        this.variable = variable;
        this.isDeleted = false;
        assert variable != null;
        assert type != null;
    }

    public long getSize() {
        return type.getSize();
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public IRType getType() {
        return type;
    }

    public void setType(IRType type) {
        this.type = type;
    }

    public Variable getVariable() {
        return variable;
    }

    public void setVariable(Variable variable) {
        this.variable = variable;
    }

    public String getName() {
        return getVariable().getName();
    }

    public String getLiteralString() {
        String ret = variable.getName() + " " + type.toString();
        if (variable.hasConstValue()) {
            ret += " " + variable.getConstValue().getLiteralString();
        }
        return ret;
    }
}

