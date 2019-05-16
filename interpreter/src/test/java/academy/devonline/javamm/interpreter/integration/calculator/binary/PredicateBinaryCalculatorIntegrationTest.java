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
import static academy.devonline.javamm.code.syntax.Keywords.TYPEOF;
import static org.junit.jupiter.params.provider.Arguments.arguments;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 * @link https://junit.org/junit5/docs/current/user-guide/#writing-tests-dynamic-tests
 */
class PredicateBinaryCalculatorIntegrationTest extends AbstractBinaryCalculatorIntegrationTest {

    @Override
    protected Stream<Arguments> validExpressionProvider() {
        return Stream.of(
            /* ************* == ************* */
            arguments(null, "==", null, true),
            arguments(null, "==", 0, false),
            arguments(0, "==", null, false),
            arguments(0, "==", 0, true),

            arguments(3, "==", 3, true),
            arguments(3.0, "==", 3.0, true),
            arguments(true, "==", true, true),
            arguments(false, "==", false, true),
            arguments("hello", "==", "hello", true),
            arguments(INTEGER, "==", INTEGER, true),
            arguments(DOUBLE, "==", DOUBLE, true),
            arguments(BOOLEAN, "==", BOOLEAN, true),
            arguments(STRING, "==", STRING, true),
            arguments(3, "==", 4, false),
            arguments(3.0, "==", 4.0, false),
            arguments(true, "==", false, false),
            arguments(false, "==", true, false),
            arguments("hello", "==", "Hello", false),
            arguments(INTEGER, "==", STRING, false),

            /* ************* != ************* */
            arguments(null, "!=", null, false),
            arguments(null, "!=", 0, true),
            arguments(0, "!=", null, true),
            arguments(0, "!=", 0, false),

            arguments(3, "!=", 3, false),
            arguments(3.0, "!=", 3.0, false),
            arguments(true, "!=", true, false),
            arguments(false, "!=", false, false),
            arguments("hello", "!=", "hello", false),
            arguments(INTEGER, "!=", INTEGER, false),
            arguments(DOUBLE, "!=", DOUBLE, false),
            arguments(BOOLEAN, "!=", BOOLEAN, false),
            arguments(STRING, "!=", STRING, false),
            arguments(3, "!=", 4, true),
            arguments(3.0, "!=", 4.0, true),
            arguments(true, "!=", false, true),
            arguments(false, "!=", true, true),
            arguments("hello", "!=", "Hello", true),
            arguments(INTEGER, "!=", STRING, true),

            /* ************* > ************* */
            arguments(3, ">", 2, true),
            arguments(3.1, ">", 2, true),
            arguments(3, ">", 2.1, true),
            arguments(3.1, ">", 2.1, true),

            arguments(3, ">", 4, false),
            arguments(3.1, ">", 4, false),
            arguments(3, ">", 4.1, false),
            arguments(3.1, ">", 4.1, false),

            /* ************* >= ************* */
            arguments(3, ">=", 2, true),
            arguments(3.1, ">=", 2, true),
            arguments(3, ">=", 2.1, true),
            arguments(3.1, ">=", 2.1, true),

            arguments(3, ">=", 3, true),

            arguments(3, ">=", 4, false),
            arguments(3.1, ">=", 4, false),
            arguments(3, ">=", 4.1, false),
            arguments(3.1, ">=", 4.1, false),

            /* ************* < ************* */
            arguments(3, "<", 2, false),
            arguments(3.1, "<", 2, false),
            arguments(3, "<", 2.1, false),
            arguments(3.1, "<", 2.1, false),

            arguments(3, "<", 4, true),
            arguments(3.1, "<", 4, true),
            arguments(3, "<", 4.1, true),
            arguments(3.1, "<", 4.1, true),

            /* ************* <= ************* */
            arguments(3, "<=", 2, false),
            arguments(3.1, "<=", 2, false),
            arguments(3, "<=", 2.1, false),
            arguments(3.1, "<=", 2.1, false),

            arguments(3, "<=", 3, true),

            arguments(3, "<=", 4, true),
            arguments(3.1, "<=", 4, true),
            arguments(3, "<=", 4.1, true),
            arguments(3.1, "<=", 4.1, true),

            /* ************* typeof ************* */
            arguments(3, TYPEOF, INTEGER, true),
            arguments(3.0, TYPEOF, DOUBLE, true),
            arguments(true, TYPEOF, BOOLEAN, true),
            arguments("hello", TYPEOF, STRING, true),
            arguments(null, TYPEOF, INTEGER, false),
            arguments(null, TYPEOF, DOUBLE, false),
            arguments(null, TYPEOF, BOOLEAN, false),
            arguments(null, TYPEOF, STRING, false)
        );
    }

    @Override
    protected Stream<Arguments> invalidExpressionProvider() {
        return Stream.of(
            arguments(3, "==", true,
                "Runtime error: Operator '==' is not supported for types: integer and boolean"),
            arguments(3, "!=", true,
                "Runtime error: Operator '!=' is not supported for types: integer and boolean"),

            arguments(3, ">", true,
                "Runtime error: Operator '>' is not supported for types: integer and boolean"),
            arguments(3, ">=", true,
                "Runtime error: Operator '>=' is not supported for types: integer and boolean"),
            arguments(3, "<", true,
                "Runtime error: Operator '<' is not supported for types: integer and boolean"),
            arguments(3, "<=", true,
                "Runtime error: Operator '<=' is not supported for types: integer and boolean"),
            arguments(3, TYPEOF, true,
                "Runtime error: Operator 'typeof' is not supported for types: integer and boolean"),

            arguments(true, ">", 3,
                "Runtime error: Operator '>' is not supported for types: boolean and integer"),
            arguments(true, ">=", 3,
                "Runtime error: Operator '>=' is not supported for types: boolean and integer"),
            arguments(true, "<", 3,
                "Runtime error: Operator '<' is not supported for types: boolean and integer"),
            arguments(true, "<=", 3,
                "Runtime error: Operator '<=' is not supported for types: boolean and integer"),
            arguments(true, TYPEOF, 3,
                "Runtime error: Operator 'typeof' is not supported for types: boolean and integer")
        );
    }
}
