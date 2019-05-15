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

import academy.devonline.javamm.ide.component.NewLineHelper;
import org.fxmisc.richtext.CodeArea;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static academy.devonline.javamm.ide.util.TabReplaceUtils.replaceTabulations;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class NewLineHelperImpl_UnitTest {

    private static final String TAB = replaceTabulations("\t");

    @Mock
    private CodeArea codeArea;

    private NewLineHelper newLineHelper;

    @BeforeEach
    void beforeEach() {
        newLineHelper = new NewLineHelperImpl();
    }

    @Test
    void Should_insert_a_new_line_add_a_tab_between_curly_braces_and_move_caret_inside_block() {
        when(codeArea.getCurrentParagraph()).thenReturn(1);
        when(codeArea.getText(0)).thenReturn("{");
        when(codeArea.getCaretPosition()).thenReturn(1);

        newLineHelper.insertNewLine(codeArea);

        verify(codeArea).replaceText(0, 1, "\n" + TAB + "\n");
        verify(codeArea).moveTo(5);
    }

    @Test
    void Should_insert_a_new_line_and_add_a_tabulation_shift() {
        when(codeArea.getCurrentParagraph()).thenReturn(1);
        when(codeArea.getText(0)).thenReturn(TAB);
        when(codeArea.getCaretPosition()).thenReturn(1);

        newLineHelper.insertNewLine(codeArea);

        verify(codeArea).replaceText(0, 1, "\n" + TAB);
    }
}