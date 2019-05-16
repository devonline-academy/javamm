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

package academy.devonline.javamm.vm.integration.operation;

import academy.devonline.javamm.interpreter.JavammRuntimeError;
import academy.devonline.javamm.vm.integration.AbstractIntegrationTest;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.params.provider.Arguments.arguments;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class IfElseOperationInterpreter_IntegrationTest extends AbstractIntegrationTest {

    @ParameterizedTest
    @ArgumentsSource(IfElseOperationProvider.class)
    @Order(1)
    void Should_interpret_correctly(final List<String> lines,
                                    final List<Object> expectedOutput) {
        assertDoesNotThrow(() -> {
            runBlock(lines);
            assertEquals(expectedOutput, getOutput());
        });
    }

    @Test
    @Order(2)
    void Should_throw_runtime_error() {
        final List<String> lines = of(
            "if ( 8 + 10 ) {",

            "}"
        );
        final JavammRuntimeError error = assertThrows(JavammRuntimeError.class, () -> runBlock(lines));
        assertEquals(
            "Runtime error: Condition expression should be boolean. Current type is integer",
            error.getSimpleMessage());
    }

    /**
     * @author devonline
     * @link http://devonline.academy/javamm
     */
    private static final class IfElseOperationProvider implements ArgumentsProvider {

        @Override
        public Stream<Arguments> provideArguments(final ExtensionContext context) {
            return Stream.of(
                // 1 -----------------------------------
                arguments(of(
                    "if ( 1 < 100 ) {",
                    "   println ('case 1: Inside if')",
                    "}"
                ), of("case 1: Inside if")),
                // 2 -----------------------------------
                arguments(of(
                    "if ( 2 < -100 ) {",
                    "   println ('case 2: Inside if')",
                    "}"
                ), of()),
                // 3 -----------------------------------
                arguments(of(
                    "if ( 3 < -100 ) {",
                    "   println ('case 3: Inside if')",
                    "}",
                    "else {",
                    "   println ('case 3: Inside else')",
                    "}"
                ), of("case 3: Inside else"))
            );
        }
    }
}
