package org.example;

/** Variable expression. */
public class Variable extends Expression {
    private String name;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public Variable(String name) {
        setName(name);
    }

    @Override
    public Expression derivative(String var) {
        if (this.name.equals(var)) {
            return new Number(1);
        } else {
            return new Number(0);
        }
    }

    @Override
    public int eval(String vars) {
        String[] tokens = vars.split(";");
        for (String token : tokens) {
            String[] varValuePair = token.split("=");
            String varName = varValuePair[0].trim();
            if (varName.equals(this.name)) {
                String varValue = varValuePair[1].trim();
                return Integer.parseInt(varValue);
            }
        }
        throw new IllegalArgumentException("Value of variable " + this.name + " unfound");
    }

    @Override
    public String toString() {
        return getName();
    }
}