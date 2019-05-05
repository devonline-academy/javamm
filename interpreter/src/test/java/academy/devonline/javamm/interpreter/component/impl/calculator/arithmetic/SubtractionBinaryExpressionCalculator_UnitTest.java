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

package academy.devonline.javamm.interpreter.component.impl.calculator.arithmetic;

import academy.devonline.javamm.code.component.ExpressionContext;
import academy.devonline.javamm.code.fragment.Expression;
import academy.devonline.javamm.interpreter.component.impl.error.JavammLineRuntimeError;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static academy.devonline.javamm.code.fragment.SourceLine.EMPTY_SOURCE_LINE;
import static academy.devonline.javamm.interpreter.TestRuntimeUtils.getCurrentTestRuntime;
import static academy.devonline.javamm.interpreter.component.impl.error.JavammLineRuntimeError.buildRuntimeErrorMessage;
import static academy.devonline.javamm.interpreter.model.CurrentRuntimeProvider.setCurrentRuntime;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

/**
 * This example of unit test for SubtractionBinaryExpressionCalculator
 * Please use ArithmeticBinaryCalculatorIntegrationTest instead this unit test
 *
 * @author devonline
 * @link http://devonline.academy/javamm
 */
@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Disabled
class SubtractionBinaryExpressionCalculator_UnitTest {

    private final SubtractionBinaryExpressionCalculator calculator =
        SubtractionBinaryExpressionCalculator.createArithmeticCalculator();

    @Mock
    private ExpressionContext expressionContext;

    @Mock
    private Expression expression1;

    @Mock
    private Expression expression2;

    @Test
    @Order(1)
    void Should_return_integer_if_integer_minus_integer() {
        when(expression1.getValue(expressionContext)).thenReturn(3);
        when(expression2.getValue(expressionContext)).thenReturn(2);

        final Object result = assertDoesNotThrow(() ->
            calculator.calculate(expressionContext, expression1, expression2));
        assertEquals(Integer.class, result.getClass());
        assertEquals(1, (int) result);
    }

    @Test
    @Order(2)
    void Should_return_double_if_double_minus_integer() {
        when(expression1.getValue(expressionContext)).thenReturn(3.1);
        when(expression2.getValue(expressionContext)).thenReturn(2);

        final Object result = assertDoesNotThrow(() ->
            calculator.calculate(expressionContext, expression1, expression2));
        assertEquals(Double.class, result.getClass());
        assertEquals(1.1, (double) result, 0.0000000001);
    }

    @Test
    @Order(3)
    void Should_return_double_if_integer_minus_double() {
        when(expression1.getValue(expressionContext)).thenReturn(3);
        when(expression2.getValue(expressionContext)).thenReturn(2.1);

        final Object result = assertDoesNotThrow(() ->
            calculator.calculate(expressionContext, expression1, expression2));
        assertEquals(Double.class, result.getClass());
        assertEquals(0.9, (double) result, 0.0000000001);
    }

    @Test
    @Order(4)
    void Should_return_double_if_double_minus_double() {
        when(expression1.getValue(expressionContext)).thenReturn(3.1);
        when(expression2.getValue(expressionContext)).thenReturn(2.1);

        final Object result = assertDoesNotThrow(() ->
            calculator.calculate(expressionContext, expression1, expression2));
        assertEquals(Double.class, result.getClass());
        assertEquals(1.0, (double) result, 0.0000000001);
    }

    @Test
    @Order(5)
    void Should_throw_error_if_second_operand_is_not_number() {
        setCurrentRuntime(getCurrentTestRuntime(EMPTY_SOURCE_LINE));
        when(expression1.getValue(expressionContext)).thenReturn(3);
        when(expression2.getValue(expressionContext)).thenReturn(true);

        final JavammLineRuntimeError error = assertThrows(JavammLineRuntimeError.class, () ->
            calculator.calculate(expressionContext, expression1, expression2));
        assertEquals(
            buildRuntimeErrorMessage("Operator '-' is not supported for types: integer and boolean"),
            error.getMessage());
    }

    @Test
    @Order(6)
    void Should_throw_error_if_first_operand_is_not_number() {
        setCurrentRuntime(getCurrentTestRuntime(EMPTY_SOURCE_LINE));
        when(expression1.getValue(expressionContext)).thenReturn(true);
        when(expression2.getValue(expressionContext)).thenReturn(3);

        final JavammLineRuntimeError error = assertThrows(JavammLineRuntimeError.class, () ->
            calculator.calculate(expressionContext, expression1, expression2));
        assertEquals(
            buildRuntimeErrorMessage("Operator '-' is not supported for types: boolean and integer"),
            error.getMessage());
    }
}
