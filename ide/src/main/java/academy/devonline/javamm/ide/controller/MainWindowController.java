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

package academy.devonline.javamm.ide.controller;

import academy.devonline.javamm.ide.ui.listener.ActionListener;
import academy.devonline.javamm.ide.ui.pane.ActionPane;
import academy.devonline.javamm.ide.ui.pane.CodeTabPane;
import academy.devonline.javamm.ide.ui.pane.ConsolePane;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
public class MainWindowController implements ActionListener {

    @FXML
    private ActionPane actionPane;

    @FXML
    private CodeTabPane codeTabPane;

    @FXML
    private ConsolePane consolePane;

    @FXML
    private void initialize() {
        actionPane.setActionListener(this);
    }

    @FXML
    private void onCloseAction(final ActionEvent event) {
        System.exit(0);
    }

    @Override
    public void onNewAction() {
        System.out.println("onNewAction");
    }

    @Override
    public boolean onOpenAction() {
        return false;
    }

    @Override
    public boolean onSaveAction() {
        return false;
    }

    @Override
    public boolean onExitAction() {
        if (actionPane.isExitActionDisable()) {
            return false;
        }
        //TODO
        getStage().close();
        return false;
    }

    private Stage getStage() {
        return (Stage) actionPane.getScene().getWindow();
    }

    @Override
    public void onUndoAction() {

    }

    @Override
    public void onRedoAction() {

    }

    @Override
    public void onFormatAction() {

    }

    @Override
    public void onRunAction() {

    }

    @Override
    public void onTerminateAction() {

    }
}

