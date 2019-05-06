package academy.devonline.javamm.interpreter.component.impl.calculator.arithmetic;

import academy.devonline.javamm.code.component.ExpressionContext;
import academy.devonline.javamm.code.fragment.Expression;
import academy.devonline.javamm.code.fragment.operator.BinaryOperator;
import academy.devonline.javamm.interpreter.component.BinaryExpressionCalculator;
import academy.devonline.javamm.interpreter.component.impl.calculator.AbstractBinaryExpressionCalculator;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
public final class SubtractionBinaryExpressionCalculator extends AbstractBinaryExpressionCalculator
    implements BinaryExpressionCalculator {

    private SubtractionBinaryExpressionCalculator(final BinaryOperator operator) {
        super(operator);
    }

    public static SubtractionBinaryExpressionCalculator createArithmeticCalculator() {
        return new SubtractionBinaryExpressionCalculator(BinaryOperator.ARITHMETIC_SUBTRACTION);
    }

    public static SubtractionBinaryExpressionCalculator createAssignmentCalculator() {
        return new SubtractionBinaryExpressionCalculator(BinaryOperator.ASSIGNMENT_SUBTRACTION);
    }

    @Override
    public Object calculate(final ExpressionContext expressionContext,
                            final Expression expression1,
                            final Expression expression2) {
        final Object value1 = expression1.getValue(expressionContext);
        final Object value2 = expression2.getValue(expressionContext);
        if (value1 instanceof Number && value2 instanceof Number) {
            if (value1 instanceof Integer && value2 instanceof Integer) {
                return (Integer) value1 - (Integer) value2;
            } else {
                return ((Number) value1).doubleValue() - ((Number) value2).doubleValue();
            }
        } else {
            throw createNotSupportedTypesError(value1, value2);
        }
    }
}
