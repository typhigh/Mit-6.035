package edu.mit.compilers.lowercode;


public class LocationVariable extends Operand {
    private Variable variable;
    private Operand location;


    public LocationVariable(Variable variable, Operand location) {
        this.variable = variable;
        this.location = location;
    }

    public Variable getVariable() {
        return variable;
    }

    public Operand getLocation() {
        return location;
    }

    public boolean isArray() {
        return location != null;
    }

    @Override
    public String getLiteralString() {
        String ret = variable.getLiteralString();
        if (isArray()) {
            ret += "[" + location.getLiteralString() + "]";
        }
        return ret;
    }
}
