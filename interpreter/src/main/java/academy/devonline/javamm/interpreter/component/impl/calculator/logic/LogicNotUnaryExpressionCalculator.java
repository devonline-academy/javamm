package academy.devonline.javamm.interpreter.component.impl.calculator.logic;

import academy.devonline.javamm.code.component.ExpressionContext;
import academy.devonline.javamm.code.fragment.Expression;
import academy.devonline.javamm.code.fragment.operator.UnaryOperator;
import academy.devonline.javamm.interpreter.component.UnaryExpressionCalculator;
import academy.devonline.javamm.interpreter.component.impl.calculator.AbstractUnaryExpressionCalculator;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
public final class LogicNotUnaryExpressionCalculator extends AbstractUnaryExpressionCalculator
    implements UnaryExpressionCalculator {

    public LogicNotUnaryExpressionCalculator() {
        super(UnaryOperator.LOGIC_NOT);
    }

    @Override
    public Object calculate(final ExpressionContext expressionContext,
                            final Expression expression) {
        final Object value = expression.getValue(expressionContext);
        if (value instanceof Boolean) {
            return !(Boolean) value;
        } else {
            throw createNotSupportedTypesError(value);
        }
    }
}
