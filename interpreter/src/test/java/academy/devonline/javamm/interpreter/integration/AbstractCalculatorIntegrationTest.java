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
import academy.devonline.javamm.code.fragment.expression.ConstantExpression;
import academy.devonline.javamm.code.fragment.expression.NullValueExpression;
import academy.devonline.javamm.code.fragment.operator.BinaryOperator;
import academy.devonline.javamm.interpreter.InterpreterConfigurator;
import academy.devonline.javamm.interpreter.component.CalculatorFacade;

import static org.mockito.Mockito.mock;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
abstract class AbstractCalculatorIntegrationTest {

    private final ExpressionContext expressionContext = mock(ExpressionContext.class);

    private final CalculatorFacade calculatorFacade = new InterpreterConfigurator().getCalculatorFacade();

    final Object calculate(final Object operand1, final String operator, final Object operand2) {
        return calculatorFacade.calculate(
            expressionContext,
            exp(operand1),
            BinaryOperator.of(operator).orElseThrow(),
            exp(operand2));
    }

    private Expression exp(final Object value) {
        if (value == null) {
            return NullValueExpression.getInstance();
        } else if (value instanceof Expression) {
            return (Expression) value;
        } else {
            return ConstantExpression.valueOf(value);
        }
    }
}
