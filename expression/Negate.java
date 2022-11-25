package expression;

import expression.exceptions.CheckedNegate;

public class Negate extends AbstractUnaryOperation {

    public Negate(AbstractExpression value) {
        super(value);
    }

    public int makeOperation(int res1) {
        return -res1;
    }

    public String toString() {
        return "-" + value;
    }


    public boolean equals(Object that) {
        if (this == that) return true;
        if (that instanceof Negate) {
            return ((Negate) that).value.equals(value);
        }
        return false;
    }
}
