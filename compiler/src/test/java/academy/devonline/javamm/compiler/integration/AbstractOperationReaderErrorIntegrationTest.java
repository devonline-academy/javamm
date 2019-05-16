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

package academy.devonline.javamm.compiler.integration;

import academy.devonline.javamm.compiler.JavammSyntaxError;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.params.provider.Arguments;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;
import static org.junit.jupiter.params.provider.Arguments.arguments;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
public abstract class AbstractOperationReaderErrorIntegrationTest extends AbstractIntegrationTest {

    @TestFactory
    @Order(2)
    @SuppressWarnings("unchecked")
    Stream<DynamicTest> Should_throw_syntax_error() {
        final int[] index = new int[1];
        final Set<String> names = new HashSet<>();
        return invalidSourceLineProvider().map(args -> {
            final List<String> lines = (List<String>) args.get()[0];
            final String expectedMessage = (String) args.get()[1];
            final String name = args.get().length == 3 ? String.valueOf(args.get()[2]) : null;
            if (names.add(name)) {
                index[0] = 0;
            }
            final String subName = name != null ? format(" [%s-%s]", args.get()[2], ++index[0]) : "";

            return dynamicTest(
                format("%s :: %s -> '%s'", subName, String.join(" ", lines), expectedMessage),
                () -> {
                    final JavammSyntaxError error = assertThrows(JavammSyntaxError.class, () ->
                        wrapMainFunctionAndCompile(lines, false));
                    assertEquals(expectedMessage, error.getMessage());
                });
        });
    }

    protected abstract Stream<Arguments> invalidSourceLineProvider();

    protected final Stream<Arguments> named(final Stream<Arguments> stream, final String name) {
        return stream.map(arguments -> {
            final List<Object> args = new ArrayList<>(Arrays.asList(arguments.get()));
            args.add(name.toUpperCase());
            return arguments(args.toArray());
        });
    }
}
