package org.example;

/**
 * Multiplication expression.
 */
public class Mul extends BinaryOperator {
    public Mul(Expression left, Expression right) {
        super(left, right);
    }
    
    @Override
    public Expression derivative(String var) {
        return new Add(new Mul(this.left.derivative(var), this.right),
            new Mul(this.left, this.right.derivative(var)));
    }
    
    @Override
    public int eval(String vars) {
        return this.left.eval(vars) * this.right.eval(vars);
    }
    
    @Override
    public String toString() {
        return "(" + this.left + "*" + this.right + ")";
    }
}