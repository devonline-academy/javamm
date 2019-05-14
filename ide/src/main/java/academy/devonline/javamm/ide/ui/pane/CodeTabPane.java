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

package academy.devonline.javamm.ide.ui.pane;

import academy.devonline.javamm.code.fragment.SourceCode;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
public final class CodeTabPane extends TabPane {

    private int untitledCounter = 1;

    public CodeTabPane() {
        setTabClosingPolicy(TabClosingPolicy.ALL_TABS);
    }

    public void newCodeEditor() {
        final String tabTitle = format("Untitled-%s.javamm", untitledCounter++);
        final CodeEditorPane codeEditorPane = new CodeEditorPane();
        final Tab tab = new CodeTab(tabTitle, codeEditorPane);
        getTabs().add(tab);
        getSelectionModel().select(tab);
        codeEditorPane.requestFocus();
    }

    public List<SourceCode> getAllSourceCodes() {
        return getTabs().stream().map(t -> ((CodeTab) t).getSourceCode()).collect(Collectors.toList());
    }
}
