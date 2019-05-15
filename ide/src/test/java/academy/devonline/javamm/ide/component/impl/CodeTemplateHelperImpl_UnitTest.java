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

import academy.devonline.javamm.ide.component.CodeTemplateHelper;
import academy.devonline.javamm.ide.component.CodeTemplateStorage;
import academy.devonline.javamm.ide.model.CodeTemplate;
import org.fxmisc.richtext.CodeArea;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static academy.devonline.javamm.ide.model.CodeTemplate.CURSOR;
import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CodeTemplateHelperImpl_UnitTest {

    @Mock
    private CodeTemplateStorage codeTemplateStorage;

    @Mock
    private CodeArea codeArea;

    private CodeTemplateHelper codeTemplateHelper;

    @BeforeEach
    void beforeEach() {
        codeTemplateHelper = new CodeTemplateHelperImpl(codeTemplateStorage);
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "",
        " ",
        "\t",
        "    "
    })
    void Should_return_false_and_ignore_getting_a_template_if_the_current_line_is_blank(final String currentLine) {
        when(codeArea.getCurrentParagraph()).thenReturn(1);
        when(codeArea.getText(0)).thenReturn(currentLine);

        assertFalse(codeTemplateHelper.insertCodeTemplateToCaretPosition(codeArea));

        verify(codeTemplateStorage, never()).getTemplate(anyString());
    }

    @ParameterizedTest
    @ValueSource(strings = {
        // @see academy.devonline.javamm.code.syntax.Delimiters.IGNORED_DELIMITERS
        "var ",
        "var\u00A0",
        "var\t",
        "var\r",
        "var\n"
    })
    void Should_return_false_and_ignore_getting_a_template_if_the_last_token_is_one_of_IGNORED_DELIMITERS(
        final String currentLine) {
        when(codeArea.getCurrentParagraph()).thenReturn(1);
        when(codeArea.getText(0)).thenReturn(currentLine);

        assertFalse(codeTemplateHelper.insertCodeTemplateToCaretPosition(codeArea));

        verify(codeTemplateStorage, never()).getTemplate(anyString());
    }

    @Test
    void Should_return_false_if_template_not_found() {
        when(codeArea.getCurrentParagraph()).thenReturn(1);
        when(codeArea.getText(0)).thenReturn("test");
        when(codeTemplateStorage.getTemplate("test")).thenReturn(Optional.empty());

        assertFalse(codeTemplateHelper.insertCodeTemplateToCaretPosition(codeArea));
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "n",
        "var a = n",
        "'Hello ' + n",
        "\"Hello \" + n",
        "a +'Hello ' + n",
        "a + \"Hello \" + n"
    })
    void Should_return_true_and_replace_the_text_without_moving_of_the_caret(final String currentLine) {
        when(codeArea.getCurrentParagraph()).thenReturn(1);
        when(codeArea.getText(0)).thenReturn(currentLine);
        when(codeTemplateStorage.getTemplate("n")).thenReturn(Optional.of(new CodeTemplate("null")));
        when(codeArea.getCaretPosition()).thenReturn(2);

        assertTrue(codeTemplateHelper.insertCodeTemplateToCaretPosition(codeArea));

        verify(codeArea).replaceText(0, 2, "null");
        verify(codeArea, never()).moveTo(anyInt());
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "v",
        "for (v",
        "'Hello ' + v",
        "\"Hello \" + v",
        "a +'Hello ' + v",
        "a + \"Hello \" + v"
    })
    void Should_return_true_replace_the_text_and_move_the_caret(final String currentLine) {
        final CodeTemplate codeTemplate = new CodeTemplate(format("var %s = ", CURSOR));
        when(codeArea.getCurrentParagraph()).thenReturn(1);
        when(codeArea.getText(0)).thenReturn(currentLine);
        when(codeTemplateStorage.getTemplate("v")).thenReturn(Optional.of(codeTemplate));
        when(codeArea.getCaretPosition()).thenReturn(2);

        assertTrue(codeTemplateHelper.insertCodeTemplateToCaretPosition(codeArea));

        verify(codeArea).replaceText(0, 2, "var  =");
        verify(codeArea).moveTo(4);
    }
}