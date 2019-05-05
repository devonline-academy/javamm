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

package academy.devonline.javamm.interpreter.integration.calculator.binary;

import academy.devonline.javamm.code.component.ExpressionContext;
import academy.devonline.javamm.code.fragment.Expression;
import academy.devonline.javamm.interpreter.integration.calculator.AbstractBinaryCalculatorIntegrationTest;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

import static academy.devonline.javamm.interpreter.component.impl.error.JavammLineRuntimeError.buildRuntimeErrorMessage;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.params.provider.Arguments.arguments;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 * @link https://junit.org/junit5/docs/current/user-guide/#writing-tests-dynamic-tests
 */
class LogicBinaryCalculatorIntegrationTest extends AbstractBinaryCalculatorIntegrationTest {

    private static Expression notEvaluatedExpression() {
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

    @Override
    protected Stream<Arguments> validExpressionProvider() {
        return Stream.of(
            // &&
            arguments(true, "&&", false, false),
            arguments(true, "&&", true, true),
            arguments(false, "&&", notEvaluatedExpression(), false),

            // ||
            arguments(false, "||", false, false),
            arguments(false, "||", true, true),
            arguments(true, "||", notEvaluatedExpression(), true)
        );
    }

    @Override
    protected Stream<Arguments> invalidExpressionProvider() {
        return Stream.of(
            arguments(true, "&&", 3,
                buildRuntimeErrorMessage("Operator '&&' is not supported for types: boolean and integer")),
            arguments(3, "&&", true,
                buildRuntimeErrorMessage("First operand for operator '&&' has invalid type: integer")),

            arguments(false, "||", 3,
                buildRuntimeErrorMessage("Operator '||' is not supported for types: boolean and integer")),
            arguments(3, "||", true,
                buildRuntimeErrorMessage("First operand for operator '||' has invalid type: integer"))
        );
    }
}
