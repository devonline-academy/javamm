package academy.devonline.javamm.code.fragment.expression;

import academy.devonline.javamm.code.component.ExpressionContext;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class NullValueExpression_UnitTest {

    @Mock
    private ExpressionContext expressionContext;

    @Test
    @Order(1)
    void getInstance_should_return_singleton_instance() {
        assertSame(NullValueExpression.getInstance(), NullValueExpression.getInstance());
    }

    @Test
    @Order(2)
    void getValue_should_return_null_without_interaction_with_expressionContext() {
        final NullValueExpression nullValueExpression = NullValueExpression.getInstance();

        assertNull(nullValueExpression.getValue(expressionContext));
        verify(expressionContext, never()).getValue(any());
    }

    @Test
    @Order(3)
    void toString_should_return_null_string() {
        assertEquals("null", NullValueExpression.getInstance().toString());
    }
}