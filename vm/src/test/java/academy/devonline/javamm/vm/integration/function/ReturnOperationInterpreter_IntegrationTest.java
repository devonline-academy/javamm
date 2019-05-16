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

package academy.devonline.javamm.vm.integration.function;

import academy.devonline.javamm.code.fragment.Void;
import academy.devonline.javamm.vm.integration.AbstractIntegrationTest;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.util.List;
import java.util.stream.Stream;

import static java.util.List.of;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ReturnOperationInterpreter_IntegrationTest extends AbstractIntegrationTest {

    @ParameterizedTest
    @ArgumentsSource(ReturnOperationProvider.class)
    void Should_throw_runtime_error(final List<String> lines,
                                    final List<Object> expectedOutput) {
        assertDoesNotThrow(() -> {
            runCode(lines);
            assertEquals(expectedOutput, getOutput());
        });
    }

    /**
     * @author devonline
     * @link http://devonline.academy/javamm
     */
    private static final class ReturnOperationProvider implements ArgumentsProvider {

        @Override
        public Stream<Arguments> provideArguments(final ExtensionContext context) {
            return Stream.of(
                // 1 -----------------------------------
                arguments(of(
                    "function main(){",
                    "   return",
                    "   println(1)",
                    "}"
                ), of()),
                // 2 -----------------------------------
                arguments(of(
                    "function main(){",
                    "   println(sum(1, 2))",
                    "}",

                    "function sum(a, b){",
                    "   return a + b",
                    "}"
                ), of(3)),
                // 3 -----------------------------------
                arguments(of(
                    "function main(){",
                    "   while(true) {",
                    "       return",
                    "   }",
                    "}"
                ), of()),
                // 4 -----------------------------------
                arguments(of(
                    "function main(){",
                    "   println(sayHello())",
                    "}",

                    "function sayHello(){",
                    "   println('Hello')",
                    "}"
                ), of("Hello", Void.INSTANCE)),
                // 5 -----------------------------------
                arguments(of(
                    "function main(){",
                    "   println(sayHello())",
                    "}",

                    "function sayHello(){",
                    "   println('Hello')",
                    "   return",
                    "}"
                ), of("Hello", Void.INSTANCE)),
                // 6 -----------------------------------
                arguments(of(
                    "function main(){",
                    "   var a = function1()",
                    "   var b = function2()",
                    "   println(a typeof void)",
                    "   println(b typeof void)",
                    "}",

                    "function function1(){",

                    "}",

                    "function function2(){",
                    "   return",
                    "}"
                ), of(true, true)),
                // 7 -----------------------------------
                arguments(of(
                    "function main(){",
                    "   println(calculate(1, 2))",
                    "}",

                    "function calculate(a, b){",
                    "   return ( a + 1 ) * (2 * (32 - b)) + sum (a, b)",
                    "}",

                    "function sum(a, b){",
                    "   return a + b",
                    "}"
                ), of(123))
            );
        }
    }
}

