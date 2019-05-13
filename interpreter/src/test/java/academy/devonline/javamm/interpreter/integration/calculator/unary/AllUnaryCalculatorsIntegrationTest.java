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

package academy.devonline.javamm.interpreter.integration.calculator.unary;

import academy.devonline.javamm.interpreter.integration.calculator.AbstractUnaryCalculatorIntegrationTest;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
class AllUnaryCalculatorsIntegrationTest extends AbstractUnaryCalculatorIntegrationTest {

    @Override
    protected Stream<Arguments> validExpressionProvider() {
        return Stream.of(
            // +
            arguments("+", 2, 2),
            arguments("+", 2.0, 2.0),
            // -
            arguments("-", 2, -2),
            arguments("-", 2.0, -2.0),
            // ++
            arguments("++", 2, 3),
            arguments("++", 2.0, 3.0),
            // --
            arguments("--", 2, 1),
            arguments("--", 2.0, 1.0),
            // ~
            arguments("~", 2, -3),
            // !
            arguments("!", true, false),
            arguments("!", false, true)
        );
    }

    @Override
    protected Stream<Arguments> invalidExpressionProvider() {
        return Stream.of(
            arguments("+", "hello",
                "Runtime error: Operator '+' is not supported for type: string"),
            arguments("-", "hello",
                "Runtime error: Operator '-' is not supported for type: string"),
            arguments("++", "hello",
                "Runtime error: Operator '++' is not supported for type: string"),
            arguments("--", "hello",
                "Runtime error: Operator '--' is not supported for type: string"),
            arguments("~", "hello",
                "Runtime error: Operator '~' is not supported for type: string"),
            arguments("~", "hello",
                "Runtime error: Operator '~' is not supported for type: string"),
            arguments("!", "hello",
                "Runtime error: Operator '!' is not supported for type: string")
        );
    }
}
