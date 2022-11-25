package expression.exceptions;

import expression.Divide;
import expression.AbstractExpression;

public class CheckedDivide extends Divide {
    public CheckedDivide(AbstractExpression term1, AbstractExpression term2) {
        super(term1, term2);
    }

    public int makeOperation(int res1, int res2) throws ArithmeticException {
        if (res2 == 0)
            throw new DivisionByZeroException(res1 + " / " + res2);
        if (res1 == Integer.MIN_VALUE && res2 == -1)
            throw new OverflowException(res1 + " / " + res2);
        return res1 / res2;
    }
}
