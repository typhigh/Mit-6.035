package edu.mit.compilers.lowercode;

// just left part
public abstract class AssignCode extends ThreeAddressCode {

    private LocationVariable left;

    public AssignCode(Variable variable, Operand location) {
        this.left = new LocationVariable(variable, location);
    }

    public AssignCode(Variable variable) {
        this(variable, null);
    }

    public LocationVariable getLeft() {
        return left;
    }

    public boolean isArray() {
        return left.isArray();
    }

    public String getLeftString() {
        return left.getLiteralString();
    }
}
