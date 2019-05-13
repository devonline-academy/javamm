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

import academy.devonline.javamm.interpreter.JavammRuntimeError;
import academy.devonline.javamm.interpreter.model.StackTraceItem;
import academy.devonline.javamm.vm.integration.AbstractIntegrationTest;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Stream;

import static java.lang.System.lineSeparator;
import static java.util.List.of;
import static java.util.stream.Collectors.joining;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class StackTrace_IntegrationTest extends AbstractIntegrationTest {

    @Test
    void Should_throw_runtime_error_with_stack_trace() {
        final List<String> lines = of(
            "function main(){",
            "   first()",
            "}",

            "function first(){",
            "   second()",
            "}",

            "function second(){",
            "   var a = 1 / 0",
            "}"
        );

        final String stackTrace = Stream.of(
            "    at second() [integration-vm-test:8]",
            "    at first() [integration-vm-test:5]",
            "    at main() [integration-vm-test:2]"
        ).collect(joining(lineSeparator()));

        final JavammRuntimeError error = assertThrows(JavammRuntimeError.class, () -> runCode(lines));
        assertEquals("Runtime error: / by zero" + lineSeparator() + stackTrace, error.getMessage());

        final List<StackTraceItem> actualStackTraceItems = error.getCurrentStackTrace();
        assertEquals(3, actualStackTraceItems.size());

        assertEquals("integration-vm-test", actualStackTraceItems.get(0).getModuleName());
        assertEquals("second()", actualStackTraceItems.get(0).getFunction().toString());
        assertEquals(8, actualStackTraceItems.get(0).getSourceLineNumber());

        assertEquals("integration-vm-test", actualStackTraceItems.get(1).getModuleName());
        assertEquals("first()", actualStackTraceItems.get(1).getFunction().toString());
        assertEquals(5, actualStackTraceItems.get(1).getSourceLineNumber());

        assertEquals("integration-vm-test", actualStackTraceItems.get(2).getModuleName());
        assertEquals("main()", actualStackTraceItems.get(2).getFunction().toString());
        assertEquals(2, actualStackTraceItems.get(2).getSourceLineNumber());
    }

    @Test
    void Should_throw_stack_overflow_error() {
        final List<String> lines = of(
            "function main(){",
            "   main()",
            "}"
        );

        final JavammRuntimeError error = assertThrows(JavammRuntimeError.class, () -> runCode(lines));
        assertEquals(
            "Runtime error: Stack overflow error. Max stack size is 10" +
                lineSeparator() +
                Stream.generate(() -> "    at main() [integration-vm-test:2]")
                    .limit(10)
                    .collect(joining(lineSeparator())), error.getMessage());
    }

    @Test
    void Should_throw_runtime_error_without_stack_trace() {
        final List<String> lines = of(
            "function test(){",
            "}"
        );

        final JavammRuntimeError error = assertThrows(JavammRuntimeError.class, () -> runCode(lines));
        assertEquals("Runtime error: Main function not found, please define the main function as: 'function main()'", error.getMessage());
        assertEquals(error.getMessage(), error.getMessage());
        assertEquals(List.of(), error.getCurrentStackTrace());
    }
}
