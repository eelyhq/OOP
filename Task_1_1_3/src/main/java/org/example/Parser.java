package org.example;

/**
 * Parser for mathematical expressions.
 */
public class Parser {
    /**
     * Parses an expression from a string.
     */
    public static Expression parse(String s) {
        s = s.replaceAll("\\s+", "");

        if (!s.startsWith("(")) {
            if (Character.isDigit(s.charAt(0))) {
                return new Number(Integer.parseInt(s));
            } else { 
               return new Variable(s);
            }
        }
        int balance = 0;
        int opIndex = -1; 
        
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
        
            if (c == '(') {
                balance++;
            } else if (c == ')') {
                balance--;
            } else if ((c == '+' || c == '-' || c == '*' || c == '/') && balance == 1) {
                opIndex = i;
                break; 
            }
        }
        if (opIndex == -1) {
            throw new IllegalArgumentException("No valid operator found in expression: " + s);
        }
        
        char operator = s.charAt(opIndex);
        
        String leftStr = s.substring(1, opIndex); 

        String rightStr = s.substring(opIndex + 1, s.length() - 1);
        
        Expression left = parse(leftStr);
        Expression right = parse(rightStr);

        return switch(operator) {
            case '+' -> new Add(left, right);
            case '-' -> new Sub(left, right);
            case '*' -> new Mul(left, right);
            case '/' -> new Div(left, right);
            default -> throw new IllegalArgumentException("Unknown operator: " + operator);
        };
    }   
}