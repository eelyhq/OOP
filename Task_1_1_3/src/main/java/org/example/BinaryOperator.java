package org.example;

/** Abstract binary operator with left and right operands. */
public abstract class BinaryOperator extends Expression {
    protected Expression left;
    protected Expression right;

    public BinaryOperator(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }
}