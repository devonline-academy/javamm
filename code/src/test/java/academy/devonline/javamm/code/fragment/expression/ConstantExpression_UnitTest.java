/*
 * Copyright (c) 2019. http://devonline.academy
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package academy.devonline.javamm.code.fragment.expression;

import academy.devonline.javamm.code.component.ExpressionContext;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.params.provider.Arguments.of;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ConstantExpression_UnitTest {

    @Mock
    private ExpressionContext expressionContext;

    @Test
    @Order(1)
    void valueOf_should_throw_IllegalArgumentException_if_value_is_null() {
        final IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
            ConstantExpression.valueOf(null));
        assertEquals("null value is not allowed. Use NullValueExpression instead", exception.getMessage());
    }

    @Test
    @Order(2)
    void valueOf_should_throw_IllegalArgumentException_if_value_has_the_unsupported_class() {
        final IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
            ConstantExpression.valueOf(Integer.class));
        assertEquals("Unsupported value type: java.lang.Class", exception.getMessage());
    }

    @ParameterizedTest
    @Order(3)
    @CsvSource( {"true", "false"})
    void valueOf_should_return_static_instances_for_boolean_types(final boolean value) {
        assertSame(ConstantExpression.valueOf(value), ConstantExpression.valueOf(value));
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 0, 2, 3, 4, 5, 6, 7, 8, 9, 10})
    @Order(4)
    void valueOf_should_return_static_instances_from_integer_pool_for_small_integer_values(final int value) {
        assertSame(ConstantExpression.valueOf(value), ConstantExpression.valueOf(value));
    }

    @ParameterizedTest
    @ValueSource(ints = {-100, -10, -5, -2, 11, 15, 50, 100, 1000})
    @Order(5)
    void valueOf_should_create_new_instances_for_other_values(final int value) {
        assertNotSame(ConstantExpression.valueOf(value), ConstantExpression.valueOf(value));
    }

    @ParameterizedTest
    @Order(6)
    @ArgumentsSource(ValidValueProvider.class)
    void valueOf_should_support_all_javamm_types(final Object value) {
        assertDoesNotThrow(() -> ConstantExpression.valueOf(value));
    }

    @Test
    @Order(7)
    void getValue_should_not_invoke_the_getValue_from_expressionContext() {
        final ConstantExpression expression = ConstantExpression.valueOf(2);

        assertEquals(2, expression.getValue(expressionContext));
        verify(expressionContext, never()).getValue(expression);
    }

    @ParameterizedTest
    @Order(8)
    @ArgumentsSource(ToStringProvider.class)
    void toString_should_return_the_string_representation_of_value(final Object value, final String expectedResult) {
        assertEquals(expectedResult, ConstantExpression.valueOf(value).toString());
    }

    /**
     * @author devonline
     * @link http://devonline.academy/javamm
     */
    private static class ValidValueProvider implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(final ExtensionContext context) {
            return Stream.of(
                // boolean
                of(true),
                // integer
                of(2),
                // double
                of(2.2),
                // string
                of("text")
            );
        }
    }

    /**
     * @author devonline
     * @link http://devonline.academy/javamm
     */
    private static class ToStringProvider implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(final ExtensionContext context) {
            return Stream.of(
                of(true, "true"),
                of(false, "false"),
                of(2, "2"),
                of(2.2, "2.2"),
                of("text", "text")
            );
        }
    }
}