package expression;

public abstract class AbstractUnaryOperation implements AbstractExpression {
    protected final AbstractExpression value;

    protected AbstractUnaryOperation(AbstractExpression value) {
        this.value = value;
    }

    public int evaluate(int x) {
        int res1 = value.evaluate(x);
        return makeOperation(res1);
    }

    public int evaluate(int x, int y, int z) {
        int res1 = value.evaluate(x, y, z);
        return makeOperation(res1);
    }

    protected abstract int makeOperation(int res1) throws ArithmeticException;

    public boolean equals(Object that) {
        if (this == that)
            return true;
        if (that == null)
            return false;
        if (this.getClass() == that.getClass()) {
            return this.value.equals(((AbstractUnaryOperation) that).value);
        }
        return false;
    }
}
