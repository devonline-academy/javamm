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

package academy.devonline.javamm.compiler.integration.function;

import academy.devonline.javamm.code.fragment.ByteCode;
import academy.devonline.javamm.compiler.integration.AbstractIntegrationTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

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
class FunctionReader_Expected_success_IntegrationTest extends AbstractIntegrationTest {

    static Stream<Arguments> validSourceLineProvider() {
        return Stream.of(
            arguments(of(
                "function main() {",

                "}"
            )),
            arguments(of(
                "function factorial(a) {",

                "}"
            )),
            arguments(of(
                "function sum(a, b) {",

                "}"
            )),
            arguments(of(
                "function sum(a, b, c, d, e) {",

                "}"
            ))
        );
    }

    @ParameterizedTest
    @MethodSource("validSourceLineProvider")
    void Should_compile_the_code_successful(final List<String> lines) {
        final ByteCode byteCode = assertDoesNotThrow(() -> compile(lines));
        assertEquals(1, byteCode.getAllFunctions().size());
    }
}
