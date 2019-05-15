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

package academy.devonline.javamm.ide.util;

import javafx.scene.input.Clipboard;
import javafx.scene.input.KeyCode;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.wellbehaved.event.EventPattern;
import org.fxmisc.wellbehaved.event.InputMap;
import org.fxmisc.wellbehaved.event.Nodes;

import java.util.Optional;
import java.util.regex.Pattern;

import static javafx.scene.input.KeyCombination.SHORTCUT_DOWN;

/**
 * https://bugs.openjdk.java.net/browse/JDK-8130738
 * See: com.sun.javafx.text.PrismTextLayout.getTabAdvance()
 *
 * @author devonline
 * @link http://devonline.academy/javamm
 */
public final class TabReplaceUtils {

    private static final String TABULATION = " ".repeat(4);

    private static final String TAB_REGEXP_PATTERN = Pattern.quote(TABULATION);

    private static final int TAB_LENGTH = TABULATION.length();

    private TabReplaceUtils() {
    }

    public static void initCodeAreaTabFixer(final CodeArea codeArea) {
        Nodes.addInputMap(codeArea, InputMap.consume(
            EventPattern.keyPressed(KeyCode.TAB), e ->
                codeArea.replaceSelection(TABULATION)
        ));
        Nodes.addInputMap(codeArea, InputMap.consume(
            EventPattern.keyPressed(KeyCode.V, SHORTCUT_DOWN), e ->
                Optional.ofNullable(Clipboard.getSystemClipboard().getString())
                    .ifPresent(text -> codeArea.replaceSelection(replaceTabulations(text)))
        ));
    }

    public static String replaceTabulations(final String source) {
        return source.replace("\t", TABULATION);
    }

    public static int getTabCount(final String line) {
        return (line.length() - line.replaceAll(TAB_REGEXP_PATTERN, "").length()) / TAB_LENGTH;
    }

    public static String getLineWithTabs(final String trimmedLine, final int tabCount) {
        return TABULATION.repeat(tabCount) + trimmedLine;
    }
}
