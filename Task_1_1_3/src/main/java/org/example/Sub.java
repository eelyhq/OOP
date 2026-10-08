package org.example;

/** Subtraction operation. */
public class Sub extends BinaryOperator {
    public Sub(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public Expression derivative(String var) {
        return new Sub(this.left.derivative(var), this.right.derivative(var));
    }

    @Override
    public int eval(String vars) {
        return this.left.eval(vars) - this.right.eval(vars);
    }

    @Override
    public String toString() {
        return "(" + this.left + "-" + this.right + ")";
    }
}