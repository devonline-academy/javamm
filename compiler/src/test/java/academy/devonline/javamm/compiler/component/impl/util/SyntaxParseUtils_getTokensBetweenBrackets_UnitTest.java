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
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static academy.devonline.javamm.compiler.component.impl.util.SyntaxParseUtils.getTokensBetweenFirstAndLastBrackets;
import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SyntaxParseUtils_getTokensBetweenBrackets_UnitTest {

    @ParameterizedTest
    @Order(1)
    @CsvSource(delimiter = ';', value = {
        // Operations and functions
        "(; );      if ( a < 4 ) {;                                 a < 4",
        "(; );      if ( ( a < 4 ) ) {;                             ( a < 4 )",
        "(; );      if ( ( a + 4 ) * 5 < ( b - 6 ) / ( - c ) ) {;   ( a + 4 ) * 5 < ( b - 6 ) / ( - c )",
        "(; );      function1 ( ) {;                                ",
        "(; );      function1 ( ( ) ) {;                            ( )",
        // Array declaration with values
        "{; };      var a = { };                                    ",
        "{; };      var a = { 5, 6, 7 };                            5, 6, 7",
        "{; };      var a = { { 5 } , { 6, 7 } };                   { 5 } , { 6, 7 }",
        "{; };      var a = { { 6, { 7 } } , 3 , { { { } } } };     { 6, { 7 } } , 3 , { { { } } }",
        // Empty array declaration
        "[; ];      var a = array [ ];                              ",
        "[; ];      var a = array [ 4 + a ];                        4 + a",
        "[; ];      var a = array [ b [ 4 + a [ ar [ 0 ] ] ] ];     b [ 4 + a [ ar [ 0 ] ] ]",
        "[; ];      var a = array [ 4 * sum ( 3 , a [ 5 ] ) ];      4 * sum ( 3 , a [ 5 ] )"
    })
    void Should_support_all_valid_test_cases_with_empty_results(final String openingBracket,
                                                                final String closingBracket,
                                                                final String source,
                                                                final String expected) {
        final SourceLine sourceLine = new SourceLine("module1", 5, List.of(source.split(" ")));
        final List<String> expectedTokens = expected == null ? List.of() : List.of(expected.split(" "));

        final List<String> actualTokens =
            getTokensBetweenFirstAndLastBrackets(openingBracket, closingBracket, sourceLine, true);

        assertEquals(expectedTokens, actualTokens);
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
        // Operations and functions
        "(; );      if a < 4 ) {",
        "(; );      if ( a < 4 ) ) {",
        "(; );      if ( a + 4 ) * 5 < ( b - 6 ) / ( - c ) ) {",
        "(; );      function1 ) {",
        "(; );      function1 ( ) ) {",
        // Array declaration with values
        "{; };      var a = }",
        "{; };      var a = 5, 6, 7 }",
        "{; };      var a = { 5 } , { 6, 7 } }",
        "{; };      var a = { 6, { 7 } } , 3 , { { { } } } }",
        // Empty array declaration
        "[; ];      var a = array ]",
        "[; ];      var a = array 4 + a ]",
        "[; ];      var a = array b [ 4 + a [ ar [ 0 ] ] ] ]",
        "[; ];      var a = array 4 * sum ( 3 , a [ 5 ] ) ]"
    })
    @Order(2)
    void Should_throw_error_if_opening_parenthesis_is_missing(final String openingBracket,
                                                              final String closingBracket,
                                                              final String source) {
        final SourceLine sourceLine = new SourceLine("module1", 5, List.of(source.split(" ")));

        final JavammLineSyntaxError error = assertThrows(JavammLineSyntaxError.class, () ->
            getTokensBetweenFirstAndLastBrackets(openingBracket, closingBracket, sourceLine, true));
        assertEquals("Syntax error in 'module1' [Line: 5]: Missing " + openingBracket, error.getMessage());
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
        // Operations and functions
        "(; );      if ( a < 4 {",
        "(; );      if ( ( a < 4 ) {",
        "(; );      if ( ( a + 4 ) * 5 < ( b - 6 / ( - c ) ) {",
        "(; );      function1 ( {",
        "(; );      function1 ( ( ) {",
        // Array declaration with values
        "{; };      var a = {",
        "{; };      var a = { 5, 6, 7",
        "{; };      var a = { { 5 , { 6, 7 } }",
        "{; };      var a = { { 6, { 7 } , 3 , { { { } } } }",
        // Empty array declaration
        "[; ];      var a = array [",
        "[; ];      var a = array [ 4 + a",
        "[; ];      var a = array [ b [ 4 + a [ ar [ 0 ] ] ]",
        "[; ];      var a = array [ 4 * sum ( 3 , a [ 5 ) ]"
    })
    @Order(3)
    void Should_throw_error_if_closing_parenthesis_is_missing(final String openingBracket,
                                                              final String closingBracket,
                                                              final String source) {
        final SourceLine sourceLine = new SourceLine("module1", 5, List.of(source.split(" ")));

        final JavammLineSyntaxError error = assertThrows(JavammLineSyntaxError.class, () ->
            getTokensBetweenFirstAndLastBrackets(openingBracket, closingBracket, sourceLine, true));
        assertEquals("Syntax error in 'module1' [Line: 5]: Missing " + closingBracket, error.getMessage());
    }

    @ParameterizedTest
    @CsvSource({
        "(,     ),      if ( )",
        "(,     ),      for ( )",
        "[,     ],      var a = array [ ]",
        "[,     ],      a [ ] = 5"
    })
    @Order(4)
    void Should_throw_error_if_the_expression_between_brackets_is_empty(final String openingBracket,
                                                                        final String closingBracket,
                                                                        final String source) {
        final SourceLine sourceLine = new SourceLine("module1", 5, List.of(source.split(" ")));
        final JavammLineSyntaxError error = assertThrows(JavammLineSyntaxError.class, () ->
            getTokensBetweenFirstAndLastBrackets(openingBracket, closingBracket, sourceLine, false));
        assertEquals(format(
            "Syntax error in 'module1' [Line: 5]: An expression is expected between %s and %s",
            openingBracket, closingBracket),
            error.getMessage());
    }
}