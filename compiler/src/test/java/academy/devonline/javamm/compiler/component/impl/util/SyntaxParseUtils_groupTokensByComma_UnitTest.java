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

package academy.devonline.javamm.compiler.component.impl.util;

import academy.devonline.javamm.code.fragment.SourceLine;
import academy.devonline.javamm.compiler.component.impl.error.JavammLineSyntaxError;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static academy.devonline.javamm.compiler.component.impl.util.SyntaxParseUtils.groupTokensByComma;
import static java.util.List.of;
import static java.util.stream.Collectors.toUnmodifiableList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SyntaxParseUtils_groupTokensByComma_UnitTest {

    private final SourceLine sourceLine = new SourceLine("module1", 5, List.of());

    @Test
    @Order(1)
    void Should_return_empty_list_if_token_list_if_empty() {
        final List<List<String>> actualResult = groupTokensByComma(of(), sourceLine);
        assertEquals(List.of(), actualResult);
    }

    @Test
    @Order(2)
    void Should_return_unmodifiable_list_of_unmodifiable_lists() {
        final Class<?> expectedClass = Collections.unmodifiableList(List.of()).getClass();

        final List<List<String>> actualResult = groupTokensByComma(List.of("a", ",", "b"), sourceLine);

        assertEquals(expectedClass, actualResult.getClass());
        for (final List<String> list : actualResult) {
            assertEquals(expectedClass, list.getClass());
        }
    }

    @ParameterizedTest
    @Order(3)
    @CsvSource(delimiter = ';', value = {
        "a;                                                         a",
        "a , b;                                                     a|b",
        "a - 2 , b + 1;                                             a - 2|b + 1",
        "a - 2 , b + 1 , 4 , a + ( 4 * b );                         a - 2|b + 1|4|a + ( 4 * b )",
        "a [ i - 8 ] , { 4 } , a + ( 4 * b );                       a [ i - 8 ]|{ 4 }|a + ( 4 * b )",
        "sum ( 4 , a ) , { 1 , 2 } , a [ sum ( 3 , 5 ) ] );         sum ( 4 , a )|{ 1 , 2 }|a [ sum ( 3 , 5 ) ] )",
        // Missing or redundant comma error is not expected here
        "a , sum ( 4 , a ) , fun ( 3 , fun ( 3 , , 5 ) );           a|sum ( 4 , a )|fun ( 3 , fun ( 3 , , 5 ) )",
        "sum ( 4 , , a ) , { 1 , , 2 } , a [ sum ( 3 , , 5 ) ] );   sum ( 4 , , a )|{ 1 , , 2 }|a [ sum ( 3 , , 5 ) ] )",

        "a [ b , ( c , { d , e } , ) , ] , 2;                       a [ b , ( c , { d , e } , ) , ]|2"
    })
    void Should_support_all_valid_test_cases(final String source,
                                             final String expected) {
        final List<String> tokens = List.of(source.split(" "));
        final List<List<String>> expectedResult = Arrays.stream(expected.split("\\|"))
            .map(v -> List.of(v.split(" ")))
            .collect(toUnmodifiableList());

        final List<List<String>> actualResult = groupTokensByComma(tokens, sourceLine);

        assertEquals(expectedResult, actualResult);
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "a , , b",
        "sum ( 4 , a ) , { 1 , 2 } , , a [ sum ( 3 , 5 ) ] )"
    })
    @Order(4)
    void Should_throw_error_if_value_is_missing_or_redundant_comma_is_found(final String source) {
        final List<String> tokens = of(source.split(" "));

        final JavammLineSyntaxError error = assertThrows(JavammLineSyntaxError.class, () ->
            groupTokensByComma(tokens, sourceLine));
        assertEquals("Syntax error in 'module1' [Line: 5]: Missing a value or redundant ','", error.getMessage());
    }
}