package expression;

import java.util.Objects;

public class Const implements AbstractExpression {
    private final int value;

    public Const(int value) {
        this.value = value;
    }

    public int evaluate(int x) {
        return value;
    }

    public int evaluate(int x, int y, int z) { return  value;}

    public String toString() {
        return Integer.toString(value);
    }

    public boolean equals(Object that) {
        if (this == that) return true;
        if (that instanceof Const) {
            return value == ((Const) that).evaluate(0);
        }
        return false;
    }

    public int hashCode() {
        return Integer.hashCode(value);
    }
}
