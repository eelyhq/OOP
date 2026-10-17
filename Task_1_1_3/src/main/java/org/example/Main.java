package org.example;

/** Entry point class. */
public class Main {
    public static void main(String[] args) {
        Expression e = new Add(new Number(3), new Mul(new Number(2),
        new Variable("x"))); // (3+(2*x))
        int result = e.eval("x = 10; y = 13");
        System.out.println(result);
    }
}
