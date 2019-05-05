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

package academy.devonline.javamm.interpreter.integration.calculator;

import academy.devonline.javamm.interpreter.JavammRuntimeError;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

import static academy.devonline.javamm.code.fragment.SourceLine.EMPTY_SOURCE_LINE;
import static academy.devonline.javamm.interpreter.TestRuntimeUtils.getCurrentTestRuntime;
import static academy.devonline.javamm.interpreter.model.CurrentRuntimeProvider.setCurrentRuntime;
import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public abstract class AbstractUnaryCalculatorIntegrationTest extends AbstractCalculatorIntegrationTest {

    @TestFactory
    @Order(1)
    Stream<DynamicTest> shouldCalculateSuccessful() {
        return validExpressionProvider().map(args -> {
            final String operator = (String) args.get()[0];
            final Object operand = args.get()[1];
            final Object expectedResult = args.get()[2];
            return dynamicTest(
                format("Should calculate successful: %s %s = %s", operator, operand, expectedResult),
                () -> shouldCalculateSuccessful(operator, operand, expectedResult));
        });
    }

    protected abstract Stream<Arguments> validExpressionProvider();

    @TestFactory
    @Order(2)
    Stream<DynamicTest> shouldThrowError() {
        return invalidExpressionProvider().map(args -> {
            final String operator = (String) args.get()[0];
            final Object operand = args.get()[1];
            final String expectedMessage = (String) args.get()[2];
            return dynamicTest(
                format("Should throw error: %s %s -> '%s'", operator, operand, expectedMessage),
                () -> shouldThrowError(operator, operand, expectedMessage));
        });
    }

    protected abstract Stream<Arguments> invalidExpressionProvider();

    private void shouldCalculateSuccessful(final String operator,
                                           final Object operand,
                                           final Object expectedResult) {
        setCurrentRuntime(getCurrentTestRuntime(EMPTY_SOURCE_LINE));
        assertDoesNotThrow(() -> {
            final Object actualResult = calculate(operator, operand);
            assertEquals(expectedResult.getClass(), actualResult.getClass());
            if (expectedResult instanceof Double) {
                assertEquals((double) expectedResult, (double) actualResult, 0.0000000001);
            } else {
                assertEquals(expectedResult, actualResult);
            }
        });
    }

    private void shouldThrowError(final String operator,
                                  final Object operand,
                                  final String expectedMessage) {
        setCurrentRuntime(getCurrentTestRuntime(EMPTY_SOURCE_LINE));
        final JavammRuntimeError error = assertThrows(JavammRuntimeError.class, () -> calculate(operator, operand));
        assertEquals(expectedMessage, error.getMessage());
    }
}
