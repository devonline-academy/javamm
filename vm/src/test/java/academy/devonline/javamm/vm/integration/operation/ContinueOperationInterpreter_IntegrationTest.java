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
import org.junit.jupiter.api.AfterAll;
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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
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
class ContinueOperationInterpreter_IntegrationTest extends AbstractIntegrationTest {

    private static final ExecutorService EXECUTOR_SERVICE = Executors.newSingleThreadExecutor();

    @AfterAll
    static void afterAll() {
        EXECUTOR_SERVICE.shutdownNow();
    }

    @ParameterizedTest
    @ArgumentsSource(ContinueOperationProvider.class)
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
            "continue"
        );
        final JavammRuntimeError error = assertThrows(JavammRuntimeError.class, () -> runBlock(lines));
        assertEquals(
            "Runtime error: Operation 'continue' not expected here",
            error.getSimpleMessage());
    }

    /**
     * @author devonline
     * @link http://devonline.academy/javamm
     */
    private static final class ContinueOperationProvider implements ArgumentsProvider {

        @Override
        public Stream<Arguments> provideArguments(final ExtensionContext context) {
            return Stream.of(
                // 1 -----------------------------------
                arguments(of(
                    "var i = 0",
                    "while ( i ++ < 5 ) {",
                    "   println (i)",
                    "   if (i > 2) {",
                    "       continue",
                    "   }",
                    "   println (i)",
                    "}",
                    "println ('after while')"
                ), of(1, 1, 2, 2, 3, 4, 5, "after while")),
                // 2 -----------------------------------
                arguments(of(
                    "var i = 0",
                    "do {",
                    "   println (i)",
                    "   if (i > 2) {",
                    "       continue",
                    "   }",
                    "   println (i)",
                    "}",
                    "while ( i ++ < 5 )",
                    "println ('after do while')"
                ), of(0, 0, 1, 1, 2, 2, 3, 4, 5, "after do while")),
                // 3 -----------------------------------
                arguments(of(
                    "var i = 0",
                    "for ( ; i < 5 ; i ++) {",
                    "   println (i)",
                    "   if (i > 2) {",
                    "       continue",
                    "   }",
                    "   println (i)",
                    "}",
                    "println ('after for')"
                ), of(0, 0, 1, 1, 2, 2, 3, 4, "after for"))
            );
        }
    }
}
