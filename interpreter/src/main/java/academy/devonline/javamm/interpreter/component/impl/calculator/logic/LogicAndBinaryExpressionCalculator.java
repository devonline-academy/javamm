package academy.devonline.javamm.interpreter.component.impl.calculator.logic;

import academy.devonline.javamm.code.component.ExpressionContext;
import academy.devonline.javamm.code.fragment.Expression;
import academy.devonline.javamm.code.fragment.operator.BinaryOperator;
import academy.devonline.javamm.interpreter.component.BinaryExpressionCalculator;
import academy.devonline.javamm.interpreter.component.impl.calculator.AbstractBinaryExpressionCalculator;
import academy.devonline.javamm.interpreter.component.impl.error.JavammLineRuntimeError;

import static academy.devonline.javamm.code.util.TypeUtils.getType;
import static java.lang.String.format;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
public final class LogicAndBinaryExpressionCalculator extends AbstractBinaryExpressionCalculator
        implements BinaryExpressionCalculator {

    public LogicAndBinaryExpressionCalculator() {
        super(BinaryOperator.LOGIC_AND);
    }

    @Override
    public Object calculate(final ExpressionContext expressionContext,
                            final Expression expression1,
                            final Expression expression2) {
        final Object value1 = expression1.getValue(expressionContext);
        if (value1 instanceof Boolean) {
            if ((Boolean) value1) {
                final Object value2 = expression2.getValue(expressionContext);
                if (value2 instanceof Boolean) {
                    return value2;
                } else {
                    throw createNotSupportedTypesError(true, value2);
                }
            } else {
                return false;
            }
        } else {
            throw new JavammLineRuntimeError(format(
                    "First operand for operator '%s' has invalid type: %s",
                    getOperator().getCode(),
                    getType(value1)));
        }
    }
}
