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
import academy.devonline.javamm.ide.component.Releasable;
import academy.devonline.javamm.ide.model.StringSourceCode;
import academy.devonline.javamm.ide.ui.listener.ActionStateManager;
import academy.devonline.javamm.ide.ui.listener.TabCloseConfirmationListener;
import javafx.scene.control.Tab;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
public final class CodeTab extends Tab implements Releasable {

    private final ActionStateManager actionStateManager;

    private String moduleName;

    private boolean changed;

    CodeTab(final String moduleName,
            final CodeEditorPane content,
            final ActionStateManager actionStateManager,
            final TabCloseConfirmationListener tabCloseConfirmationListener) {
        super(requireNonNull(moduleName), requireNonNull(content));
        this.moduleName = moduleName;
        this.actionStateManager = requireNonNull(actionStateManager);

        content.setChangeListener((observable, oldValue, newValue) -> setChanged());
        setOnCloseRequest(event -> {
            if (tabCloseConfirmationListener.isTabCloseEventCancelled(this)) {
                event.consume();
            } else {
                release();
            }
        });
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

    public boolean isChanged() {
        return changed;
    }

    private void setChanged() {
        changed = true;
        if (!getText().startsWith("*")) {
            setText("*" + moduleName);
        }
        actionStateManager.setSaveActionDisable(false);
        updateUndoRedoActionState();
    }

    @Override
    public void release() {
        getCodeEditorPane().release();
    }

    public Optional<File> getSourceCodeFile() {
        return getCodeEditorPane().getSourceCodeFile();
    }

    void loadCode(final File selectedFile) throws IOException {
        getCodeEditorPane().loadCode(selectedFile);
        updateTabState(selectedFile);
    }

    public void saveChanges(final File selectedFile) throws IOException {
        getCodeEditorPane().saveCode(selectedFile);
        updateTabState(selectedFile);
    }

    private void updateTabState(final File selectedFile) {
        changed = false;
        setText(selectedFile.getName());
        moduleName = selectedFile.getName();
    }

    void requestFocus() {
        getCodeEditorPane().requestFocus();
    }

    public void undo() {
        getCodeEditorPane().getUndoActions().undo();
        updateUndoRedoActionState();
    }

    public void redo() {
        getCodeEditorPane().getUndoActions().redo();
        updateUndoRedoActionState();
    }

    boolean isUndoAvailable() {
        return getCodeEditorPane().getUndoActions().isUndoAvailable();
    }

    boolean isRedoAvailable() {
        return getCodeEditorPane().getUndoActions().isRedoAvailable();
    }

    private void updateUndoRedoActionState() {
        actionStateManager.setUndoActionDisable(!getCodeEditorPane().getUndoActions().isUndoAvailable());
        actionStateManager.setRedoActionDisable(!getCodeEditorPane().getUndoActions().isRedoAvailable());
    }

    public void format() {
        getCodeEditorPane().format();
        setChanged();
    }
}
