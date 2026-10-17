package org.example;

/**
 * Numeric constant expression.
 */
public class Number extends Expression {
    private final int value;

    public Number(int value) {
        this.value = value;
    }

    public int getNumber() {
        return this.value; 
    }

    @Override
    public Expression derivative(String var) {
        return new Number(0);
    }

    @Override 
    public int eval(String vars) {
        return getNumber();
    }

    @Override 
    public String toString() {
        return Integer.toString(getNumber());
    }
}