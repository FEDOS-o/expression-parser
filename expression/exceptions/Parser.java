package expression.exceptions;

import expression.TripleExpression;
import expression.parser.ParseException;


public interface Parser {
    TripleExpression parse(String expression) throws ParseException;
}