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
class ForOperationInterpreter_IntegrationTest extends AbstractIntegrationTest {

    private static final ExecutorService EXECUTOR_SERVICE = Executors.newSingleThreadExecutor();

    private static final long INFINITE_LOOP_TIMEOUT_IN_MILLISECONDS = 500;

    @AfterAll
    static void afterAll() {
        EXECUTOR_SERVICE.shutdownNow();
    }

    @ParameterizedTest
    @ArgumentsSource(ForOperationProvider.class)
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
            "for ( var i = 0 ; 8 + 10 ; i ++ ) {",

            "}"
        );
        final JavammRuntimeError error = assertThrows(JavammRuntimeError.class, () -> runBlock(lines));
        assertEquals(
            "Runtime error: Condition expression should be boolean. Current type is integer",
            error.getSimpleMessage());
    }

    @ParameterizedTest
    @Order(3)
    @ValueSource(strings = {
        "",
        "true",
        "1 > 0",
        "1 typeof integer"
    })
    void Should_support_an_interruption_of_infinite_loop(final String condition) throws InterruptedException, ExecutionException {
        final List<String> lines = of(
            "for ( ; " + condition + " ; ) {",
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

    @Test
    @Order(4)
    void Should_create_a_new_scope_for_local_variable() {
        final List<String> lines = of(
            "for ( var i = 0 ; i < 5 ; i++) {",
            "   println (i)",
            "}",
            "println (i)"
        );
        final JavammRuntimeError error = assertThrows(JavammRuntimeError.class, () -> runBlock(lines));
        assertEquals("Runtime error: Variable 'i' is not defined", error.getSimpleMessage());
        assertEquals(of(0, 1, 2, 3, 4), getOutput());
    }

    /**
     * @author devonline
     * @link http://devonline.academy/javamm
     */
    private static final class ForOperationProvider implements ArgumentsProvider {

        @Override
        public Stream<Arguments> provideArguments(final ExtensionContext context) {
            return Stream.of(
                // 1 -----------------------------------
                arguments(of(
                    "for ( var i = 0 ; i < 5 ; i ++ ) {",
                    "   println (i)",
                    "}",
                    "println ('after for')"
                ), of(0, 1, 2, 3, 4, "after for")),
                // 2 -----------------------------------
                arguments(of(
                    "for ( var i = 0 ; i < 5 ; ) {",
                    "   println (i++)",
                    "}",
                    "println ('after for')"
                ), of(0, 1, 2, 3, 4, "after for")),
                // 3 -----------------------------------
                arguments(of(
                    "var i = 0",
                    "for ( ; i < 5 ; i = i + 1) {",
                    "   println (i)",
                    "}",
                    "println ('after for')"
                ), of(0, 1, 2, 3, 4, "after for")),
                // 4 -----------------------------------
                arguments(of(
                    "var i",
                    "for (i = (5 - 3) * (2 - 2); i < 5; i = i + (5 - 4) * (3 - 2)) {",
                    "   println (i)",
                    "}",
                    "println ('after for')"
                ), of(0, 1, 2, 3, 4, "after for")),
                // 5 -----------------------------------
                arguments(of(
                    "var condition = true",
                    "for (var i = 0; condition; ++ i) {",
                    "   println (i)",
                    "   if(i == 4) {",
                    "       condition = false",
                    "   }",
                    "}",
                    "println ('after for')"
                ), of(0, 1, 2, 3, 4, "after for")),
                // 6 -----------------------------------
                arguments(of(
                    "var i = 0",
                    "for (final j = 5; i < 5; ++ i) {",
                    "   println (j)",
                    "}",
                    "println ('after for')"
                ), of(5, 5, 5, 5, 5, "after for")),
                // 7 -----------------------------------
                arguments(of(
                    "var i = 0",
                    "for ( println (i) ; i < 5 ; println (i ++) ) {",
                    "}",
                    "println ('after for')"
                ), of(0, 0, 1, 2, 3, 4, "after for"))
            );
        }
    }
}
