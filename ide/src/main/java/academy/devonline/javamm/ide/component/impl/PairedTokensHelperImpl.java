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

import java.util.Map;

import static java.util.Map.entry;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
public class PairedTokensHelperImpl implements PairedTokensHelper {

    private final Map<String, String> map = Map.ofEntries(
        entry("[", "]"),
        entry("(", ")"),
        entry("{", "}"),
        entry("'", "'"),
        entry("\"", "\"")
    );

    @Override
    public boolean isPairedToken(final String character) {
        return map.containsKey(character);
    }

    @Override
    public void insertPairedToken(final CodeArea codeArea, final String character) {
        final String token = map.get(character);
        final int caretPosition = codeArea.getCaretPosition();
        codeArea.insertText(caretPosition, token);
        codeArea.moveTo(caretPosition);
    }
}
