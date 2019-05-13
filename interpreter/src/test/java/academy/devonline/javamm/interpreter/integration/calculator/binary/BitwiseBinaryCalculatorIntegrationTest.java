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

import static org.junit.jupiter.params.provider.Arguments.arguments;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 * @link https://junit.org/junit5/docs/current/user-guide/#writing-tests-dynamic-tests
 */
class BitwiseBinaryCalculatorIntegrationTest extends AbstractBinaryCalculatorIntegrationTest {

    @Override
    protected Stream<Arguments> validExpressionProvider() {
        return Stream.of(
            // &
            arguments(4, "&", 2, 0),
            arguments(true, "&", false, false),
            // |
            arguments(4, "|", 2, 6),
            arguments(true, "|", false, true),
            // <<
            arguments(100, "<<", 2, 400),
            // >>
            arguments(-100, ">>", 2, -25),
            // >>>
            arguments(-100, ">>>", 2, 1073741799),
            // ^
            arguments(3, "^", 2, 1),
            arguments(true, "^", false, true)
        );
    }

    @Override
    protected Stream<Arguments> invalidExpressionProvider() {
        return Stream.of(
            arguments(3, "&", true,
                "Runtime error: Operator '&' is not supported for types: integer and boolean"),
            arguments(3, "|", true,
                "Runtime error: Operator '|' is not supported for types: integer and boolean"),
            arguments(3, "<<", true,
                "Runtime error: Operator '<<' is not supported for types: integer and boolean"),
            arguments(3, ">>", true,
                "Runtime error: Operator '>>' is not supported for types: integer and boolean"),
            arguments(3, ">>>", true,
                "Runtime error: Operator '>>>' is not supported for types: integer and boolean"),
            arguments(3, "^", true,
                "Runtime error: Operator '^' is not supported for types: integer and boolean"),

            arguments(true, "&", 3,
                "Runtime error: Operator '&' is not supported for types: boolean and integer"),
            arguments(true, "|", 3,
                "Runtime error: Operator '|' is not supported for types: boolean and integer"),
            arguments(true, "<<", 3,
                "Runtime error: Operator '<<' is not supported for types: boolean and integer"),
            arguments(true, ">>", 3,
                "Runtime error: Operator '>>' is not supported for types: boolean and integer"),
            arguments(true, ">>>", 3,
                "Runtime error: Operator '>>>' is not supported for types: boolean and integer"),
            arguments(true, "^", 3,
                "Runtime error: Operator '^' is not supported for types: boolean and integer")
        );
    }
}
