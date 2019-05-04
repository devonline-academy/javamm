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

import academy.devonline.javamm.code.fragment.SourceCode;
import academy.devonline.javamm.code.fragment.SourceLine;
import academy.devonline.javamm.compiler.component.SourceLineReader;
import academy.devonline.javamm.compiler.component.TokenParser;
import academy.devonline.javamm.compiler.model.TokenParserResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.quality.Strictness.LENIENT;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = LENIENT)
class SourceLineReaderImpl_UnitTest {

    private static final String MODULE_NAME = "test";

    @Mock
    private SourceCode sourceCode;

    @Mock
    private TokenParser tokenParser;

    private SourceLineReader sourceLineReader;

    @BeforeEach
    void beforeEach() {
        sourceLineReader = new SourceLineReaderImpl(tokenParser);
    }

    @Test
    @Order(1)
    void Should_return_not_null_result() {
        assertNotNull(sourceLineReader.read(sourceCode));
    }

    /*@Test
    @Order(2)
    void Should_return_the_unmodifiable_list() {
        final List<SourceLine> sourceLines = sourceLineReader.read(sourceCode);

        assertThrows(UnsupportedOperationException.class, () -> sourceLines.add(null));
        assertThrows(UnsupportedOperationException.class, () -> sourceLines.set(0, null));
        assertThrows(UnsupportedOperationException.class, () -> sourceLines.add(EMPTY_SOURCE_LINE));
        assertThrows(UnsupportedOperationException.class, () -> sourceLines.add(0, null));
        assertThrows(UnsupportedOperationException.class, () -> sourceLines.addAll(List.of(EMPTY_SOURCE_LINE)));
        assertThrows(UnsupportedOperationException.class, () -> sourceLines.addAll(0, List.of(EMPTY_SOURCE_LINE)));

        assertThrows(UnsupportedOperationException.class, () -> sourceLines.set(0, EMPTY_SOURCE_LINE));
        assertThrows(UnsupportedOperationException.class, () -> sourceLines.remove(0));
        assertThrows(UnsupportedOperationException.class, () -> sourceLines.remove(EMPTY_SOURCE_LINE));
        assertThrows(UnsupportedOperationException.class, () -> sourceLines.removeAll(List.of()));
        assertThrows(UnsupportedOperationException.class, () -> sourceLines.retainAll(List.of()));

        assertThrows(UnsupportedOperationException.class, () -> sourceLines.iterator().remove());
        assertThrows(UnsupportedOperationException.class, () -> sourceLines.listIterator().add(EMPTY_SOURCE_LINE));
        assertThrows(UnsupportedOperationException.class, () -> sourceLines.listIterator().remove());
        assertThrows(UnsupportedOperationException.class, () -> sourceLines.listIterator().set(EMPTY_SOURCE_LINE));
        assertThrows(UnsupportedOperationException.class, () -> sourceLines.listIterator(0).add(EMPTY_SOURCE_LINE));
        assertThrows(UnsupportedOperationException.class, () -> sourceLines.listIterator(0).remove());
        assertThrows(UnsupportedOperationException.class, () -> sourceLines.listIterator(0).set(EMPTY_SOURCE_LINE));

        assertThrows(UnsupportedOperationException.class, () -> sourceLines.removeIf(sourceLine -> false));
        assertThrows(UnsupportedOperationException.class, () -> sourceLines.replaceAll(UnaryOperator.identity()));
        assertThrows(UnsupportedOperationException.class, () -> sourceLines.sort((o1, o2) -> 0));
        assertThrows(UnsupportedOperationException.class, sourceLines::clear);
        // TODO Add verifications for new edit methods
    }*/

    @Test
    @Order(3)
    void Should_return_the_Collections_unmodifiable_list() {
        final List<SourceLine> sourceLines = sourceLineReader.read(sourceCode);

        assertEquals(Collections.unmodifiableList(List.of()).getClass(), sourceLines.getClass());
    }

    @Test
    @Order(4)
    void Should_return_the_valid_source_line_numbers() {
        when(sourceCode.getModuleName()).thenReturn(MODULE_NAME);
        when(sourceCode.getLines()).thenReturn(List.of(
            "",
            "// comment",
            "var a"
        ));
        when(tokenParser.parseLine("", false))
            .thenReturn(new TokenParserResult(false));
        when(tokenParser.parseLine("// comment", false))
            .thenReturn(new TokenParserResult(false));
        when(tokenParser.parseLine("var a", false))
            .thenReturn(new TokenParserResult(List.of("var", "a"), false));

        final List<SourceLine> sourceLines = sourceLineReader.read(sourceCode);

        assertEquals(1, sourceLines.size());
        assertEquals(new SourceLine(MODULE_NAME, 3, List.of("var", "a")), sourceLines.get(0));
        verify(tokenParser, times(3)).parseLine(anyString(), eq(false));
        verify(sourceCode, atLeastOnce()).getModuleName();
    }

    @Test
    @Order(5)
    void Should_support_the_multiline_comments_correctly() {
        when(sourceCode.getModuleName()).thenReturn(MODULE_NAME);
        when(sourceCode.getLines()).thenReturn(List.of(
            "/* */",
            "var a /* comment */ = 5",
            "a = 4 /* multiline",
            " comment ",
            " example */ println (a)"
        ));
        when(tokenParser.parseLine("/* */", false))
            .thenReturn(new TokenParserResult(false));
        when(tokenParser.parseLine("var a /* comment */ = 5", false))
            .thenReturn(new TokenParserResult(List.of("var", "a", "=", "5"), false));
        when(tokenParser.parseLine("a = 4 /* multiline", false))
            .thenReturn(new TokenParserResult(List.of("a", "=", "4"), true));
        when(tokenParser.parseLine(" comment ", true))
            .thenReturn(new TokenParserResult(true));
        when(tokenParser.parseLine(" example */ println (a)", true))
            .thenReturn(new TokenParserResult(List.of("println", "(", "a", ")"), false));

        final List<SourceLine> sourceLines = sourceLineReader.read(sourceCode);

        assertEquals(3, sourceLines.size());
        assertEquals(new SourceLine(MODULE_NAME, 2, List.of("var", "a", "=", "5")), sourceLines.get(0));
        assertEquals(new SourceLine(MODULE_NAME, 3, List.of("a", "=", "4")), sourceLines.get(1));
        assertEquals(new SourceLine(MODULE_NAME, 5, List.of("println", "(", "a", ")")), sourceLines.get(2));
        verify(tokenParser, times(5)).parseLine(anyString(), anyBoolean());
        verify(sourceCode, atLeastOnce()).getModuleName();
    }
}