package org.example;

public abstract class Expression {
    
    public void print() {
        System.out.println(this.toString());
    } 
    
    public abstract Expression derivative(String var);
    
    public abstract int eval(String vars);

    @Override 
    public abstract String toString();
}