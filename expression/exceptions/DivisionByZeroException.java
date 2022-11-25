package expression.exceptions;

public class DivisionByZeroException extends ArithmeticException {
    DivisionByZeroException(String message) {
        super(message);
    }
}
