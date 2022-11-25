package expression;

public class BitXor extends AbstractBinaryOperation {
    private final static String sign = "^";

    public BitXor(AbstractExpression term1, AbstractExpression term2) {
        super(term1, term2);
    }

    public int makeOperation(int res1, int res2) {
        return res1 ^ res2;
    }

    public String getOperand() {
        return sign;
    }
}
