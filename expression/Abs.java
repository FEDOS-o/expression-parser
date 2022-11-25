package expression;

public class Abs extends AbstractUnaryOperation {

    public Abs(AbstractExpression value) {
        super(value);
    }

    public int makeOperation(int res1) {
        return (res1 < 0) ? -res1 : res1;
    }

    public String toString() {
        return "abs" + value;
    }

    public boolean equals(Object that) {
        if (this == that) return true;
        if (that instanceof Abs) {
            return ((Abs) that).value.equals(value);
        }
        return false;
    }
}
