package academy.devonline.javamm.interpreter.component.impl;

import academy.devonline.javamm.code.exception.ConfigException;
import academy.devonline.javamm.code.fragment.Expression;
import academy.devonline.javamm.code.fragment.UpdatableExpression;
import academy.devonline.javamm.interpreter.component.ExpressionEvaluator;
import academy.devonline.javamm.interpreter.component.ExpressionUpdater;
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
import static org.mockito.Mockito.when;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ExpressionContextImpl_Constructor_UnitTest {

    @Mock
    private ExpressionEvaluator<Expression> expressionEvaluator1;

    @Mock
    private ExpressionEvaluator<Expression> expressionEvaluator2;

    @Mock
    private ExpressionUpdater<UpdatableExpression> expressionUpdater1;

    @Mock
    private ExpressionUpdater<UpdatableExpression> expressionUpdater2;

    @Test
    @Order(1)
    void Should_throw_ConfigException_if_duplicate_of_expression_evaluator_is_found() {
        when(expressionEvaluator1.getExpressionClass()).thenReturn(Expression.class);
        when(expressionEvaluator1.toString()).thenReturn("expressionEvaluator");
        when(expressionEvaluator2.getExpressionClass()).thenReturn(Expression.class);
        when(expressionEvaluator2.toString()).thenReturn("expressionEvaluator");

        final ConfigException exception = assertThrows(ConfigException.class, () ->
                new ExpressionContextImpl(of(expressionEvaluator1, expressionEvaluator2), of()));
        assertEquals("Duplicate of ExpressionEvaluator found: " +
                "expression=academy.devonline.javamm.code.fragment.Expression, " +
                "evaluator1=expressionEvaluator, evaluator2=expressionEvaluator", exception.getMessage());
    }

    @Test
    @Order(2)
    void Should_throw_ConfigException_if_duplicate_of_expression_updater_is_found() {
        when(expressionUpdater1.getExpressionClass()).thenReturn(UpdatableExpression.class);
        when(expressionUpdater1.toString()).thenReturn("expressionUpdater");
        when(expressionUpdater2.getExpressionClass()).thenReturn(UpdatableExpression.class);
        when(expressionUpdater2.toString()).thenReturn("expressionUpdater");

        final ConfigException exception = assertThrows(ConfigException.class, () ->
                new ExpressionContextImpl(of(), of(expressionUpdater1, expressionUpdater2)));
        assertEquals("Duplicate of ExpressionUpdater found: " +
                "expression=academy.devonline.javamm.code.fragment.UpdatableExpression, " +
                "updater1=expressionUpdater, updater2=expressionUpdater", exception.getMessage());
    }
}