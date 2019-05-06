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

import academy.devonline.javamm.code.fragment.Expression;
import academy.devonline.javamm.code.fragment.Lexeme;
import academy.devonline.javamm.code.fragment.Parenthesis;
import academy.devonline.javamm.code.fragment.SourceLine;
import academy.devonline.javamm.code.fragment.operator.BinaryOperator;
import academy.devonline.javamm.code.fragment.operator.UnaryOperator;
import academy.devonline.javamm.compiler.component.ComplexLexemeValidator;
import academy.devonline.javamm.compiler.component.impl.error.JavammLineSyntaxError;
import org.junit.jupiter.api.Disabled;
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
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static academy.devonline.javamm.code.fragment.Parenthesis.CLOSING_PARENTHESIS;
import static academy.devonline.javamm.code.fragment.Parenthesis.OPENING_PARENTHESIS;
import static academy.devonline.javamm.code.fragment.operator.BinaryOperator.ARITHMETIC_MULTIPLICATION;
import static academy.devonline.javamm.code.fragment.operator.UnaryOperator.LOGIC_NOT;
import static java.lang.String.format;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toUnmodifiableList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Disabled
class ComplexLexemeValidatorImpl_UnitTest {

    private static final BinaryOperator ANY_BINARY_OPERATOR = ARITHMETIC_MULTIPLICATION;

    private static final UnaryOperator ANY_UNARY_OPERATOR = LOGIC_NOT;

    private final SourceLine sourceLine = new SourceLine("module1", 5, List.of());

    private final ComplexLexemeValidator validator = new ComplexLexemeValidatorImpl();

    @Mock
    private Expression expression1;

    @Mock
    private Expression expression2;

    @Test
    @Order(1)
    void Should_throw_IllegalArgumentException_if_lexemes_are_empty() {
        final IllegalArgumentException error = assertThrows(IllegalArgumentException.class, () ->
            validator.validate(List.of(), sourceLine));
        assertEquals("Empty lexemes not allowed", error.getMessage());
    }

    @Test
    @Order(2)
    void Should_throw_error_if_the_first_lexeme_is_binary_operator() {
        final JavammLineSyntaxError error = assertThrows(JavammLineSyntaxError.class, () ->
            validator.validate(List.of(
                ANY_BINARY_OPERATOR,
                expression1
            ), sourceLine));
        assertEquals(format(
            "Syntax error in 'module1' [Line: 5]: Expression can't start with binary operator: '%s'",
            ANY_BINARY_OPERATOR.getCode()), error.getMessage());
    }

    @Test
    @Order(3)
    void Should_throw_error_if_the_last_lexeme_is_binary_operator() {
        final JavammLineSyntaxError error = assertThrows(JavammLineSyntaxError.class, () ->
            validator.validate(List.of(
                expression1,
                ANY_BINARY_OPERATOR
            ), sourceLine));
        assertEquals(format(
            "Syntax error in 'module1' [Line: 5]: Expression can't end with binary operator: '%s'",
            ANY_BINARY_OPERATOR.getCode()), error.getMessage());
    }

    @Test
    @Order(4)
    void Should_throw_error_if_the_last_lexeme_is_unary_operator() {
        final JavammLineSyntaxError error = assertThrows(JavammLineSyntaxError.class, () ->
            validator.validate(List.of(
                expression1,
                ANY_UNARY_OPERATOR
            ), sourceLine));
        assertEquals(format(
            "Syntax error in 'module1' [Line: 5]: Expression can't end with unary operator: '%s'",
            ANY_UNARY_OPERATOR.getCode()), error.getMessage());
    }

    @Test
    @Order(5)
    void Should_throw_error_if_expression_between_binary_operators_is_missing() {
        final JavammLineSyntaxError error = assertThrows(JavammLineSyntaxError.class, () ->
            validator.validate(List.of(
                expression1,
                ANY_BINARY_OPERATOR,
                ANY_BINARY_OPERATOR,
                expression2
            ), sourceLine));
        assertEquals(
            format("Syntax error in 'module1' [Line: 5]: An expression is expected between binary operators: '%s' and '%s'",
                ANY_BINARY_OPERATOR.getCode(), ANY_BINARY_OPERATOR.getCode()),
            error.getMessage());
    }

    @Test
    @Order(6)
    void Should_throw_error_if_binary_operator_between_expressions_is_missing() {
        final JavammLineSyntaxError error = assertThrows(JavammLineSyntaxError.class, () ->
            validator.validate(List.of(
                expression1,
                expression2
            ), sourceLine));
        assertEquals(
            format(
                "Syntax error in 'module1' [Line: 5]: A binary operator is expected between expressions: '%s' and '%s'",
                expression1, expression2),
            error.getMessage());
    }

    @Test
    @Order(7)
    void Should_throw_error_if_it_is_found_a_binary_operator_after_opening_parenthesis() {
        final JavammLineSyntaxError error = assertThrows(JavammLineSyntaxError.class, () ->
            validator.validate(List.of(
                OPENING_PARENTHESIS,
                ANY_BINARY_OPERATOR,
                expression1
            ), sourceLine));
        assertEquals(format(
            "Syntax error in 'module1' [Line: 5]: An expression is expected for binary operator: '%s'",
            ANY_BINARY_OPERATOR.getCode()), error.getMessage());
    }

    @Test
    @Order(8)
    void Should_throw_error_if_it_is_found_a_binary_operator_before_closing_parenthesis() {
        final JavammLineSyntaxError error = assertThrows(JavammLineSyntaxError.class, () ->
            validator.validate(List.of(
                expression1,
                ANY_BINARY_OPERATOR,
                CLOSING_PARENTHESIS
            ), sourceLine));
        assertEquals(format(
            "Syntax error in 'module1' [Line: 5]: An expression is expected for binary operator: '%s'",
            ANY_BINARY_OPERATOR.getCode()), error.getMessage());
    }

    @Test
    @Order(9)
    void Should_throw_error_if_it_is_found_an_unary_operator_before_closing_parenthesis() {
        final JavammLineSyntaxError error = assertThrows(JavammLineSyntaxError.class, () ->
            validator.validate(List.of(
                expression1,
                ANY_UNARY_OPERATOR,
                CLOSING_PARENTHESIS
            ), sourceLine));
        assertEquals(format(
            "Syntax error in 'module1' [Line: 5]: An expression is expected for unary operator: '%s'",
            ANY_UNARY_OPERATOR.getCode()), error.getMessage());
    }

    @Test
    @Order(10)
    void Should_throw_error_if_it_is_found_a_binary_operator_after_unary_operator() {
        final JavammLineSyntaxError error = assertThrows(JavammLineSyntaxError.class, () ->
            validator.validate(List.of(
                expression1,
                ANY_UNARY_OPERATOR,
                ANY_BINARY_OPERATOR,
                expression2
            ), sourceLine));
        assertEquals(format(
            "Syntax error in 'module1' [Line: 5]: An expression is expected for binary operator: '%s'",
            ANY_BINARY_OPERATOR.getCode()), error.getMessage());
    }

    @ParameterizedTest
    @CsvSource( {
        "(, )",
        "), ("
    })
    @Order(11)
    void Should_throw_error_if_there_are_no_lexemes_between_opening_and_closing_parentheses(final String parenthesis1,
                                                                                            final String parenthesis2) {
        final JavammLineSyntaxError error = assertThrows(JavammLineSyntaxError.class, () ->
            validator.validate(List.of(
                expression1,
                Parenthesis.of(parenthesis1).orElseThrow(),
                Parenthesis.of(parenthesis2).orElseThrow()
            ), sourceLine));
        assertEquals("Syntax error in 'module1' [Line: 5]: Parentheses are incorrectly placed",
            error.getMessage());
    }

    @ParameterizedTest
    @ArgumentsSource(InvalidLexemeCombinationWithParenthesesProvider.class)
    @Order(12)
    void Should_throw_error_if_binary_operator_between_expressions_is_missing_ignoring_parentheses(final List<Lexeme> lexemes,
                                                                                                   final String expectedError) {
        final JavammLineSyntaxError error = assertThrows(JavammLineSyntaxError.class, () ->
            validator.validate(lexemes, sourceLine));
        assertEquals(expectedError, error.getMessage());
    }

    /**
     * @author devonline
     * @link http://devonline.academy/javamm
     */
    static final class InvalidLexemeCombinationWithParenthesesProvider implements ArgumentsProvider {

        private static final int MAX_PARENTHESIS_REPEAT_COUNT = 5;

        @Override
        public Stream<? extends Arguments> provideArguments(final ExtensionContext context) {
            final Expression expression1 = mock(Expression.class);
            when(expression1.toString()).thenReturn("1");
            final Expression expression2 = mock(Expression.class);
            when(expression2.toString()).thenReturn("2");
            final String errorMessage =
                "Syntax error in 'module1' [Line: 5]: A binary operator is expected between expressions: '1' and '2'";

            return Arrays.stream(Parenthesis.values())
                .map(parenthesis -> IntStream.range(1, MAX_PARENTHESIS_REPEAT_COUNT + 1)
                    .mapToObj(repeatCount -> Stream.generate(() -> (Lexeme) parenthesis).limit(repeatCount))
                    .map(parenthesisRepeatStream -> arguments(
                        Stream.of(
                            Stream.of(expression1),
                            parenthesisRepeatStream,
                            Stream.of(expression2)
                        ).flatMap(identity()).collect(toUnmodifiableList()),
                        errorMessage
                    ))).flatMap(identity());
            /*
            return
                1 ( 2
                1 ( ( 2
                1 ( ( ( 2
                1 ( ( ( ( 2
                1 ( ( ( ( ( 2
                1 ) 2
                1 ) ) 2
                1 ) ) ) 2
                1 ) ) ) ) 2
                1 ) ) ) ) ) 2
             */
        }
    }
}