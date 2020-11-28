package edu.mit.compilers.utils;

public class Literal<T> {
    private String literalValue;

    // if in IRLiteral, filled by LiteralValue rule
    private T value;

    public Literal(String literalValue) {
        this.literalValue = literalValue;
    }

    public Literal(T value) {
        this.value = value;
        this.literalValue = (String) value;
    }

    public String getLiteralValue() {
        return literalValue;
    }

    public void setLiteralValue(String literalValue) {
        this.literalValue = literalValue;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
