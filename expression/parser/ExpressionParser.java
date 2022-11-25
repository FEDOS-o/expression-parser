package expression.parser;


import expression.*;

public class ExpressionParser implements Parser {
    public AbstractExpression parse(final String source) {
        return parse(new StringSource(source));
    }

    public AbstractExpression parse(CharSource source) {
        return new Parser(source).parseExpression();
    }

    private static class Parser extends BaseParser {
        private Parser(CharSource source) {
            super(source);
            nextChar();
        }

        private AbstractExpression parseExpression() {
            final AbstractExpression result = parseBitOr();
            if (eof()) {
                return result;
            }
            throw error("End of Expression expected");
        }

        private AbstractExpression parseBitOr() {
            skipWhitespace();
            AbstractExpression result = parseBitXor();
            skipWhitespace();
            while (test('|')) {
                result = new BitOr(result, parseBitXor());
                skipWhitespace();
            }
            return result;
        }

        private AbstractExpression parseBitXor() {
            skipWhitespace();
            AbstractExpression result = parseBitAnd();
            skipWhitespace();
            while (test('^')) {
                result = new BitXor(result, parseBitAnd());
                skipWhitespace();
            }
            return result;
        }

        private AbstractExpression parseBitAnd() {
            skipWhitespace();
            AbstractExpression result = parseAddSub();
            skipWhitespace();
            while (test('&')) {
                result = new BitAnd(result, parseAddSub());
                skipWhitespace();
            }
            return result;
        }

        private AbstractExpression parseAddSub() {
            skipWhitespace();
            AbstractExpression result = parseMulDiv();
            skipWhitespace();
            while (ch == '+' || ch == '-') {
                if (test('+')) {
                    result = new Add(result, parseMulDiv());
                }  if (test('-')) {
                    result = new Subtract(result,  parseMulDiv());
                }
                skipWhitespace();
            }
            return result;
        }

        private AbstractExpression parseMulDiv() {
            skipWhitespace();
            AbstractExpression result = parseElement();
            skipWhitespace();
            while (ch == '*' || ch == '/') {
                if (test('*')) {
                    result = new Multiply(result, parseElement());
                } else if (test('/')) {
                    result = new Divide(result, parseElement());
                }
                skipWhitespace();
            }
            return result;
        }

        private AbstractExpression parseElement() {
            skipWhitespace();
            if (test('-')) {
                AbstractExpression elem = parseElement();
                if (elem.equals(new Variable("minvalue"))) {
                    return new Const(Integer.MIN_VALUE);
                }
                return new Multiply(new Const(-1), elem);
            }
            if (between('x', 'z')) {
                return parseVariable();
            }
            if (test('(')) {
                AbstractExpression result = parseBitOr();
                expect(')');
                return result;
            }
            return parseConst();
        }

        private void copyDigits(final StringBuilder sb) {
            while (between('0', '9')) {
                sb.append(ch);
                nextChar();
            }
        }

        private AbstractExpression parseConst() {
            final StringBuilder sb = new StringBuilder();
            skipWhitespace();
            if (test('-')) {
                sb.append('-');
                nextChar();
                skipWhitespace();
                return new Const(-1 * parseConst().evaluate(0));
            }
            if (test('0')) {
                sb.append('0');
            } else if (between('1', '9')) {
                copyDigits(sb);
            } else {
                throw error("Invalid number");
            }
            try {
                if (sb.toString().equals("2147483648")) {
                    return new Variable("minvalue");
                }
                return new Const(Integer.parseInt(sb.toString()));
            } catch (NumberFormatException e) {
                throw error("Invalid number " + sb);
            }
        }

        private AbstractExpression parseVariable() {
            if (test('x'))
                return new Variable("x");
            if (test('y'))
                return new Variable("y");
            if (test('z'))
                return new Variable("z");
            throw error("Invalid variable");
        }

        private void skipWhitespace() {
            while (test(' ') || test('\r') || test('\n') || test('\t')) {
                // skip
            }
        }
    }
}


