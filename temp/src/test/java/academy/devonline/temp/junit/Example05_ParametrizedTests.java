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

package academy.devonline.temp.junit;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.params.provider.Arguments.arguments;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 * @link https://junit.org/junit5/docs/current/user-guide
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@Disabled
class Example05_ParametrizedTests {

    static Stream<Arguments> methodSourceProvider() {
        return Stream.of(
            arguments("apple", 1, Arrays.asList("a", "b")),
            arguments("lemon", 2, Arrays.asList("x", "y"))
        );
    }

    @ParameterizedTest
    @MethodSource("methodSourceProvider")
    void MethodSource_example(final String str, final int num, final List<String> list) {
        assertEquals(5, str.length());
        assertTrue(num >= 1 && num <= 2);
        assertEquals(2, list.size());
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "a",
        "aaaaaaaaaaaaa",
        "b",
        "bbbbbbbbbbbbbb",
        "1",
        "1111111111111"
    })
    void ValueSource_with_strings_example(final String value) {
        assertFalse(value.isEmpty());
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    void ValueSource_with_ints_example(final int argument) {
        assertTrue(argument > 0 && argument < 4);
    }

    @ParameterizedTest
    @CsvSource( {
        "5, 5",
        "6, 6",
        "7, 7"
    })
    void CsvSource_example(final String string, final int expectedResult) {
        assertEquals(expectedResult, Integer.parseInt(string));
    }

    @ParameterizedTest
    @ArgumentsSource(MyArgumentsProvider.class)
    void ArgumentsSource_example(final String argument) {
        assertNotNull(argument);
    }

    /**
     * @author devonline
     * @link http://devonline.academy/javamm
     * @link https://junit.org/junit5/docs/current/user-guide
     */
    private static class MyArgumentsProvider implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(final ExtensionContext context) {
            return Stream.of("apple", "banana").map(Arguments::of);
        }
    }
}
