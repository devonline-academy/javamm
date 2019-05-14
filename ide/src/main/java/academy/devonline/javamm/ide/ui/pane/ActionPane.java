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

import academy.devonline.javamm.ide.component.VirtualMachineRunner;
import academy.devonline.javamm.ide.ui.listener.ActionListener;
import academy.devonline.javamm.ide.ui.listener.ActionStateManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static java.lang.String.format;
import static java.util.Objects.requireNonNull;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
public final class ActionPane extends VBox implements ActionStateManager,
    VirtualMachineRunner.VirtualMachineRunCompletedListener {

    private Map<MenuItem, Boolean> beforeRunStates;

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

    @FXML
    private void initialize() {
        bindDisableProperties();
        setDefaultDisableState();
        customizeTooltip();
    }

    private void bindDisableProperties() {
        miNew.disableProperty().bindBidirectional(tbNew.disableProperty());
        miOpen.disableProperty().bindBidirectional(tbOpen.disableProperty());
        miSave.disableProperty().bindBidirectional(tbSave.disableProperty());

        miUndo.disableProperty().bindBidirectional(tbUndo.disableProperty());
        miRedo.disableProperty().bindBidirectional(tbRedo.disableProperty());
        miFormat.disableProperty().bindBidirectional(tbFormat.disableProperty());

        miRun.disableProperty().bindBidirectional(tbRun.disableProperty());
        miTerminate.disableProperty().bindBidirectional(tbTerminate.disableProperty());
    }

    private void setDefaultDisableState() {
        setSaveActionDisable(true);
        setUndoActionDisable(true);
        setRedoActionDisable(true);
        setFormatActionDisable(true);
        setRunActionDisable(true);
        setTerminateActionDisable(true);
    }

    private void customizeTooltip() {
        final String template = "%s (%s)";
        tbNew.getTooltip().setText(format(template,
            tbNew.getTooltip().getText(), miNew.getAccelerator().getDisplayText()));
        tbOpen.getTooltip().setText(format(template,
            tbOpen.getTooltip().getText(), miOpen.getAccelerator().getDisplayText()));
        tbSave.getTooltip().setText(format(template,
            tbSave.getTooltip().getText(), miSave.getAccelerator().getDisplayText()));

        tbUndo.getTooltip().setText(format(template,
            tbUndo.getTooltip().getText(), miUndo.getAccelerator().getDisplayText()));
        tbRedo.getTooltip().setText(format(template,
            tbRedo.getTooltip().getText(), miRedo.getAccelerator().getDisplayText()));
        tbFormat.getTooltip().setText(format(template,
            tbFormat.getTooltip().getText(), miFormat.getAccelerator().getDisplayText()));

        tbRun.getTooltip().setText(format(template,
            tbRun.getTooltip().getText(), miRun.getAccelerator().getDisplayText()));
        tbTerminate.getTooltip().setText(format(template,
            tbTerminate.getTooltip().getText(), miTerminate.getAccelerator().getDisplayText()));
    }

    @Override
    public void setNewActionDisable(final boolean disable) {
        miNew.setDisable(disable);
    }

    @Override
    public void setOpenActionDisable(final boolean disable) {
        miOpen.setDisable(disable);
    }

    @Override
    public void setSaveActionDisable(final boolean disable) {
        miSave.setDisable(disable);
    }

    @Override
    public void setUndoActionDisable(final boolean disable) {
        miUndo.setDisable(disable);
    }

    @Override
    public void setRedoActionDisable(final boolean disable) {
        miRedo.setDisable(disable);
    }

    @Override
    public void setFormatActionDisable(final boolean disable) {
        miFormat.setDisable(disable);
    }

    @Override
    public void setRunActionDisable(final boolean disable) {
        miRun.setDisable(disable);
    }

    @Override
    public void setTerminateActionDisable(final boolean disable) {
        miTerminate.setDisable(disable);
    }

    @FXML
    private void onNewAction(final ActionEvent event) {
        actionListener.onNewAction();
        setFormatActionDisable(false);
        setRunActionDisable(false);
    }

    @FXML
    private void onOpenAction(final ActionEvent event) {
        if (actionListener.onOpenAction()) {
            setFormatActionDisable(false);
            setRunActionDisable(false);
        }
    }

    @FXML
    private void onSaveAction(final ActionEvent event) {
        if (actionListener.onSaveAction()) {
            setSaveActionDisable(true);
        }
    }

    @FXML
    private void onExitAction(final ActionEvent event) {
        actionListener.onExitAction();
    }

    @FXML
    private void onUndoAction(final ActionEvent event) {
        actionListener.onUndoAction();
    }

    @FXML
    private void onRedoAction(final ActionEvent event) {
        actionListener.onRedoAction();
    }

    @FXML
    private void onFormatAction(final ActionEvent event) {
        actionListener.onFormatAction();
    }

    @FXML
    private void onRunAction(final ActionEvent event) {
        final List<MenuItem> menuItems = Arrays.asList(miNew, miOpen, miSave, miExit, miUndo, miRedo, miFormat);
        beforeRunStates = menuItems.stream().collect(toMap(identity(), MenuItem::isDisable));
        menuItems.forEach(mi -> mi.setDisable(true));

        setRunActionDisable(true);
        setTerminateActionDisable(false);

        actionListener.onRunAction();
    }

    @Override
    public void onRunCompleted(final VirtualMachineRunner.CompleteStatus status) {
        beforeRunStates.forEach(MenuItem::setDisable);
        beforeRunStates = null;
        setRunActionDisable(false);
        setTerminateActionDisable(true);
    }

    @FXML
    private void onTerminateAction(final ActionEvent event) {
        actionListener.onTerminateAction();

        onRunCompleted(VirtualMachineRunner.CompleteStatus.TERMINATED);
    }

    public boolean isExitActionDisable() {
        return miExit.isDisable();
    }

    @Override
    public void setExitActionDisable(final boolean disable) {
        miExit.setDisable(disable);
    }


}
