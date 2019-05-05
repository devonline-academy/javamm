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

package academy.devonline.javamm.compiler.component.impl;

import academy.devonline.javamm.code.component.ExpressionContext;
import academy.devonline.javamm.code.fragment.Expression;
import academy.devonline.javamm.code.fragment.SourceLine;
import academy.devonline.javamm.code.fragment.expression.ConstantExpression;
import academy.devonline.javamm.code.fragment.expression.NullValueExpression;
import academy.devonline.javamm.code.fragment.expression.TypeExpression;
import academy.devonline.javamm.compiler.component.SingleTokenExpressionBuilder;
import academy.devonline.javamm.compiler.component.impl.error.JavammLineSyntaxError;
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

import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

import static academy.devonline.javamm.code.syntax.Keywords.FALSE;
import static academy.devonline.javamm.code.syntax.Keywords.NULL;
import static academy.devonline.javamm.code.syntax.Keywords.TRUE;
import static java.lang.String.format;
import static java.util.List.of;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SingleTokenExpressionBuilderImpl_UnitTest {

    private final SourceLine sourceLine = new SourceLine("module1", 5, List.of());

    private final SingleTokenExpressionBuilder expressionBuilder = new SingleTokenExpressionBuilderImpl();

    @Mock
    private ExpressionContext expressionContext;

    @ParameterizedTest
    @ArgumentsSource(ValidTokenProvider.class)
    @Order(1)
    void canBuild_should_return_true(final String token) {
        assertTrue(expressionBuilder.canBuild(of(token)));
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "var a",
        "#",
        "{",
        "переменная"
    })
    @Order(2)
    void canBuild_should_return_false(final String tokens) {
        assertFalse(expressionBuilder.canBuild(of(tokens.split(" "))));
    }

    @ParameterizedTest
    @Order(3)
    @EnumSource(TypeExpression.class)
    void build_should_build_a_type_expression(final TypeExpression typeExpression) {
        final Expression expression = expressionBuilder.build(of(typeExpression.getKeyword()), sourceLine);

        assertSame(typeExpression, expression);
    }

    @ParameterizedTest
    @Order(4)
    @ValueSource(strings = {
        "'Hello world'",
        "\"Hello world\"",
        "'Привет мир'",
        "\"Привет мир\"",
    })
    void build_should_build_a_string_constant_expression(final String value) {
        final String expectedResult = value.replace("'", "").replace("\"", "");
        final Expression expression = expressionBuilder.build(of(value), sourceLine);

        assertEquals(ConstantExpression.class, expression.getClass());
        assertEquals(expectedResult, expression.getValue(expressionContext));
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "'Hello world",
        "\"Hello world",
        "'",
        "\""
    })
    @Order(5)
    void build_should_throw_error_if_the_closing_quotation_mark_is_missing(final String stringToken) {
        final JavammLineSyntaxError error = assertThrows(JavammLineSyntaxError.class, () ->
            expressionBuilder.build(of(stringToken), sourceLine));
        assertEquals(
            format("Syntax error in 'module1' [Line: 5]: %s expected at the end of the string token", stringToken.charAt(0)),
            error.getMessage());
    }

    @Test
    @Order(6)
    void build_should_throw_error_if_string_constant_expression_starts_with_double_quotation_mark_but_ends_with_single_quotation_mark() {
        final JavammLineSyntaxError error = assertThrows(JavammLineSyntaxError.class, () ->
            expressionBuilder.build(of("\"Hello world'"), sourceLine));
        assertEquals(
            "Syntax error in 'module1' [Line: 5]: \" expected at the end of the string token instead of '",
            error.getMessage());
    }

    @Test
    @Order(7)
    void build_should_throw_error_if_string_constant_expression_starts_with_single_quotation_mark_but_ends_with_double_quotation_mark() {
        final JavammLineSyntaxError error = assertThrows(JavammLineSyntaxError.class, () ->
            expressionBuilder.build(of("'Hello world\""), sourceLine));
        assertEquals(
            "Syntax error in 'module1' [Line: 5]: ' expected at the end of the string token instead of \"",
            error.getMessage());
    }

    @Test
    @Order(8)
    void build_should_build_a_null_value_expression() {
        final Expression expression = expressionBuilder.build(of(NULL), sourceLine);

        assertEquals(NullValueExpression.class, expression.getClass());
        assertNull(expression.getValue(expressionContext));
    }

    @Test
    @Order(9)
    void build_should_build_a_true_constant_expression() {
        final Expression expression = expressionBuilder.build(of(TRUE), sourceLine);

        assertEquals(ConstantExpression.class, expression.getClass());
        assertTrue((boolean) expression.getValue(expressionContext));
    }

    @Test
    @Order(10)
    void build_should_build_a_false_constant_expression() {
        final Expression expression = expressionBuilder.build(of(FALSE), sourceLine);

        assertEquals(ConstantExpression.class, expression.getClass());
        assertFalse((boolean) expression.getValue(expressionContext));
    }

    @Test
    @Order(11)
    void Should_build_an_integer_constant_expression() {
        final Expression expression = expressionBuilder.build(of("5"), sourceLine);

        assertEquals(ConstantExpression.class, expression.getClass());
        assertEquals(5, expression.getValue(expressionContext));
    }

    @Test
    @Order(12)
    void build_should_build_a_double_constant_expression() {
        final Expression expression = expressionBuilder.build(of("5.1"), sourceLine);

        assertEquals(ConstantExpression.class, expression.getClass());
        assertEquals(5.1, (double) expression.getValue(expressionContext));
    }

    @Test
    @Order(13)
    void build_should_throw_JavammLineSyntaxError_if_invalid_constant_is_found() {
        final JavammLineSyntaxError error = assertThrows(JavammLineSyntaxError.class, () ->
            expressionBuilder.build(of("test"), sourceLine));
        assertEquals(
            "Syntax error in 'module1' [Line: 5]: Invalid constant: test",
            error.getMessage());
    }

    /**
     * @author devonline
     * @link http://devonline.academy/javamm
     */
    private static class ValidTokenProvider implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            return Stream.of(
                Stream.of(TypeExpression.values())
                    .map(TypeExpression::getKeyword),
                Stream.of(
                    "'Hello world'",
                    "\"Hello world\"",
                    "'Привет мир'",
                    "\"Привет мир\"",
                    NULL,
                    TRUE,
                    FALSE,
                    "1",
                    "1.1",
                    "variable1",
                    "a"
                )
            ).flatMap(Function.identity()).map(Arguments::of);
        }
    }
}