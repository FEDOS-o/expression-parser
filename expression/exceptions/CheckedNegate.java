package expression.exceptions;

import expression.AbstractExpression;
import expression.Negate;

public class CheckedNegate extends Negate {
    public CheckedNegate(AbstractExpression value) {
        super(value);
    }

    public int makeOperation(int res1) {
        if (res1 == Integer.MIN_VALUE) {
            throw new OverflowException("-" + Integer.MIN_VALUE);
        }
        return -res1;
    }
}
