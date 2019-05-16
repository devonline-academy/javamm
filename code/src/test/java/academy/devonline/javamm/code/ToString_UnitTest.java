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

package academy.devonline.javamm.code;

import academy.devonline.javamm.code.fragment.FunctionName;
import academy.devonline.javamm.code.fragment.Lexeme;
import academy.devonline.javamm.code.fragment.Operation;
import academy.devonline.javamm.code.fragment.SourceLine;
import academy.devonline.javamm.code.fragment.Variable;
import academy.devonline.javamm.code.fragment.expression.ComplexExpression;
import academy.devonline.javamm.code.fragment.expression.ConstantExpression;
import academy.devonline.javamm.code.fragment.expression.FunctionInvokeExpression;
import academy.devonline.javamm.code.fragment.expression.NullValueExpression;
import academy.devonline.javamm.code.fragment.expression.PostfixNotationComplexExpression;
import academy.devonline.javamm.code.fragment.expression.TypeExpression;
import academy.devonline.javamm.code.fragment.expression.UnaryPostfixAssignmentExpression;
import academy.devonline.javamm.code.fragment.expression.UnaryPrefixAssignmentExpression;
import academy.devonline.javamm.code.fragment.expression.VariableExpression;
import academy.devonline.javamm.code.fragment.function.DeveloperFunction;
import academy.devonline.javamm.code.fragment.operation.Block;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.util.List;
import java.util.stream.Stream;

import static academy.devonline.javamm.code.fragment.operator.BinaryOperator.ARITHMETIC_ADDITION;
import static academy.devonline.javamm.code.fragment.operator.BinaryOperator.ARITHMETIC_MULTIPLICATION;
import static academy.devonline.javamm.code.fragment.operator.UnaryOperator.DECREMENT;
import static academy.devonline.javamm.code.fragment.operator.UnaryOperator.INCREMENT;
import static java.lang.String.format;
import static java.lang.String.join;
import static java.util.function.Function.identity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ToString_UnitTest {


    @ParameterizedTest
    @ArgumentsSource(InstanceProvider.class)
    void toString_should_return_a_human_readable_value(final Object instance,
                                                       final String expectedToStringValue) {
        assertEquals(expectedToStringValue, instance.toString());
    }

    /**
     * @author devonline
     * @link http://devonline.academy/javamm
     */
    static final class InstanceProvider implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(final ExtensionContext context) {
            return Stream.of(
                operationsProvider(),
                simpleExpressionProvider(),
                functionInvokeExpressionProvider(),
                complexExpressionProvider(),
                functionsProvider()
            ).flatMap(identity());
        }

        private Stream<? extends Arguments> operationsProvider() {
            final Operation operation = mock(Operation.class);
            when(operation.toString()).thenReturn("operation");

            return Stream.of(
                arguments(
                    new Block(List.of(operation, operation),
                        new SourceLine("module1", 5, List.of())),
                    "operation" + System.lineSeparator() + "operation"
                )
            );
        }

        private Stream<? extends Arguments> simpleExpressionProvider() {
            return Stream.of(
                arguments(
                    constant(12),
                    "12"
                ),
                arguments(
                    constant("text"),
                    "text"
                ),
                arguments(
                    constant(true),
                    "true"
                ),
                arguments(
                    constant(false),
                    "false"
                ),
                arguments(
                    variable("a"),
                    "a"
                ),
                arguments(
                    new UnaryPrefixAssignmentExpression(DECREMENT, variable("a")),
                    "--a"
                ),
                arguments(
                    new UnaryPostfixAssignmentExpression(variable("a"), INCREMENT),
                    "a++"
                ),
                arguments(
                    TypeExpression.INTEGER,
                    "integer"
                ),
                arguments(
                    NullValueExpression.getInstance(),
                    "null"
                )
            );
        }

        private Stream<? extends Arguments> functionInvokeExpressionProvider() {
            return Stream.of(
                arguments(
                    new FunctionInvokeExpression(functionName("function1"), List.of()),
                    "function1()"
                ),
                arguments(
                    new FunctionInvokeExpression(functionName("function1"), List.of(
                        constant(12)
                    )),
                    "function1(12)"
                ),
                arguments(
                    new FunctionInvokeExpression(functionName("function1"), List.of(
                        constant(12),
                        variable("a"),
                        constant(23)
                    )),
                    "function1(12,a,23)"
                )
            );
        }

        private Stream<? extends Arguments> complexExpressionProvider() {
            final List<Lexeme> lexemes = List.of(
                constant(12),
                ARITHMETIC_ADDITION,
                variable("a"),
                ARITHMETIC_MULTIPLICATION,
                constant(23)
            );
            return Stream.of(
                arguments(
                    new ComplexExpression(lexemes) {
                    },
                    "12 + a * 23"
                ),
                // toString implementation of PostfixNotationExpression does not depend on lexemes
                arguments(
                    new PostfixNotationComplexExpression(lexemes, "a + 12 * 4"),
                    "a + 12 * 4"
                ),
                arguments(
                    new PostfixNotationComplexExpression(lexemes, "(a + 12) * 4 - 45"),
                    "(a + 12) * 4 - 45"
                )
            );
        }

        private Stream<? extends Arguments> functionsProvider() {
            final SourceLine sourceLine = new SourceLine("module1", 5, List.of());
            final Block body = new Block(List.of(), sourceLine);
            return Stream.of(
                arguments(
                    new DeveloperFunction.Builder()
                        .setDeclarationSourceLine(sourceLine)
                        .setName(functionName("generate"))
                        .setBody(body)
                        .build(),
                    "generate()"
                ),
                arguments(
                    new DeveloperFunction.Builder()
                        .setDeclarationSourceLine(sourceLine)
                        .setName(functionName("factorial"))
                        .setParameters(List.of(
                            variable("a").getVariable()
                        ))
                        .setBody(body)
                        .build(),
                    "factorial(a)"
                ),
                arguments(
                    new DeveloperFunction.Builder()
                        .setDeclarationSourceLine(sourceLine)
                        .setName(functionName("sum"))
                        .setParameters(List.of(
                            variable("a").getVariable(),
                            variable("b").getVariable()
                        ))
                        .setBody(body)
                        .build(),
                    "sum(a,b)"
                )
            );
        }

        private FunctionName functionName(final String name,
                                          final String... arguments) {
            final FunctionName functionName = mock(FunctionName.class);
            when(functionName.getName()).thenReturn(name);
            when(functionName.toString()).thenReturn(format("%s(%s)", name, join(",", arguments)));
            return functionName;
        }

        private ConstantExpression constant(final Object value) {
            return ConstantExpression.valueOf(value);
        }

        private VariableExpression variable(final String name) {
            final Variable variable = mock(Variable.class);
            when(variable.toString()).thenReturn(name);
            return new VariableExpression(variable);
        }
    }
}
