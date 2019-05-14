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

import academy.devonline.javamm.ide.component.AsyncSyntaxHighlighter;
import academy.devonline.javamm.ide.component.Releasable;
import javafx.beans.value.ChangeListener;
import javafx.scene.layout.StackPane;
import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;

import static academy.devonline.javamm.ide.component.ComponentFactoryProvider.getComponentFactory;
import static academy.devonline.javamm.ide.util.ResourceUtils.getClasspathResource;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
public final class CodeEditorPane extends StackPane implements Releasable {

    private final CodeArea codeArea = new CodeArea();

    private final AsyncSyntaxHighlighter asyncSyntaxHighlighter =
        getComponentFactory().createAsyncSyntaxHighlighter(codeArea);

    private File savedSourceCodeFile;

    CodeEditorPane() {
        codeArea.setParagraphGraphicFactory(LineNumberFactory.get(codeArea));
        getChildren().add(new VirtualizedScrollPane<>(codeArea));
        getStylesheets().add(getClasspathResource("/style/code-editor-pane.css").toExternalForm());
        asyncSyntaxHighlighter.enable();
    }

    @Override
    public void requestFocus() {
        codeArea.requestFocus();
    }

    @Override
    public void release() {
        asyncSyntaxHighlighter.disable();
    }

    List<String> getCodeLines() {
        return List.of(codeArea.getText().split("\n"));
    }

    void setChangeListener(final ChangeListener<String> changeListener) {
        codeArea.textProperty().addListener(changeListener);
    }

    Optional<File> getSourceCodeFile() {
        return Optional.ofNullable(savedSourceCodeFile);
    }

    void loadCode(final File selectedFile) throws IOException {
        this.savedSourceCodeFile = selectedFile;
        codeArea.replaceText(Files.readString(selectedFile.toPath()));
    }

    void saveCode(final File selectedFile) throws IOException {
        this.savedSourceCodeFile = selectedFile;
        Files.writeString(selectedFile.toPath(), codeArea.getText());
    }
}
