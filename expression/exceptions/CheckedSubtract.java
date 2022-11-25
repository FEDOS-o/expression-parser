package expression.exceptions;

import expression.Add;

import expression.AbstractExpression;
import expression.Subtract;

public class CheckedSubtract extends Subtract {
    public CheckedSubtract(AbstractExpression term1, AbstractExpression term2) {
        super(term1, term2);
    }

    public int makeOperation(int res1, int res2) throws ArithmeticException {
        int result = res1 - res2;
        if (((res1 ^ res2) & (res1 ^ result)) < 0) {
            throw new OverflowException(res1 + " - " + res2);
        }
        return result;
    }
}
