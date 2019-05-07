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
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Stream;

import static java.util.List.of;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.params.provider.Arguments.arguments;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class WhileOperationInterpreter_IntegrationTest extends AbstractIntegrationTest {

    private static final ExecutorService EXECUTOR_SERVICE = Executors.newSingleThreadExecutor();

    private static final long INFINITE_LOOP_TIMEOUT_IN_MILLISECONDS = 500;

    @AfterAll
    static void afterAll() {
        EXECUTOR_SERVICE.shutdownNow();
    }

    @ParameterizedTest
    @ArgumentsSource(WhileOperationProvider.class)
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
            "while ( 8 + 10 ) {",

            "}"
        );
        final JavammRuntimeError error = assertThrows(JavammRuntimeError.class, () -> runBlock(lines));
        assertEquals(
            "Runtime error in 'test' [Line: 2]: Condition expression should be boolean. Current type is integer",
            error.getMessage());
    }

    @ParameterizedTest
    @Order(3)
    @ValueSource(strings = {
        "true",
        "1 > 0",
        "1 typeof integer"
    })
    void Should_support_an_interruption_of_infinite_loop(final String condition) throws InterruptedException, ExecutionException {
        final List<String> lines = of(
            "while ( " + condition + " ) {",
            "}"
        );

        final Future<?> future = EXECUTOR_SERVICE.submit(() -> runBlock(lines));
        try {
            future.get(INFINITE_LOOP_TIMEOUT_IN_MILLISECONDS, TimeUnit.MILLISECONDS);
            fail("Expected infinite loop");
        } catch (final TimeoutException e) {
            // do nothing. TimeoutException is expected.
            assertTrue(future.cancel(true));
        }
    }

    /**
     * @author devonline
     * @link http://devonline.academy/javamm
     */
    static final class WhileOperationProvider implements ArgumentsProvider {

        @Override
        public Stream<Arguments> provideArguments(final ExtensionContext context) {
            return Stream.of(
                arguments(of(
                    "var i = 0",
                    "while ( i ++ < 5 ) {",
                    "   println (i)",
                    "}",
                    "println ('after while')"
                ), of(1, 2, 3, 4, 5, "after while")),

                arguments(of(
                    "var i = 0",
                    "while ( i < 5 ) {",
                    "   println (i ++)",
                    "}",
                    "println ('after while')"
                ), of(0, 1, 2, 3, 4, "after while"))
            );
        }
    }
}
