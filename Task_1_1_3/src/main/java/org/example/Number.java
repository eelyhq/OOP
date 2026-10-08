package org.example;

public class Number extends Expression{
    private int value;

    public void setNumber(int value) {
        this.value = value;
    }

    public int getNumber() {
        return this.value; 
    } 

    public Number(int value) {
        setNumber(value);
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