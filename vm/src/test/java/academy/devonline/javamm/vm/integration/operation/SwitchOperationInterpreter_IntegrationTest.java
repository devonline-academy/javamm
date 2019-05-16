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

import academy.devonline.javamm.vm.integration.AbstractIntegrationTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
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
import static org.junit.jupiter.params.provider.Arguments.arguments;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SwitchOperationInterpreter_IntegrationTest extends AbstractIntegrationTest {

    private static final ExecutorService EXECUTOR_SERVICE = Executors.newSingleThreadExecutor();

    @AfterAll
    static void afterAll() {
        EXECUTOR_SERVICE.shutdownNow();
    }

    @ParameterizedTest
    @ArgumentsSource(SwitchOperationProvider.class)
    @Order(1)
    void Should_interpret_correctly(final List<String> lines,
                                    final List<Object> expectedOutput) {
        assertDoesNotThrow(() -> {
            runBlock(lines);
            assertEquals(expectedOutput, getOutput());
        });
    }

    /**
     * @author devonline
     * @link http://devonline.academy/javamm
     */
    private static final class SwitchOperationProvider implements ArgumentsProvider {

        @Override
        public Stream<Arguments> provideArguments(final ExtensionContext context) {
            return Stream.of(
                // 1 -----------------------------------
                arguments(of(
                    "// TestCase 1",
                    "var a = 0",
                    "switch ( a ) {",

                    "}",
                    "println ('after switch')"
                ), of("after switch")),
                // 2 -----------------------------------
                arguments(of(
                    "// TestCase 2",
                    "var a = 0",
                    "switch ( a ) {",
                    "   case 3 : {",
                    "       println (3)",
                    "       break",
                    "   }",
                    "   case 1 : {",
                    "       println (1)",
                    "       break",
                    "   }",
                    "   case 2 : {",
                    "       println (2)",
                    "       break",
                    "   }",
                    "}",
                    "println ('after switch')"
                ), of("after switch")),
                // 3 -----------------------------------
                arguments(of(
                    "// TestCase 3",
                    "var a = 0",
                    "switch ( a ) {",
                    "   default : {",
                    "       println ('default')",
                    "   }",
                    "}",
                    "println ('after switch')"
                ), of("default", "after switch")),
                // 4 -----------------------------------
                arguments(of(
                    "// TestCase 4",
                    "var a = 0",
                    "switch ( a ) {",
                    "   case 3 : {",
                    "       println (3)",
                    "       break",
                    "   }",
                    "   case 1 : {",
                    "       println (1)",
                    "       break",
                    "   }",
                    "   case 2 : {",
                    "       println (2)",
                    "       break",
                    "   }",
                    "}",
                    "println ('after switch')"
                ), of("after switch")),
                // 5 -----------------------------------
                arguments(of(
                    "// TestCase 5",
                    "var a = 1",
                    "switch ( a ) {",
                    "   case 3 : {",
                    "       println (3)",
                    "       break",
                    "   }",
                    "   case 1 : {",
                    "       println (1)",
                    "       break",
                    "   }",
                    "   case 2 : {",
                    "       println (2)",
                    "       break",
                    "   }",
                    "   default : {",
                    "       println ('default')",
                    "       break",
                    "   }",
                    "}",
                    "println ('after switch')"
                ), of(1, "after switch")),
                // 6 -----------------------------------
                arguments(of(
                    "// TestCase 6",
                    "var a = 2",
                    "switch ( a ) {",
                    "   case 3 : {",
                    "       println (3)",
                    "       break",
                    "   }",
                    "   case 1 : {",
                    "       println (1)",
                    "       break",
                    "   }",
                    "   case 2 : {",
                    "       println (2)",
                    "       break",
                    "   }",
                    "   default : {",
                    "       println ('default')",
                    "       break",
                    "   }",
                    "}",
                    "println ('after switch')"
                ), of(2, "after switch")),
                // 7 -----------------------------------
                arguments(of(
                    "// TestCase 7",
                    "var a = 3",
                    "switch ( a ) {",
                    "   case 3 : {",
                    "       println (3)",
                    "       break",
                    "   }",
                    "   case 1 : {",
                    "       println (1)",
                    "       break",
                    "   }",
                    "   case 2 : {",
                    "       println (2)",
                    "       break",
                    "   }",
                    "   default : {",
                    "       println ('default')",
                    "       break",
                    "   }",
                    "}",
                    "println ('after switch')"
                ), of(3, "after switch")),
                // 8 -----------------------------------
                arguments(of(
                    "// TestCase 8",
                    "var a = 3",
                    "switch ( a ) {",
                    "   case 3 : {",
                    "       println (3)",
                    "   }",
                    "   case 1 : {",
                    "       println (1)",
                    "   }",
                    "   case 2 : {",
                    "       println (2)",
                    "   }",
                    "   default : {",
                    "       println ('default')",
                    "       break",
                    "   }",
                    "}",
                    "println ('after switch')"
                ), of(3, 1, 2, "default", "after switch")),
                // 9 -----------------------------------
                arguments(of(
                    "// TestCase 9",
                    "var a = 3",
                    "switch ( a ) {",
                    "   case 3 : {",
                    "       println (3)",
                    "   }",
                    "   case 1 : {",
                    "       println (1)",
                    "   }",
                    "   case 2 : {",
                    "       println (2)",
                    "       break",
                    "   }",
                    "   default : {",
                    "       println ('default')",
                    "   }",
                    "}",
                    "println ('after switch')"
                ), of(3, 1, 2, "after switch")),
                // 10 -----------------------------------
                arguments(of(
                    "// TestCase 10",
                    "var a = 3",
                    "switch ( a ) {",
                    "   case 3 : {",
                    "       println (3)",
                    "   }",
                    "   case 1 : {",
                    "       println (1)",
                    "       break",
                    "   }",
                    "   case 2 : {",
                    "       println (2)",
                    "   }",
                    "   default : {",
                    "       println ('default')",
                    "   }",
                    "}",
                    "println ('after switch')"
                ), of(3, 1, "after switch")),
                // 11 -----------------------------------
                arguments(of(
                    "// TestCase 11",
                    "var a = 3",
                    "switch ( a ) {",
                    "   case 3 : {",
                    "       println (3)",
                    "       while(true) {",
                    "           break",
                    "       }",
                    "   }",
                    "   default : {",
                    "       println ('default')",
                    "   }",
                    "}",
                    "println ('after switch')"
                ), of(3, "default", "after switch"))
            );
        }
    }
}
