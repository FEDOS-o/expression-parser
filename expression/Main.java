package expression;


import expression.exceptions.ExpressionParser;
import expression.exceptions.Parser;

public class Main {
    public static void main(String[] args) {
        Parser parser = new ExpressionParser();
        TripleExpression res = parser.parse("sqrt 4");
        System.out.println(res.evaluate(0, 0, 0));
    }
}
