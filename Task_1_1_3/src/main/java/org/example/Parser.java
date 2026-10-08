package org.example;

public class Parser {
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
        char operator = s.charAt(opIndex);

        String leftStr = s.substring(1, opIndex); 

        String rightStr = s.substring(opIndex + 1, s.length() - 1);
        
        Expression left = parse(leftStr);
        Expression right = parse(rightStr);

        switch(operator) {
            case '+':
                return new Add(left, right);
            case '-':
                return new Sub(left, right);
            case '*':
                return new Mul(left, right);
            case '/':
                return new Div(left, right);
            default:
                 throw new IllegalArgumentException("Unknown operator: " + operator);
        }
    }   
}