package org.example;

public abstract class BinaryOperator extends Expression {
    protected Expression left;
    protected Expression right;

    public BinaryOperator(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }
}