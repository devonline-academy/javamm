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
import academy.devonline.javamm.code.syntax.Keywords;
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
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.params.provider.Arguments.arguments;
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
class TypeExpression_UnitTest {

    @Mock
    private ExpressionContext expressionContext;

    @ParameterizedTest
    @Order(1)
    @ValueSource(strings = {
        Keywords.INTEGER,
        Keywords.DOUBLE,
        Keywords.BOOLEAN,
        Keywords.STRING,
        // TODO Add ARRAY
    })
    void is_should_return_true(final String keyword) {
        assertTrue(TypeExpression.is(keyword));
    }

    @ParameterizedTest
    @Order(2)
    @ValueSource(strings = {
        Keywords.INTEGER,
        Keywords.DOUBLE,
        Keywords.BOOLEAN,
        Keywords.STRING,
        // TODO Add ARRAY
    })
    void of_should_support_all_type(final String keyword) {
        assertDoesNotThrow(() -> TypeExpression.of(keyword));
    }

    @Test
    @Order(3)
    void of_should_throw_IllegalArgumentException_for_unsupported_javamm_types() {
        final IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> TypeExpression.of("long"));
        assertEquals("Unsupported type: long", exception.getMessage());
    }

    @ParameterizedTest
    @Order(4)
    @EnumSource(TypeExpression.class)
    void getValue_should_return_lower_cased_type_name_without_interaction_with_expressionContext(
        final TypeExpression typeExpression) {
        assertEquals(typeExpression, typeExpression.getValue(expressionContext));
        verify(expressionContext, never()).getValue(any());
    }

    @ParameterizedTest
    @Order(5)
    @EnumSource(TypeExpression.class)
    void toString_should_return_lower_cased_type_name(final TypeExpression typeExpression) {
        assertEquals(typeExpression.getKeyword(), typeExpression.toString());
    }

    @ParameterizedTest
    @Order(6)
    @ArgumentsSource(TypesProvider.class)
    void getType_should_return_valid_Class_instance(final TypeExpression typeExpression,
                                                    final Class<?> expectedType) {
        assertEquals(expectedType, typeExpression.getType());
    }

    /**
     * @author devonline
     * @link http://devonline.academy/javamm
     */
    private static class TypesProvider implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(final ExtensionContext context) {
            return Stream.of(
                arguments(TypeExpression.INTEGER, Integer.class),
                arguments(TypeExpression.DOUBLE, Double.class),
                arguments(TypeExpression.STRING, String.class),
                arguments(TypeExpression.BOOLEAN, Boolean.class)
            );
        }
    }
}