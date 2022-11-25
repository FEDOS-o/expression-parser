package expression.exceptions;


import expression.*;
import expression.parser.*;

public class ExpressionParser implements Parser {
    public AbstractExpression parse(final String source) throws ParseException {
        return parse(new StringSource(source));
    }

    public AbstractExpression parse(CharSource source) throws ParseException{
        return new Parser(source).parseExpression();
    }

    private static class Parser extends BaseParser {
        private Parser(CharSource source) {
            super(source);
            nextChar();
        }

        private AbstractExpression parseExpression() throws ParseException {
            final AbstractExpression result = parseBitOr();
            if (eof()) {
                return result;
            }
            if (ch == ')')
                throw error("Expected '('");
            throw error("End of Expression expected");
        }

        private AbstractExpression parseBitOr() throws ParseException {
            skipWhitespace();
            AbstractExpression result = parseBitXor();
            skipWhitespace();
            while (test('|')) {
                result = new BitOr(result, parseBitXor());
                skipWhitespace();
            }
            return result;
        }

        private AbstractExpression parseBitXor() throws ParseException {
            skipWhitespace();
            AbstractExpression result = parseBitAnd();
            skipWhitespace();
            while (test('^')) {
                result = new BitXor(result, parseBitAnd());
                skipWhitespace();
            }
            return result;
        }

        private AbstractExpression parseBitAnd() throws ParseException {
            skipWhitespace();
            AbstractExpression result = parseAddSub();
            skipWhitespace();
            while (test('&')) {
                result = new BitAnd(result, parseAddSub());
                skipWhitespace();
            }
            return result;
        }

        private AbstractExpression parseAddSub() throws ParseException {
            skipWhitespace();
            AbstractExpression result = parseMulDiv();
            skipWhitespace();
            while (ch == '+' || ch == '-') {
                if (test('+')) {
                    result = new CheckedAdd(result, parseMulDiv());
                }  if (test('-')) {
                    result = new CheckedSubtract(result,  parseMulDiv());
                }
                skipWhitespace();
            }
            return result;
        }

        private AbstractExpression parseMulDiv() throws ParseException {
            skipWhitespace();
            AbstractExpression result = parseElement();
            if (result.equals(new Variable("minvalue")))
                throw error("Const overflow");
            skipWhitespace();
            while (ch == '*' || ch == '/') {
                if (test('*')) {
                    AbstractExpression sec = parseElement();
                    if (sec.equals(new Variable("minvalue"))) {
                        throw error("Overflow const");
                    }
                    result = new CheckedMultiply(result, sec);
                } else if (test('/')) {
                    AbstractExpression sec = parseElement();
                    if (sec.equals(new Variable("minvalue"))) {
                        throw error("Overflow const");
                    }
                    result = new CheckedDivide(result, sec);
                }
                skipWhitespace();
            }
            return result;
        }

        private AbstractExpression parseElement() throws ParseException {
            skipWhitespace();
            if (test('-')) {
                skipWhitespace();
                AbstractExpression res = parseElement();
                if (res.equals(new Variable("minvalue"))) {
                    return new Const(Integer.MIN_VALUE);
                } else if (res.equals(new Const(Integer.MIN_VALUE)))
                    throw  error("Const overflow  - " + Integer.MIN_VALUE);
                if (res instanceof Const)
                    return new Const(-res.evaluate(0));
                return new CheckedNegate(res);
            }
            if (ch == 'a') {
                return parseAbs();
            }
            if (ch == 's') {
                return parseSqrt();
            }
            if (between('x', 'z')) {
                return parseVariable();
            }
            if (test('(')) {
                AbstractExpression result = parseBitOr();
                expect(')');
                return result;
            }
            if (between('0', '9')) {
                return parseConst();
            }
            throw error("Element expected");
        }

        private AbstractExpression parseAbs() {
            expect("abs");
            if (ch == ' ' || ch =='-' || ch == '(') {
                skipWhitespace();
                AbstractExpression result = parseElement();
                return new CheckedAbs(result);
            }
            throw error("Invalid format abs");
        }

        private AbstractExpression parseSqrt() {
            expect("sqrt");
            if (ch == ' ' || ch =='-' || ch == '(') {
                skipWhitespace();
                AbstractExpression result = parseElement();
                return new CheckedSqrt(result);
            }
            throw error("Invalid format sqrt");
        }

        private void copyDigits(final StringBuilder sb) {
            while (between('0', '9')) {
                sb.append(ch);
                nextChar();
            }
        }

        private AbstractExpression parseConst() throws ParseException {
            final StringBuilder sb = new StringBuilder();
            skipWhitespace();
            /*
            if (test('-')) {
                AbstractExpression res = parseConst();
                if (res.equals(new Variable("minvalue"))) {
                    return new Const(Integer.MIN_VALUE);
                } else if (res.equals(new Const(Integer.MIN_VALUE)))
                    throw  error("Const overflow  - " + Integer.MIN_VALUE);
                return new CheckedNegate(res);
            }
             */
            if (test('0')) {
                sb.append('0');
            } else if (between('1', '9')) {
                copyDigits(sb);
            } else {
                throw error("Invalid number " + ch);
            }
            if (sb.toString().equals("2147483648")) {
                return new Variable("minvalue");
            }
            try {
                return new Const(Integer.parseInt(sb.toString()));
            } catch (NumberFormatException e) {
                throw error("Overflow const " + sb);
            }
        }

        private AbstractExpression parseVariable() throws ParseException {
            if (test('x'))
                return new Variable("x");
            if (test('y'))
                return new Variable("y");
            if (test('z'))
                return new Variable("z");
            throw error("Invalid variable " + ch);
        }

        private void skipWhitespace() {
            while (test(' ') || test('\r') || test('\n') || test('\t')) {
                // skip
            }
        }
    }
}


