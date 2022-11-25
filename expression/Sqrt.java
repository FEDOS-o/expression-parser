package expression;

public class Sqrt extends AbstractUnaryOperation {

    public Sqrt(AbstractExpression value) {
        super(value);
    }

    public int makeOperation(int res1) {
        int L = 0;
        int R = 46341;
        while (R - L > 1) {
            int M = (L + R) / 2;
            if (M * M <= res1) {
                L = M;
            } else {
                R = M;
            }
        }
        return L;
    }

    public String toString() {
        return "sqrt(" + value + ")";
    }

    public boolean equals(Object that) {
        if (this == that) return true;
        if (that instanceof Sqrt) {
            return ((Sqrt) that).value.equals(value);
        }
        return false;
    }
}
