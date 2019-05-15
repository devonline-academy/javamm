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

import academy.devonline.javamm.ide.component.PairedTokensHelper;
import org.fxmisc.richtext.CodeArea;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PairedTokensHelperImpl_UnitTest {

    private final PairedTokensHelper pairedTokensHelper = new PairedTokensHelperImpl();

    @Mock
    private CodeArea codeArea;

    @ParameterizedTest
    @CsvSource( {
        "[,]",
        "{,}",
        "(,)",
        "\",\""
    })
    void Should_insert_paired_token_and_move_caret_between_tokens(final String character,
                                                                  final String expectedPairedChar) {
        when(codeArea.getCaretPosition()).thenReturn(1);

        assertTrue(pairedTokensHelper.isPairedToken(character));
        pairedTokensHelper.insertPairedToken(codeArea, character);

        verify(codeArea).insertText(1, expectedPairedChar);
        verify(codeArea).moveTo(1);
    }

    @Test
    void Should_insert_paired_single_quotation_mark_and_move_caret_between_tokens() {
        Should_insert_paired_token_and_move_caret_between_tokens("'", "'");
    }
}