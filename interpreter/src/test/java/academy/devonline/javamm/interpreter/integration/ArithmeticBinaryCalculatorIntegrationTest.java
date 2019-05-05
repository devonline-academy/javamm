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
import static org.junit.jupiter.params.provider.Arguments.arguments;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ArithmeticBinaryCalculatorIntegrationTest extends AbstractCalculatorIntegrationTest {

    static Stream<Arguments> validExpressionProvider() {
        return Stream.of(
            // +
            arguments(3, "+", 2, 5),
            arguments(3.1, "+", 2, 5.1),
            arguments(3, "+", 2.1, 5.1),
            arguments(3.1, "+", 2.1, 5.2),
            arguments("Hello ", "+", 2, "Hello 2"),
            arguments("Hello ", "+", 2.1, "Hello 2.1"),
            arguments("Hello ", "+", true, "Hello true"),
            arguments("Hello ", "+", false, "Hello false"),
            arguments("Hello ", "+", null, "Hello null"),
            arguments("Hello ", "+", INTEGER, "Hello integer"),
            arguments("Hello ", "+", DOUBLE, "Hello double"),
            arguments("Hello ", "+", BOOLEAN, "Hello boolean"),
            arguments("Hello ", "+", STRING, "Hello string"),
            // TODO Add ARRAY
            arguments("Hello ", "+", "world", "Hello world"),
            arguments(2, "+", " world", "2 world"),
            arguments(2.1, "+", " world", "2.1 world"),
            arguments(true, "+", " world", "true world"),
            arguments(false, "+", " world", "false world"),
            arguments(null, "+", " world", "null world"),
            arguments(INTEGER, "+", " world", "integer world"),
            arguments(DOUBLE, "+", " world", "double world"),
            arguments(BOOLEAN, "+", " world", "boolean world"),
            arguments(STRING, "+", " world", "string world"),
            // TODO Add ARRAY
            // -
            arguments(3, "-", 2, 1),
            arguments(3.1, "-", 2, 1.1),
            arguments(3, "-", 2.1, 0.9),
            arguments(3.1, "-", 2.1, 1.0)
        );
    }

    static Stream<Arguments> invalidExpressionProvider() {
        return Stream.of(
            arguments(3, "+", true,
                buildRuntimeErrorMessage("Operator '+' is not supported for types: integer and boolean")),
            arguments(true, "+", 3,
                buildRuntimeErrorMessage("Operator '+' is not supported for types: boolean and integer")),
            arguments(3, "-", true,
                buildRuntimeErrorMessage("Operator '-' is not supported for types: integer and boolean")),
            arguments(true, "-", 3,
                buildRuntimeErrorMessage("Operator '-' is not supported for types: boolean and integer"))
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
