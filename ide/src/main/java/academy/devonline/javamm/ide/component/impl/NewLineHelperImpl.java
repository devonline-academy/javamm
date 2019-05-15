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
import academy.devonline.javamm.ide.model.CodeTemplate;
import org.fxmisc.richtext.CodeArea;

import static academy.devonline.javamm.ide.model.CodeTemplate.CURSOR;
import static academy.devonline.javamm.ide.util.TabReplaceUtils.getLineWithTabs;
import static academy.devonline.javamm.ide.util.TabReplaceUtils.getTabCount;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
public class NewLineHelperImpl implements NewLineHelper {

    private final CodeTemplate codeTemplate = new CodeTemplate("\t" + CURSOR, "");

    @Override
    public void insertNewLine(final CodeArea codeArea) {
        final String currentLine = codeArea.getText(codeArea.getCurrentParagraph() - 1);
        final int caretPosition = codeArea.getCaretPosition();
        final int tabCount = getTabCount(currentLine);
        if (currentLine.endsWith("{")) {
            final String nextLineCode = "\n" + codeTemplate.getFormattedCode(tabCount);
            final int index = nextLineCode.indexOf(CURSOR);
            codeArea.replaceText(caretPosition - 1, caretPosition, nextLineCode.replace(CURSOR, ""));
            codeArea.moveTo(caretPosition - 1 + index);
        } else {
            final String nextLineCode = "\n" + getLineWithTabs("", tabCount);
            codeArea.replaceText(caretPosition - 1, caretPosition, nextLineCode);
        }
    }
}
