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

import academy.devonline.javamm.interpreter.integration.calculator.AbstractBinaryCalculatorIntegrationTest;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

import static academy.devonline.javamm.code.fragment.expression.TypeExpression.BOOLEAN;
import static academy.devonline.javamm.code.fragment.expression.TypeExpression.DOUBLE;
import static academy.devonline.javamm.code.fragment.expression.TypeExpression.INTEGER;
import static academy.devonline.javamm.code.fragment.expression.TypeExpression.STRING;
import static java.lang.Double.NaN;
import static java.lang.Double.POSITIVE_INFINITY;
import static org.junit.jupiter.params.provider.Arguments.arguments;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 * @link https://junit.org/junit5/docs/current/user-guide/#writing-tests-dynamic-tests
 */
class ArithmeticBinaryCalculatorIntegrationTest extends AbstractBinaryCalculatorIntegrationTest {

    @Override
    protected Stream<Arguments> validExpressionProvider() {
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
            arguments(3.1, "-", 2.1, 1.0),
            // *
            arguments(3, "*", 2, 6),
            arguments(3.1, "*", 2, 6.2),
            arguments(3, "*", 2.1, 6.3),
            arguments(3.1, "*", 2.1, 6.51),
            // /
            arguments(3, "/", 2, 1),
            arguments(3.1, "/", 2, 1.55),
            arguments(3, "/", 2.0, 1.5),
            arguments(3.1, "/", 2.0, 1.55),
            arguments(3.1, "/", 0, POSITIVE_INFINITY),
            arguments(3, "/", 0.0, POSITIVE_INFINITY),
            arguments(3.1, "/", 0.0, POSITIVE_INFINITY),
            // %
            arguments(3, "%", 2, 1),
            arguments(3.1, "%", 2, 1.1),
            arguments(3, "%", 2.0, 1.0),
            arguments(3.1, "%", 2.0, 1.1),
            arguments(3.1, "%", 0, NaN),
            arguments(3, "%", 0.0, NaN),
            arguments(3.1, "%", 0.0, NaN)
        );
    }

    @Override
    protected Stream<Arguments> invalidExpressionProvider() {
        return Stream.of(
            arguments(3, "+", true,
                "Runtime error: Operator '+' is not supported for types: integer and boolean"),
            arguments(3, "-", true,
                "Runtime error: Operator '-' is not supported for types: integer and boolean"),
            arguments(3, "*", true,
                "Runtime error: Operator '*' is not supported for types: integer and boolean"),
            arguments(3, "/", true,
                "Runtime error: Operator '/' is not supported for types: integer and boolean"),
            arguments(3, "%", true,
                "Runtime error: Operator '%' is not supported for types: integer and boolean"),

            arguments(true, "+", 3,
                "Runtime error: Operator '+' is not supported for types: boolean and integer"),
            arguments(true, "-", 3,
                "Runtime error: Operator '-' is not supported for types: boolean and integer"),
            arguments(true, "*", 3,
                "Runtime error: Operator '*' is not supported for types: boolean and integer"),
            arguments(true, "/", 3,
                "Runtime error: Operator '/' is not supported for types: boolean and integer"),
            arguments(true, "%", 3,
                "Runtime error: Operator '%' is not supported for types: boolean and integer"),

            arguments(3, "/", 0,
                "Runtime error: / by zero"),
            arguments(3, "%", 0,
                "Runtime error: / by zero")
        );
    }
}
