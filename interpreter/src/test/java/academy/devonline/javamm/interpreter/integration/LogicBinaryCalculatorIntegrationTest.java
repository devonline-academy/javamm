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

package academy.devonline.javamm.interpreter.integration;

import academy.devonline.javamm.code.component.ExpressionContext;
import academy.devonline.javamm.code.fragment.Expression;
import academy.devonline.javamm.interpreter.JavammRuntimeError;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static academy.devonline.javamm.code.fragment.SourceLine.EMPTY_SOURCE_LINE;
import static academy.devonline.javamm.code.fragment.expression.TypeExpression.STRING;
import static academy.devonline.javamm.code.syntax.Keywords.BOOLEAN;
import static academy.devonline.javamm.code.syntax.Keywords.DOUBLE;
import static academy.devonline.javamm.code.syntax.Keywords.INTEGER;
import static academy.devonline.javamm.interpreter.TestRuntimeUtils.getCurrentTestRuntime;
import static academy.devonline.javamm.interpreter.component.impl.error.JavammLineRuntimeError.buildRuntimeErrorMessage;
import static academy.devonline.javamm.interpreter.model.CurrentRuntimeProvider.setCurrentRuntime;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.params.provider.Arguments.arguments;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class LogicBinaryCalculatorIntegrationTest extends AbstractCalculatorIntegrationTest {

    static Expression notEvaluatedExpression() {
        return new Expression() {
            @Override
            public Object getValue(final ExpressionContext expressionContext) {
                return fail("getValue should not be invoked for expression");
            }

            @Override
            public String toString() {
                return "Expression mock";
            }
        };
    }

    static Stream<Arguments> validExpressionProvider() {
        return Stream.of(
            // &&
            arguments(true, "&&", false, false),
            arguments(true, "&&", true, true),
            arguments(false, "&&", notEvaluatedExpression(), false)

            // ||
            /*arguments(false, "||", false, false),
            arguments(false, "||", true, true),
            arguments(true, "||", notEvaluatedExpression(), true)*/
        );
    }

    static Stream<Arguments> invalidExpressionProvider() {
        return Stream.of(
            arguments(true, "&&", 3,
                buildRuntimeErrorMessage("Operator '&&' is not supported for types: boolean and integer")),
            arguments(3, "&&", true,
                buildRuntimeErrorMessage("First operand for operator '&&' has invalid type: integer"))

            /*arguments(false, "||", 3,
                buildRuntimeErrorMessage("Operator '||' is not supported for types: boolean and integer")),
            arguments(3, "||", true,
                buildRuntimeErrorMessage("First operand for operator '||' has invalid type: integer"))*/
        );
    }

    @ParameterizedTest
    @MethodSource("validExpressionProvider")
    @Order(1)
    void Should_calculate_successful(final Object operand1,
                                     final String operator,
                                     final Object operand2,
                                     final Object expectedResult) {
        setCurrentRuntime(getCurrentTestRuntime(EMPTY_SOURCE_LINE));
        assertDoesNotThrow(() -> {
            final Object actualResult = calculate(operand1, operator, operand2);
            assertEquals(expectedResult.getClass(), actualResult.getClass());
            if (expectedResult instanceof Double) {
                assertEquals((double) expectedResult, (double) actualResult, 0.0000000001);
            } else {
                assertEquals(expectedResult, actualResult);
            }
        });
    }

    @ParameterizedTest
    @MethodSource("invalidExpressionProvider")
    @Order(2)
    void Should_throw_error(final Object operand1,
                            final String operator,
                            final Object operand2,
                            final String expectedMessage) {
        setCurrentRuntime(getCurrentTestRuntime(EMPTY_SOURCE_LINE));
        final JavammRuntimeError error = assertThrows(JavammRuntimeError.class, () ->
            calculate(operand1, operator, operand2));
        assertEquals(expectedMessage, error.getMessage());
    }
}
