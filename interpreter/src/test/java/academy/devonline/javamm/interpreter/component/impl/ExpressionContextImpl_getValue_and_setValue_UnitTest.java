package academy.devonline.javamm.interpreter.component.impl;

import academy.devonline.javamm.code.component.ExpressionContext;
import academy.devonline.javamm.code.exception.ConfigException;
import academy.devonline.javamm.code.fragment.Expression;
import academy.devonline.javamm.code.fragment.UpdatableExpression;
import academy.devonline.javamm.interpreter.component.ExpressionEvaluator;
import academy.devonline.javamm.interpreter.component.ExpressionUpdater;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static java.util.Set.of;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ExpressionContextImpl_getValue_and_setValue_UnitTest {

    @Mock
    private ExpressionEvaluator<TestExpression> expressionEvaluator;

    @Mock
    private ExpressionUpdater<TestUpdatableExpression> expressionUpdater;

    private ExpressionContext expressionContext;

    @BeforeEach
    void beforeEach() {
        when(expressionEvaluator.getExpressionClass()).thenReturn(TestExpression.class);
        when(expressionUpdater.getExpressionClass()).thenReturn(TestUpdatableExpression.class);
        expressionContext = new ExpressionContextImpl(of(expressionEvaluator), of(expressionUpdater));
    }

    @Test
    @Order(1)
    void getValue_should_delegate_the_call_to_the_appropriate_evaluator() {
        final TestExpression expression = new TestExpression();

        expressionContext.getValue(expression);

        verify(expressionEvaluator).evaluate(expression);
    }

    @Test
    @Order(2)
    void getValue_should_throw_ConfigException_if_expression_evaluator_is_not_defined_for_expression() {
        class TestExpressionEx extends TestExpression {

        }
        final TestExpression expression = new TestExpressionEx();

        final ConfigException exception = assertThrows(ConfigException.class, () ->
                expressionContext.getValue(expression));
        assertEquals("ExpressionEvaluator not defined for class " +
                "academy.devonline.javamm.interpreter.component.impl.ExpressionContextImpl_getValue_and_setValue_UnitTest" +
                "$1TestExpressionEx", exception.getMessage());
        verify(expressionEvaluator, never()).evaluate(expression);
    }

    @Test
    @Order(3)
    void setValue_should_delegate_the_call_to_the_appropriate_updater() {
        final TestUpdatableExpression expression = new TestUpdatableExpression();
        final Object value = new Object();
        expressionContext.setValue(expression, value);

        verify(expressionUpdater).update(expression, value);
    }

    @Test
    @Order(4)
    void setValue_should_throw_ConfigException_if_expression_updater_is_not_defined_for_expression() {
        class TestUpdatableExpressionEx extends TestUpdatableExpression {

        }
        final TestUpdatableExpression expression = new TestUpdatableExpressionEx();

        final ConfigException exception = assertThrows(ConfigException.class, () ->
                expressionContext.setValue(expression, null));
        assertEquals("ExpressionUpdater not defined for class " +
                "academy.devonline.javamm.interpreter.component.impl.ExpressionContextImpl_getValue_and_setValue_UnitTest" +
                "$1TestUpdatableExpressionEx", exception.getMessage());
        verify(expressionUpdater, never()).update(expression, null);
    }

    /**
     * @author devonline
     * @link http://devonline.academy/javamm
     */
    private static class TestExpression implements Expression {

    }

    /**
     * @author devonline
     * @link http://devonline.academy/javamm
     */
    private static class TestUpdatableExpression implements UpdatableExpression {

    }
}
