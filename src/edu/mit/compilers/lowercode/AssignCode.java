package edu.mit.compilers.lowercode;

// just left part
public abstract class AssignCode extends ThreeAddressCode {

    // left [location], location must be variable, neither be method nor literal value
    private String left;
    private String location;
    private boolean isArray;

    public AssignCode(String left, String location) {
        this.left = left;
        this.location = location;
        this.isArray = location != null;
    }

    public AssignCode(String left) {
        this(left, null);
    }

    public String getLeft() {
        return left;
    }

    public boolean isArray() {
        return isArray;
    }

    public String getLocation() {
        return location;
    }

    public String getName() {
        return left + (isArray ? "[" + location + "]" : "");
    }
}
