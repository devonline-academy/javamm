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

import javafx.scene.Node;
import javafx.scene.control.SplitPane;

import java.util.Optional;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
public final class PaneManager {

    private static final double CODE_TAB_VS_CONSOLE_DIVIDER_POSITION = 0.65;

    private ConsolePane consolePane;

    public ConsolePane getVisibleConsolePane(final SplitPane spWork) {
        return find(spWork, ConsolePane.class).orElseGet(() ->
            addPaneAndReturn(spWork, getConsolePane(), CODE_TAB_VS_CONSOLE_DIVIDER_POSITION));
    }

    private ConsolePane getConsolePane() {
        if (consolePane == null) {
            consolePane = new ConsolePane();
        }
        return consolePane;
    }

    private <T extends Node> T addPaneAndReturn(final SplitPane splitPane,
                                                final T pane,
                                                final double dividerPosition) {
        splitPane.getItems().add(pane);
        splitPane.setDividerPosition(0, dividerPosition);
        return pane;
    }

    @SuppressWarnings("unchecked")
    private <T> Optional<T> find(final SplitPane splitPane,
                                 final Class<T> paneClass) {
        return splitPane.getItems().stream().filter(n -> n.getClass() == paneClass).map(n -> (T) n).findFirst();
    }
}

