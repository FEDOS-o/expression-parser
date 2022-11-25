package expression;

public abstract class AbstractBinaryOperation implements AbstractExpression {
    protected final AbstractExpression term1, term2;

    protected AbstractBinaryOperation(AbstractExpression term1,
                                      AbstractExpression term2) {
        this.term1 = term1;
        this.term2 = term2;
    }

    public int evaluate(int x) throws ArithmeticException {
        int res1 = term1.evaluate(x);
        int res2 = term2.evaluate(x);
        return makeOperation(res1, res2);
    }

    public int evaluate(int x, int y, int z) throws ArithmeticException {
        int res1 = term1.evaluate(x, y, z);
        int res2 = term2.evaluate(x, y, z);
        return makeOperation(res1, res2);
    }

    protected abstract int makeOperation(int res1, int res2) throws ArithmeticException;

    public abstract String getOperand();

    public String toString() {
        return "(" + term1 + " " + getOperand() + " " + term2 + ")";
    }

    public boolean equals(Object that) {
        if (this == that)
            return true;
        if (that == null)
            return false;
        if (this.getClass() == that.getClass()) {
            AbstractBinaryOperation another = (AbstractBinaryOperation) that;
            return this.term1.equals(another.term1) && this.term2.equals(another.term2);
        }
        return false;
    }

    public int hashCode() {
        return (term1.hashCode() * 31 + term2.hashCode()) * 31 + getOperand().hashCode();
    }
}
