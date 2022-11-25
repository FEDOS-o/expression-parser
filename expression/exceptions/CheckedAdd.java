package expression.exceptions;

import expression.Add;
import expression.AbstractExpression;

public class CheckedAdd extends Add {
    public CheckedAdd(AbstractExpression term1, AbstractExpression term2) {
        super(term1, term2);
    }

    public int makeOperation(int res1, int res2) throws ArithmeticException {
        int result = res1 + res2;
        if (((res1 ^ result) & (res2 ^ result)) < 0) {
            throw new OverflowException(res1 + " + " + res2);
        }
        return result;
    }
}
