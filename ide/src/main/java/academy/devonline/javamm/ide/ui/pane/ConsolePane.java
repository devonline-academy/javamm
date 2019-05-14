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

import academy.devonline.javamm.code.component.Console;
import javafx.application.Platform;
import javafx.fxml.FXML;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;

import java.util.Collection;
import java.util.List;

import static academy.devonline.javamm.ide.util.ResourceUtils.getClasspathResource;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
public final class ConsolePane extends TitledPane {

    private final Console console = new CodeAreaConsoleAdapter();

    @FXML
    private CodeArea caConsole;

    ConsolePane() {
        super("/javafx/console-pane.fxml");
        caConsole.getStylesheets().add(getClasspathResource("/style/console-pane.css").toExternalForm());
    }

    public Console getNewConsole() {
        caConsole.setEditable(false);
        caConsole.replaceText("");
        return console;
    }

    private void postAppendText(final String message,
                                final String styleClass) {
        final String normalizedMessage = message.replace("\r", "");
        Platform.runLater(() -> displayMessage(normalizedMessage, styleClass));
    }

    private void displayMessage(final String message,
                                final String styleClass) {
        final StyleSpans<Collection<String>> styleSpans = new StyleSpansBuilder<Collection<String>>(1)
            .add(List.of(styleClass), message.length())
            .create();
        final int from = caConsole.getLength();
        caConsole.appendText(message);
        caConsole.setStyleSpans(from, styleSpans);
    }

    public void displayCompletedMessage() {
        displayMessage("\nProcess successful finished", "completed");
    }

    public void displayTerminatedMessage() {
        displayMessage("\nProcess terminated", "err");
    }

    /**
     * @author devonline
     * @link http://devonline.academy/javamm
     */
    private class CodeAreaConsoleAdapter implements Console {

        @Override
        public void outPrintln(final Object value) {
            postAppendText(value + "\n", "out");
        }

        @Override
        public void errPrintln(final String message) {
            postAppendText(message + "\n", "err");
        }
    }
}
