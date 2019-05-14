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

import academy.devonline.javamm.ide.ui.listener.ActionListener;
import academy.devonline.javamm.ide.ui.listener.ActionStateManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;

import java.io.IOException;

import static java.util.Objects.requireNonNull;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
public final class ActionPane extends VBox implements ActionStateManager {

    @FXML
    private MenuItem miNew;

    @FXML
    private MenuItem miOpen;

    @FXML
    private MenuItem miSave;

    @FXML
    private MenuItem miExit;

    @FXML
    private MenuItem miUndo;

    @FXML
    private MenuItem miRedo;

    @FXML
    private MenuItem miFormat;

    @FXML
    private MenuItem miRun;

    @FXML
    private MenuItem miTerminate;

    @FXML
    private Button tbNew;

    @FXML
    private Button tbOpen;

    @FXML
    private Button tbSave;

    @FXML
    private Button tbUndo;

    @FXML
    private Button tbRedo;

    @FXML
    private Button tbFormat;

    @FXML
    private Button tbRun;

    @FXML
    private Button tbTerminate;

    private ActionListener actionListener;

    public ActionPane() throws IOException {
        final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/javafx/action-pane.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        fxmlLoader.load();
    }

    public void setActionListener(final ActionListener actionListener) {
        this.actionListener = requireNonNull(actionListener);
    }

    @Override
    public void setNewActionDisable(final boolean disable) {

    }

    @Override
    public void setOpenActionDisable(final boolean disable) {

    }

    @Override
    public void setSaveActionDisable(final boolean disable) {

    }

    @Override
    public void setExitActionDisable(final boolean disable) {

    }

    @Override
    public void setUndoActionDisable(final boolean disable) {

    }

    @Override
    public void setRedoActionDisable(final boolean disable) {

    }

    @Override
    public void setFormatActionDisable(final boolean disable) {

    }

    @Override
    public void setRunActionDisable(final boolean disable) {

    }

    @Override
    public void setTerminateActionDisable(final boolean disable) {

    }

    @FXML
    private void onNewAction(final ActionEvent event) {

    }

    @FXML
    private void onOpenAction(final ActionEvent event) {

    }

    @FXML
    private void onSaveAction(final ActionEvent event) {

    }

    @FXML
    private void onExitAction(final ActionEvent event) {

    }

    @FXML
    private void onUndoAction(final ActionEvent event) {

    }

    @FXML
    private void onRedoAction(final ActionEvent event) {

    }

    @FXML
    private void onFormatAction(final ActionEvent event) {

    }

    @FXML
    private void onRunAction(final ActionEvent event) {

    }

    @FXML
    private void onTerminateAction(final ActionEvent event) {

    }
}
