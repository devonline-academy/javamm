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

import java.util.Optional;

import static academy.devonline.javamm.code.syntax.Delimiters.STRING_DELIMITERS;
import static academy.devonline.javamm.code.syntax.SyntaxUtils.isLatinLetter;
import static academy.devonline.javamm.ide.model.CodeTemplate.CURSOR;
import static academy.devonline.javamm.ide.util.TabReplaceUtils.getTabCount;
import static java.util.Objects.requireNonNull;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
public class CodeTemplateHelperImpl implements CodeTemplateHelper {

    private final CodeTemplateStorage codeTemplateStorage;

    CodeTemplateHelperImpl(final CodeTemplateStorage codeTemplateStorage) {
        this.codeTemplateStorage = requireNonNull(codeTemplateStorage);
    }

    @Override
    public boolean insertCodeTemplateToCaretPosition(final CodeArea codeArea) {
        final String currentLine = codeArea.getText(codeArea.getCurrentParagraph() - 1);
        if (!currentLine.isBlank()) {
            final String lastToken = getLastToken(currentLine);
            if (!lastToken.isEmpty()) {
                final Optional<CodeTemplate> operationTemplate = codeTemplateStorage.getTemplate(lastToken);
                if (operationTemplate.isPresent()) {
                    addCodeTemplate(codeArea, currentLine, lastToken, operationTemplate.get());
                    return true;
                }
            }
        }
        return false;
    }

    private String getLastToken(final String line) {
        final String tokens = getSubStringAfterLastStringConstantIfFound(line);
        final StringBuilder sb = new StringBuilder();
        for (int i = tokens.length() - 1; i >= 0; i--) {
            final char ch = tokens.charAt(i);
            if (isLatinLetter(ch)) {
                sb.insert(0, ch);
            } else {
                break;
            }
        }
        return sb.toString();
    }

    private String getSubStringAfterLastStringConstantIfFound(final String line) {
        final StringBuilder tokenBuilder = new StringBuilder();
        boolean stringConstantStarted = false;
        for (int i = 0; i < line.length(); i++) {
            final char ch = line.charAt(i);
            if (STRING_DELIMITERS.contains(ch)) {
                if (stringConstantStarted) {
                    stringConstantStarted = false;
                    if (tokenBuilder.length() > 0) {
                        tokenBuilder.delete(0, tokenBuilder.length() - 1);
                    }
                } else {
                    stringConstantStarted = true;
                }
            } else if (!stringConstantStarted) {
                tokenBuilder.append(ch);
            }
        }
        return tokenBuilder.toString();
    }

    private void addCodeTemplate(final CodeArea codeArea,
                                 final String currentLine,
                                 final String lastToken,
                                 final CodeTemplate codeTemplate) {
        final int caretPosition = codeArea.getCaretPosition();
        final int startReplacePosition = caretPosition - lastToken.length() - 1;
        final String code = codeTemplate.getFormattedCode(getTabCount(currentLine)).trim();
        final int index = code.indexOf(CURSOR);
        if (index != -1) {
            codeArea.replaceText(startReplacePosition, caretPosition, code.replace(CURSOR, ""));
            codeArea.moveTo(startReplacePosition + index);
        } else {
            codeArea.replaceText(startReplacePosition, caretPosition, code);
        }
    }
}
