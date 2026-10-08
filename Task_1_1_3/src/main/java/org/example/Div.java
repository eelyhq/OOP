package org.example;

public class Div extends BinaryOperator {
    public Div(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public Expression derivative(String var) {
        return new Div(new Sub(new Mul(this.left.derivative(var), this.right), new Mul(this.left, this.right.derivative(var))), new Mul(this.right, this.right));
    }

    @Override
    public int eval(String vars) {
        return this.left.eval(vars) / this.right.eval(vars);
    }

    @Override
    public String toString() {
        return "(" + this.left + "/" + this.right + ")";
    }
}