package expression.exceptions;

import expression.Multiply;
import expression.AbstractExpression;

public class CheckedMultiply extends Multiply {
    public CheckedMultiply(AbstractExpression term1, AbstractExpression term2) {
        super(term1, term2);
    }

    public int makeOperation(int res1, int res2) throws ArithmeticException {
        int result = res1 * res2;
        int ares1 = (res1 < 0) ? -res1 : res1;
        int ares2 = (res2 < 0) ? -res2 : res2;
        if (((ares1 | ares2) >>> 15 != 0)) {
            if (((res2 != 0) && (result / res2 != res1)) ||
                    (res1 == Integer.MIN_VALUE && res2 == -1)) {
                throw new OverflowException(res1 + " * " + res2);
            }
        }
        return result;
    }
}
