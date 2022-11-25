package expression.exceptions;

import expression.AbstractExpression;
import expression.Sqrt;

public class CheckedSqrt extends Sqrt {

    public CheckedSqrt(AbstractExpression value) {
        super(value);
    }

    public int makeOperation(int res1) {
        if (res1 < 0)
            throw new IllegalSqrtArgumentException("sqrt(" + res1 + ")");
        return super.makeOperation(res1);
    }
}
