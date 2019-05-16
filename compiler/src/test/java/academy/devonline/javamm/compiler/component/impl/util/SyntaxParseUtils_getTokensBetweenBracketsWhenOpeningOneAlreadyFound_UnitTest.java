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

import java.util.Iterator;
import java.util.List;

import static academy.devonline.javamm.compiler.component.impl.util.SyntaxParseUtils.getTokensBetweenBracketsWhenOpeningOneAlreadyFound;
import static java.lang.String.format;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyIterator;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SyntaxParseUtils_getTokensBetweenBracketsWhenOpeningOneAlreadyFound_UnitTest {

    private final SourceLine sourceLine = new SourceLine("module1", 5, List.of());

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
        "(;     );      ",
        "(;     );      1",
        "(;     );      2 + 5",
        "(;     );      2 + ( 5 + 6 )",
        "[;     ];      ",
        "[;     ];      1",
        "[;     ];      2 + 5",
        "[;     ];      2 + arr [ 1 + 5 ]",
    })
    @Order(1)
    void Should_throw_error_if_closing_bracket_is_missing(final String openingBracket,
                                                          final String closingBracket,
                                                          final String source) {
        final Iterator<String> iterator = toIterator(source);
        final JavammLineSyntaxError error = assertThrows(JavammLineSyntaxError.class, () ->
            getTokensBetweenBracketsWhenOpeningOneAlreadyFound(openingBracket, closingBracket, iterator, sourceLine, true));
        assertEquals("Syntax error in 'module1' [Line: 5]: Missing " + closingBracket, error.getMessage());
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
        "(;     );      )",
        "[;     ];      ]",
    })
    @Order(2)
    void Should_throw_error_if_the_expression_between_brackets_is_empty(final String openingBracket,
                                                                        final String closingBracket,
                                                                        final String source) {
        final Iterator<String> iterator = toIterator(source);
        final JavammLineSyntaxError error = assertThrows(JavammLineSyntaxError.class, () ->
            getTokensBetweenBracketsWhenOpeningOneAlreadyFound(openingBracket, closingBracket, iterator, sourceLine, false));
        assertEquals(format(
            "Syntax error in 'module1' [Line: 5]: An expression is expected between '%s' and '%s'",
            openingBracket, closingBracket),
            error.getMessage());
    }

    private Iterator<String> toIterator(final String source) {
        return source == null ? emptyIterator() : asList(source.split(" ")).iterator();
    }
}