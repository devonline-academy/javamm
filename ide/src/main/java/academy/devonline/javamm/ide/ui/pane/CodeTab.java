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
import academy.devonline.javamm.ide.model.StringSourceCode;
import javafx.scene.control.Tab;

import static java.util.Objects.requireNonNull;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
final class CodeTab extends Tab {

    private String moduleName;

    CodeTab(final String moduleName,
            final CodeEditorPane content) {
        super(requireNonNull(moduleName), requireNonNull(content));
        this.moduleName = moduleName;
    }

    public String getModuleName() {
        return moduleName;
    }

    SourceCode getSourceCode() {
        return new StringSourceCode(moduleName, getCodeEditorPane().getCodeLines());
    }

    private CodeEditorPane getCodeEditorPane() {
        return (CodeEditorPane) getContent();
    }
}
