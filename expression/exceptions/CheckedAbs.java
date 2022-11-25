package expression.exceptions;

import expression.AbstractExpression;
import expression.Abs;

public class CheckedAbs extends Abs {

    public CheckedAbs(AbstractExpression value) {
        super(value);
    }

    public int makeOperation(int res1) {
        if (res1 == Integer.MIN_VALUE)
            throw new OverflowException("abs(" + res1 + ")");
        return (res1 < 0) ? -res1 : res1;
    }
}
