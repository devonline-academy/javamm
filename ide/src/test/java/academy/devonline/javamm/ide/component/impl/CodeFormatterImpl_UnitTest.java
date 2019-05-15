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

package academy.devonline.javamm.ide.component.impl;

import academy.devonline.javamm.ide.component.CodeFormatter;
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

import static academy.devonline.javamm.code.syntax.Delimiters.trim;
import static academy.devonline.javamm.ide.util.TabReplaceUtils.replaceTabulations;
import static java.util.List.of;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 * @see academy.devonline.javamm.code.syntax.Delimiters -> IGNORED_DELIMITERS
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CodeFormatterImpl_UnitTest {

    private static final String TABULATION = replaceTabulations("\t");

    private final CodeFormatter codeFormatter = new CodeFormatterImpl();

    @Test
    @Order(1)
    void Should_return_unmodifiable_list() {
        assertEquals(Collections.unmodifiableList(of()).getClass(), codeFormatter.getFormattedCode(of()).getClass());
    }

    @Test
    @Order(2)
    void Should_return_empty_list_if_source_code_list_is_empty() {
        assertEquals(of(), codeFormatter.getFormattedCode(of()));
    }

    @Test
    @Order(3)
    void Should_replace_tabulation_by_TABULATION() {
        final List<String> actual = codeFormatter.getFormattedCode(List.of(
            "function main() {",
            "\tprintln('hello')",
            "}"
        ));
        assertFalse(String.join("\n", actual).contains("\t"));
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "line1\nline2",
        "line1 \nline2",
        "line1 \nline2 ",
        "line1\u00A0\u00A0\nline2\u00A0\u00A0",
        "      line1        \n       line2         ",
        "line1\t\nline2\t",
        "\tline1\t\n\tline2\t",
        "\tline1\t\t\t\n\t\t\tline2\t\t\t",
        "line1\r\nline2",
        "line1\r\nline2\r",
    })
    @Order(4)
    void Should_ignore_all_Delimiters_IGNORED_DELIMITERS(final String sourceCode) {
        final List<String> expected = List.of(
            "line1",
            "line2"
        );
        assertEquals(expected, codeFormatter.getFormattedCode(Arrays.asList(sourceCode.split("\n"))));
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "line1\n\nline2",
        "line1\n\n\nline2",
        "line1\n\n\n\nline2",

        "line1\n\t\nline2",
        "line1\n\t\n\t\nline2",
        "line1\n\t\n\t\n\t\nline2",

        "line1\n  \nline2",
        "line1\n  \n  \nline2",
        "line1\n  \n  \n  \nline2"
    })
    @Order(5)
    void Should_return_not_more_than_one_empty_line_between_rows_with_code(final String sourceCode) {
        final List<String> expected = List.of(
            "line1",
            "",
            "line2"
        );
        assertEquals(expected, codeFormatter.getFormattedCode(Arrays.asList(sourceCode.split("\n"))));
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "\nline1\nline2\n",
        "\n\n\nline1\nline2\n\n\n",
        "\n\n\nline1\nline2\n\n\n",

        "\n\t\nline1\nline2\n\n\n",
        "\n\t\n\t\nline1\nline2\n\n\n",
        "\n\t\n\t\n\t\nline1\nline2\n\n\n",

        "\n  \nline1\nline2\n\n\n\n\n\n\n\n\n",
        "\n  \n  \nline1\nline2\n\n\n\n\n\n\n",
        "\n  \n  \n  \nline1\nline2"
    })
    @Order(5)
    void Should_return_not_more_than_one_empty_line_before_first_row_with_code(final String sourceCode) {
        final List<String> expected = List.of(
            "",
            "line1",
            "line2"
        );
        assertEquals(expected, codeFormatter.getFormattedCode(Arrays.asList(sourceCode.split("\n"))));
    }

    @ParameterizedTest
    @ValueSource(strings = {
        " var a = 1 + 2 * 5 / 8 ",
        "\tvar\ta\t=\t1\t+\t2\t*\t5\t/\t8\t",
        "var a=1+2*5/8",
        "    var a=1   +      2     *  5/ 8            ",
        "\u00A0\u00A0var\u00A0a=1\u00A0\u00A0\u00A0+\u00A02\u00A0*\u00A0\u00A05/\u00A08\u00A0",
        "\t\tvar a = 1+2\t* 5   /\u00A08    "
    })
    @Order(6)
    void Should_trim_and_add_only_one_space_between_all_tokens(final String row) {
        assertEquals("var a = 1 + 2 * 5 / 8", codeFormatter.getFormattedCode(List.of(row)).get(0));
    }

    @ParameterizedTest
    @CsvSource(delimiter = '.', value = {
        ").     a ).     a)",
        "[.     a [.     a[",
        "].     a ].     a]",
        ",.     a ,.     a,",
        ";.     a ;.     a;",
        ":.     a :.     a:"
    })
    @Order(7)
    void Exception1_Should_not_add_empty_space_before_the_following_tokens(final String token,
                                                                           final String row,
                                                                           final String expectedResult) {
        assertEquals(expectedResult, codeFormatter.getFormattedCode(List.of(row)).get(0),
            "Expected no empty space before token: " + token);
    }

    @ParameterizedTest
    @CsvSource(delimiter = '.', value = {
        "(.     ( a.     (a",
        "[.     [ a.     [a"
    })
    @Order(8)
    void Exception2_Should_not_add_empty_space_after_the_following_tokens(final String token,
                                                                          final String row,
                                                                          final String expectedResult) {
        assertEquals(expectedResult, codeFormatter.getFormattedCode(List.of(row)).get(0),
            "Expected no empty space after token: " + token);
    }

    @Test
    @Order(9)
    void Should_add_TABULATION_for_code_inside_any_block() {
        final List<String> expected = of(
            "{",
            TABULATION + "{",
            TABULATION + TABULATION + "{",
            TABULATION + TABULATION + TABULATION + "println",
            TABULATION + TABULATION + "}",
            TABULATION + "}",
            "}"
        );
        final List<String> actual = codeFormatter.getFormattedCode(of(
            "{",
            "{",
            "{",
            "println",
            "}",
            "}",
            "}"
        ));
        assertEquals(expected, actual);
    }

    @Test
    @Order(10)
    void Should_normalize_TABULATION_for_code_inside_any_block() {
        final List<String> expected = of(
            "{",
            TABULATION + "{",
            TABULATION + TABULATION + "{",
            TABULATION + TABULATION + TABULATION + "println",
            TABULATION + TABULATION + "}",
            TABULATION + "}",
            "}"
        );
        final List<String> actual = codeFormatter.getFormattedCode(of(
            "   {",
            "\t\t\t{",
            "\u00A0\u00A0\u00A0\u00A0{\u00A0\u00A0\u00A0\u00A0",
            "println" + TABULATION + TABULATION + TABULATION,
            "}" + TABULATION + TABULATION + TABULATION,
            "}",
            TABULATION + TABULATION + TABULATION + "}"
        ));
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "   // comment    ",
        "  // var \u00A0 a = 3 + 4      ",
        "\u00A0\u00A0\u00A0\u00A0// var a=3+4\u00A0\u00A0\u00A0\u00A0",
        "\t\t// var a = ( 3 + 4 ) + array [ a [ 4 + f ] ]\r\r"
    })
    @Order(11)
    void Should_ignore_formatting_for_line_comments_except_trim(final String row) {
        assertEquals(trim(row), codeFormatter.getFormattedCode(List.of(row)).get(0));
    }

    @Test
    @Order(12)
    void Should_ignore_formatting_for_line_comments_except_trim_and_block_formatting() {
        final List<String> expected = of(
            "{",
            TABULATION + "// comment",
            TABULATION + "var a = 6 // var \u00A0 a = 3 + 4",
            TABULATION + "// var \u00A0 a = 3 + 4",
            TABULATION + "a = 5 // var a = ( 3 + 4 ) + array [ a [ 4 + f ] ]",
            "}"
        );
        final List<String> actual = codeFormatter.getFormattedCode(of(
            "{",
            "   // comment    ",
            "  var a=6// var \u00A0 a = 3 + 4      ",
            "\u00A0\u00A0\u00A0\u00A0// var \u00A0 a = 3 + 4 ",
            "a = 5\t\t// var a = ( 3 + 4 ) + array [ a [ 4 + f ] ]\r\r",
            "}"
        ));
        assertEquals(expected, actual);
    }

    @Test
    @Order(13)
    void Should_ignore_of_formatting_for_multiline_comments_except_trim_and_block_formatting() {
        final List<String> expected = of(
            "{",
            TABULATION + "/* multi                  line",
            TABULATION + "** comment",
            TABULATION + "** started\u00A0\u00A0\u00A0\u00A0!",
            TABULATION + "*/",
            "}"
        );
        final List<String> actual = codeFormatter.getFormattedCode(of(
            "{",
            "/* multi                  line",
            "   ** comment                ",
            "\t\t\t** started\u00A0\u00A0\u00A0\u00A0!  ",
            "\u00A0\u00A0*/",
            "}"
        ));
        assertEquals(expected, actual);
    }

    @Test
    @Order(14)
    void Should_ignore_of_formatting_for_multiline_comments_inside_code() {
        final List<String> expected = of(
            "{",
            TABULATION + "for(var i = 0; /* // test    comment */ i < 9; /*\u00A0test\u00A0comment\u00A0*/ i++) /*test comment*/ {",
            TABULATION + "}",
            "}"
        );
        final List<String> actual = codeFormatter.getFormattedCode(of(
            "{",
            "for ( var i=0;/* // test    comment */ i<9 ; /*\u00A0test\u00A0comment\u00A0*/ i++ ) /*test comment*/ {",
            "}",
            "}"
        ));
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "'  var a=1 +   2 * 5 / 8  '",
        "'  var a=1 +   2 * 5 / 8",
        "\"  var a = 1 + 2*5   / 8  \"",
        "\"  var a = 1 + 2*5   / 8",
        "'  var a=1+2*5/8'",
        "'  var a=1+2*5/8",
        "\"  var a=1+2*5/8\"",
        "\"  var a=1+2*5/8"
    })
    @Order(15)
    void Should_ignore_formatting_for_string_constants_with_and_without_closing_quotation_mark(final String row) {
        assertEquals(row, codeFormatter.getFormattedCode(List.of(row)).get(0));
    }

    @Test
    @Order(16)
    void Should_correct_differ_block_of_code_from_array_with_value_declaration() {
        final List<String> expected = of(
            "{",
            TABULATION + "var a = { 2, c, 5 }",
            TABULATION + "if(c > 2) {",
            TABULATION + TABULATION + "var b = { 2, a, 5 }",
            TABULATION + "}",
            "}"
        );
        final List<String> actual = codeFormatter.getFormattedCode(of(
            "{",
            "var a={2,c,5}",
            "if(c > 2){",
            "var b={2,a,5}",
            "}",
            "}"
        ));
        assertEquals(expected, actual);
    }

    @Test
    @Order(17)
    void Should_not_throw_error_if_opening_curly_brace_is_missing() {
        final List<String> expected = of(
            "{",
            TABULATION + "var a = { 2, c, 5 }",
            "}",
            "}",
            "}"
        );
        final List<String> actual = assertDoesNotThrow(() -> codeFormatter.getFormattedCode(of(
            "{",
            "var a={2,c,5}",
            "\t\t}",
            "    }",
            "\u00A0\u00A0}"
        )));
        assertEquals(expected, actual);
    }

    @Test
    @Order(18)
    void Should_not_throw_error_if_closing_curly_brace_is_missing() {
        final List<String> expected = of(
            "{",
            TABULATION + "{",
            TABULATION + TABULATION + "{",
            TABULATION + TABULATION + TABULATION + "println a +",
            TABULATION + TABULATION + "}"
        );
        final List<String> actual = assertDoesNotThrow(() -> codeFormatter.getFormattedCode(of(
            "{",
            "{",
            "{",
            "println a +",
            "}"
        )));
        assertEquals(expected, actual);
    }
}