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
import academy.devonline.javamm.code.fragment.SourceLine;
import academy.devonline.javamm.code.fragment.expression.PostfixNotationComplexExpression;
import academy.devonline.javamm.code.fragment.expression.UnaryPostfixAssignmentExpression;
import academy.devonline.javamm.code.fragment.expression.UnaryPrefixAssignmentExpression;
import academy.devonline.javamm.compiler.component.ExpressionOperationBuilder;
import academy.devonline.javamm.compiler.component.impl.error.JavammLineSyntaxError;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.util.List;
import java.util.stream.Stream;

import static academy.devonline.javamm.code.fragment.expression.ConstantExpression.valueOf;
import static academy.devonline.javamm.code.fragment.operator.BinaryOperator.ARITHMETIC_ADDITION;
import static academy.devonline.javamm.code.fragment.operator.BinaryOperator.ASSIGNMENT_ADDITION;
import static academy.devonline.javamm.code.fragment.operator.UnaryOperator.DECREMENT;
import static academy.devonline.javamm.code.fragment.operator.UnaryOperator.INCREMENT;
import static academy.devonline.javamm.compiler.MockUtils.variable;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.params.provider.Arguments.arguments;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ExpressionOperationBuilderImpl_UnitTest {

    private final SourceLine sourceLine = new SourceLine("module1", 5, List.of());

    private final ExpressionOperationBuilder expressionOperationBuilder = new ExpressionOperationBuilderImpl();

    @ParameterizedTest
    @ArgumentsSource(AllSupportedExpressionsProvider.class)
    @Order(1)
    void Should_support_all_valid_expression_types(final Expression allowedExpression) {
        assertDoesNotThrow(() -> expressionOperationBuilder.build(allowedExpression, sourceLine));
    }

    @Test
    @Order(2)
    void Should_build_a_operation_if_complex_expression_is_expression_with_assignment_operator() {
        final Expression expression = new PostfixNotationComplexExpression(List.of(
            variable("b"),
            valueOf(1),
            ASSIGNMENT_ADDITION
        ), "b += 1");

        assertDoesNotThrow(() -> expressionOperationBuilder.build(expression, sourceLine));
    }

    @ParameterizedTest
    @ArgumentsSource(InvalidPostfixNotationExpressionsProvider.class)
    @Order(3)
    void Should_throw_error_if_complex_expression_is_not_expression_with_assignment_operator(final Expression expression) {
        final JavammLineSyntaxError error = assertThrows(JavammLineSyntaxError.class, () ->
            expressionOperationBuilder.build(expression,
                new SourceLine("module1", 5, List.of("invalid"))));
        assertEquals("Syntax error in 'module1' [Line: 5]: Expression 'invalid' is not allowed here", error.getMessage());
    }

    @Test
    @Order(4)
    void Should_throw_error_if_expression_type_is_not_supported() {
        final JavammLineSyntaxError error = assertThrows(JavammLineSyntaxError.class, () ->
            expressionOperationBuilder.build(valueOf(1),
                new SourceLine("module1", 5, List.of("1"))));
        assertEquals("Syntax error in 'module1' [Line: 5]: Expression '1' is not allowed here", error.getMessage());
    }

    /**
     * @author devonline
     * @link http://devonline.academy/javamm
     */
    static final class AllSupportedExpressionsProvider implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(final ExtensionContext context) {
            return Stream.of(
                arguments(new UnaryPrefixAssignmentExpression(INCREMENT, variable("a"))),
                arguments(new UnaryPostfixAssignmentExpression(variable("a"), DECREMENT))
            );
        }
    }

    /**
     * @author devonline
     * @link http://devonline.academy/javamm
     */
    static final class InvalidPostfixNotationExpressionsProvider implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(final ExtensionContext context) {
            return Stream.of(
                arguments(
                    // size <= 2
                    new PostfixNotationComplexExpression(List.of(
                        variable("a"),
                        valueOf(1)
                    ), "a 1")
                ),
                arguments(
                    // first lexeme is not variable expression
                    new PostfixNotationComplexExpression(List.of(
                        valueOf(3),
                        valueOf(1),
                        ASSIGNMENT_ADDITION
                    ), "3 += 1")
                ),
                arguments(
                    // last lexeme is not binary operator
                    new PostfixNotationComplexExpression(List.of(
                        variable("a"),
                        valueOf(1),
                        INCREMENT
                    ), "a++ 1")
                ),
                arguments(
                    // last lexeme is not assignment operator
                    new PostfixNotationComplexExpression(List.of(
                        variable("a"),
                        valueOf(1),
                        ARITHMETIC_ADDITION
                    ), "a + 1")
                )
            );
        }
    }
}