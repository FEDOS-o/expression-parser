package expression;

import java.util.Objects;

public class Variable implements AbstractExpression {
    private final String name;

    public Variable(String name) {
        this.name = name;
    }

    public int evaluate(int x) {
        if (name == "x") {
            return x;
        }
        throw new IllegalArgumentException("Dont have value for valiable " + name);
    }

    public int evaluate(int x, int y, int z) {
        if (name == "x") {
            return x;
        }
        if (name == "y") {
            return y;
        }
        if (name == "z") {
            return z;
        }
        throw new IllegalArgumentException("Dont have value for valiable " + name);

    }


    public String toString() {
        return name;
    }

    public boolean equals(Object that) {
        if (this == that)
            return true;
        if (that instanceof Variable) {
            return name.equals(that.toString());
        }
        return false;
    }

    public int hashCode() {
        return name.hashCode();
    }
}
