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

package academy.devonline.javamm.compiler.component.impl;

import academy.devonline.javamm.compiler.component.TokenParser;
import academy.devonline.javamm.compiler.model.TokenParserResult;
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
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.stream.Stream;

import static java.lang.String.format;
import static java.util.function.Function.identity;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.params.provider.Arguments.arguments;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TokenParserImpl_UnitTest {

    private final TokenParser tokenParser = new TokenParserImpl();

    @ParameterizedTest
    @ValueSource(strings = {
        "",
        "\u00A0\u00A0",
        " ",
        "     ",
        "\r\r\r",
        "\t",
        "\t\t\t",
        "//",
        "//some text",
        "//1 + 1 /* 5 - 7 */",
        "//1 + 1 */",
        "//1 + 1 */ /* ",
        "//1 + 1 // 5 - 7"
    })
    @Order(1)
    void Should_return_the_empty_result_for_ignored_delimiters_and_single_line_comments(final String value) {
        final boolean multilineCommentStarted = false;
        final TokenParserResult result = tokenParser.parseLine(value, multilineCommentStarted);

        assertEquals(List.of(), result.getTokens());
        // Important if line comment inside multiline comment
        assertEquals(multilineCommentStarted, result.isMultilineCommentStarted());
    }

    @ParameterizedTest
    @CsvSource( {
        "/*,       false",
        "1 + 1,    true",
        "/* 1 + 1, true",
        "/* 1 + 1, false"
    })
    @Order(3)
    void Should_return_result_that_multi_line_comment_is_started(final String line,
                                                                 final boolean multilineCommentStarted) {
        final TokenParserResult result = tokenParser.parseLine(line, multilineCommentStarted);

        assertEquals(List.of(), result.getTokens());
        assertTrue(result.isMultilineCommentStarted());
    }

    @ParameterizedTest
    @CsvSource( {
        "*/,         true",
        "1 + 1 */,   true",
        "/*1 + 1 */, true",
        "/*1 + 1 */, false"
    })
    @Order(4)
    void Should_return_result_that_multi_line_comment_is_completed(final String line,
                                                                   final boolean multilineCommentStarted) {
        final TokenParserResult result = tokenParser.parseLine(line, multilineCommentStarted);

        assertEquals(List.of(), result.getTokens());
        assertFalse(result.isMultilineCommentStarted());
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "'",
        "'without close quote",
        "\"",
        "\"without close quote"
    })
    @Order(5)
    void Should_not_throw_error_if_string_constant_does_not_end_with_closing_quote(final String line) {
        final TokenParserResult result = assertDoesNotThrow(() ->
            tokenParser.parseLine(line, false));

        assertEquals(List.of(line), result.getTokens());
        assertFalse(result.isMultilineCommentStarted());
    }


    @ParameterizedTest
    @CsvSource( {
        "b>>>=2,                            b >>>= 2",
        "b>>> =2,                           b >>> = 2",
        "b>> >=2,                           b >> >= 2",
        "b> >>=2,                           b > >>= 2",
        "b>> >=2,                           b >> >= 2",

        "b>>>>3,                            b >>> > 3",
        "a+++3,                             a ++ + 3",
        "a+++++3,                           a ++ ++ + 3",
        "a>>>>>3,                           a >>> >> 3",
        "a<<<<<3,                           a << << < 3",
        "a<<<<<=3,                          a << << <= 3",
        "a<<<<<<=3,                         a << << <<= 3",
        "a<<<<<==3,                         a << << <= = 3",
        "a<<<<<<==3,                        a << << <<= = 3",
        "a<<<<<===3,                        a << << <= == 3",

        "var a=b>>>>>2>> >4>>>5<<<6<<<=8,   var a = b >>> >> 2 >> > 4 >>> 5 << < 6 << <= 8"
    })
    @Order(6)
    void Should_try_to_extract_the_operator_with_bigger_length_first(final String sourceLine,
                                                                     final String expectedTokens) {
        final TokenParserResult result = tokenParser.parseLine(sourceLine, false);
        assertEquals(List.of(expectedTokens.split(" ")), result.getTokens());
    }

    @ParameterizedTest(name = "[{index}] -> [{0}] (multilineCommentStarted={1})")
    @ArgumentsSource(DifferenceSourcesButSameResultProvider.class)
    @Order(7)
    void Should_ignore_of_all_comment_types(final String sourceLine,
                                            final boolean multilineCommentStartedParameter,
                                            final TokenParserResult expectedTokenParserResult) {
        final TokenParserResult result = tokenParser.parseLine(sourceLine, multilineCommentStartedParameter);

        assertEquals(expectedTokenParserResult.getTokens(), result.getTokens());
        assertEquals(expectedTokenParserResult.isMultilineCommentStarted(), result.isMultilineCommentStarted());
    }

    @ParameterizedTest(name = "[{index}] -> {0}")
    @ArgumentsSource(ComplexExpressionProvider.class)
    @Order(8)
    void Should_split_line_using_all_supported_delimiters_correctly(final String sourceLine,
                                                                    final List<String> expectedTokens) {
        final TokenParserResult result = tokenParser.parseLine(sourceLine, false);

        assertEquals(expectedTokens, result.getTokens());
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "+", "++", "+=", "-", "--", "-=", "*", "*=", "/", "/=", "%", "%=",
        ">", ">>", ">=", ">>>", ">>=", ">>>=", "<", "<<", "<=", "<<=",
        "!", "!=", "=", "==", "&", "&&", "&=", "|", "||", "|=", "^", "^=", "~"
    })
    @Order(9)
    void Should_support_all_operator_tokens(final String token) {
        final String sourceLine = format("var a=b%sc", token);

        final TokenParserResult result = tokenParser.parseLine(sourceLine, false);
        assertEquals(List.of("var", "a", "=", "b", token, "c"), result.getTokens());
    }

    /**
     * @author devonline
     * @link http://devonline.academy/javamm
     */
    static final class DifferenceSourcesButSameResultProvider implements ArgumentsProvider {

        private final List<String> expectedTokens = List.of("var", "a", "=", "3");

        @Override
        public Stream<? extends Arguments> provideArguments(final ExtensionContext context) {
            return Stream.of(
                multilineCommentStartedSourceFalseAndResultFalse(),
                multilineCommentStartedSourceTrueAndResultFalse(),
                multilineCommentStartedSourceFalseAndResultTrue(),
                multilineCommentStartedSourceTrueAndResultTrue()
            ).flatMap(identity());
        }

        private Stream<Arguments> multilineCommentStartedSourceFalseAndResultFalse() {
            return Stream.of(
                arguments("var a = 3", false,
                    new TokenParserResult(expectedTokens, false)),
                arguments("var a = 3 // 4 - 5", false,
                    new TokenParserResult(expectedTokens, false)),
                arguments("var a = 3 /* 2 + 2 */", false,
                    new TokenParserResult(expectedTokens, false)),
                arguments("var a = 3 /* 2 + 2 */ // 4 - 5", false,
                    new TokenParserResult(expectedTokens, false)),
                arguments("var a = /* 2 + 2 */ 3", false,
                    new TokenParserResult(expectedTokens, false)),
                arguments("var a = /* 2 + 2 */ 3 // 4 - 5", false,
                    new TokenParserResult(expectedTokens, false)),
                arguments("var /* 1 + 1 */ a /* 2 + 2 */ = /* 3 + 3 */ 3 /* 4 + 4 */", false,
                    new TokenParserResult(expectedTokens, false)),
                arguments("var /* 1 + 1 */ a /* 2 + 2 */ = /* 3 + 3 */ 3 // 4 - 5", false,
                    new TokenParserResult(expectedTokens, false))
            );
        }

        private Stream<Arguments> multilineCommentStartedSourceTrueAndResultFalse() {
            return Stream.of(
                arguments("var a = 3 /* 2 + 2", false,
                    new TokenParserResult(expectedTokens, true)),
                arguments("var a = 3 /* 2 + 2 */ /* 3 + 3", false,
                    new TokenParserResult(expectedTokens, true)),
                arguments("var a = 3 /* // 2 + 2 */ /* //3 + 3", false,
                    new TokenParserResult(expectedTokens, true))
            );
        }

        private Stream<Arguments> multilineCommentStartedSourceFalseAndResultTrue() {
            return Stream.of(
                arguments("2 + 2 */ var a = 3", true,
                    new TokenParserResult(expectedTokens, false)),
                arguments("2 + 2 */ var a = 3 // 4 - 5", true,
                    new TokenParserResult(expectedTokens, false)),
                arguments("2 + 2 */ var a = 3 /* 4 - 5 */", true,
                    new TokenParserResult(expectedTokens, false)),
                arguments("// 2 + 2 */ var a = 3 /* 4 - 5 */", true,
                    new TokenParserResult(expectedTokens, false))
            );
        }

        private Stream<Arguments> multilineCommentStartedSourceTrueAndResultTrue() {
            return Stream.of(
                arguments("2 + 2 */ var a = 3 /* 3 + 3", true,
                    new TokenParserResult(expectedTokens, true)),
                arguments("2 + 2 */ /* 3 + 3 */ var a = 3 /* 4 + 4 */ /* 5 + 5", true,
                    new TokenParserResult(expectedTokens, true)),
                arguments("2 + 2 */ /* // 3 + 3 */ var a = 3 /* // 4 + 4 */ /* // 5 + 5", true,
                    new TokenParserResult(expectedTokens, true))
            );
        }
    }

    /**
     * @author devonline
     * @link http://devonline.academy/javamm
     */
    static final class ComplexExpressionProvider implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(final ExtensionContext context) {
            return Stream.of(
                // var e = c + 2 * d - 5 / a[0]
                arguments("\tvar e = c+2*d-5 / a[0]\t ",
                    List.of("var", "e", "=", "c", "+", "2", "*", "d", "-", "5", "/", "a", "[", "0", "]")),

                // println ("pr = " + (c + sum (4, d - 2) * 3 - 12 ) % a[3])
                arguments("  println (\"pr = \" + (c + sum (4, d-2) * 3-12) % a[3])",
                    List.of("println", "(", "\"pr = \"", "+", "(", "c", "+", "sum", "(", "4", ",", "d", "-",
                        "2", ")", "*", "3", "-", "12", ")", "%", "a", "[", "3", "]", ")")),

                // println ('unary = ' + (-c) + ',' + (-3) + ',' + (+c) + ',' + (++c) + ',' + (c++) + '.')
                arguments("  println ('unary = ' + (-c) + ',' + (-3) + ',' + (+c) + ',' + (++c) + ',' + (c++) + '.')",
                    List.of("println", "(", "'unary = '", "+", "(", "-", "c", ")", "+", "','", "+", "(", "-", "3", ")",
                        "+", "','", "+", "(", "+", "c", ")", "+", "','", "+", "(", "++", "c", ")", "+", "','",
                        "+", "(", "c", "++", ")", "+", "'.'", ")")),

                // var a = 'Hello ' + 2 + '5 + 678 - 6'
                arguments(" var a = 'Hello ' + 2 + '5 + 678 - 6'  \t \t",
                    List.of("var", "a", "=", "'Hello '", "+", "2", "+", "'5 + 678 - 6'")),

                // println (a[0] > ar[a[4 - a[3]] * sum (a[1], 0 - a[1])] ? a typeof array && ar typeof array : sum (parseInt (\"12\"), parseDouble (\"12.1\")))
                arguments("\tprintln (a[0] > ar[a[4 - a[3]] * sum (a[1], 0 - a[1])] ? " +
                        "a typeof array && ar typeof array : sum (parseInt (\"12\"), parseDouble (\"12.1\")))",
                    List.of("println", "(", "a", "[", "0", "]", ">", "ar", "[", "a", "[", "4", "-", "a", "[", "3", "]",
                        "]", "*", "sum", "(", "a", "[", "1", "]", ",", "0", "-", "a", "[", "1", "]", ")", "]",
                        "?", "a", "typeof", "array", "&&", "ar", "typeof", "array", ":", "sum", "(",
                        "parseInt", "(", "\"12\"", ")", ",", "parseDouble", "(", "\"12.1\"", ")", ")", ")"))
            );
        }
    }
}