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

import academy.devonline.javamm.ide.component.VirtualMachineRunner;
import academy.devonline.javamm.ide.ui.listener.ActionListener;
import academy.devonline.javamm.ide.ui.listener.TabCloseConfirmationListener;
import academy.devonline.javamm.ide.ui.pane.ActionPane;
import academy.devonline.javamm.ide.ui.pane.CodeTab;
import academy.devonline.javamm.ide.ui.pane.CodeTabPane;
import academy.devonline.javamm.ide.ui.pane.PaneManager;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.stage.Stage;

import static academy.devonline.javamm.ide.component.ComponentFactoryProvider.getComponentFactory;
import static academy.devonline.javamm.ide.ui.dialog.DialogFactoryProvider.getSimpleDialogFactory;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
public class MainWindowController implements ActionListener,
    VirtualMachineRunner.VirtualMachineRunCompletedListener,
    TabCloseConfirmationListener {

    private final PaneManager paneManager = new PaneManager();

    @FXML
    private ActionPane actionPane;

    @FXML
    private CodeTabPane codeTabPane;

    @FXML
    private SplitPane spWork;

    private VirtualMachineRunner virtualMachineRunner;

    @FXML
    private void initialize() {
        actionPane.setActionListener(this);
        codeTabPane.setActionStateManager(actionPane);
        codeTabPane.setTabCloseConfirmationListener(this);
    }

    @Override
    public void onNewAction() {
        codeTabPane.newCodeEditor();
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
    public boolean isTabCloseEventCancelled(final CodeTab codeTab) {
        if (isRunning()) {
            getSimpleDialogFactory().showInfoDialog(
                "Javamm VM is running.\nWait for VM is completed or terminate it!");
            return true;
        } else if (codeTab.isChanged()) {
            final ButtonBar.ButtonData result = getSimpleDialogFactory().showYesNoCancelDialog(
                "Source code has unsaved changes",
                "Save changes before close?");
            if (result == ButtonBar.ButtonData.CANCEL_CLOSE) {
                return true;
            } else if (result == ButtonBar.ButtonData.YES) {
                return !saveChanges(codeTab);
            }
        }
        return false;
    }

    private boolean saveChanges(final CodeTab codeTab) {
        return false;
    }

    private boolean isRunning() {
        return virtualMachineRunner != null && virtualMachineRunner.isRunning();
    }

    @Override
    public boolean onExitAction() {
        if (isRunning()) {
            getSimpleDialogFactory().showInfoDialog(
                "Javamm VM is running.\nWait for VM is completed or terminate it!");
            return false;
        }
        for (final Tab tab : codeTabPane.getTabs()) {
            final CodeTab codeTab = (CodeTab) tab;
            if (codeTab.isChanged()) {
                codeTabPane.getSelectionModel().select(codeTab);
                if (isTabCloseEventCancelled(codeTab)) {
                    return false;
                }
            }
        }
        getStage().close();
        return true;
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
        virtualMachineRunner = getComponentFactory().createVirtualMachineRunner(
            paneManager.getVisibleConsolePane(spWork).getNewConsole(),
            codeTabPane.getAllSourceCodes());
        virtualMachineRunner.run(this);
    }

    @Override
    public void onRunCompleted(final VirtualMachineRunner.CompleteStatus status) {
        Platform.runLater(() -> {
            actionPane.onRunCompleted(status);
            if (status == VirtualMachineRunner.CompleteStatus.SUCCESSFUL) {
                paneManager.getVisibleConsolePane(spWork).displayCompletedMessage();
            } else if (status == VirtualMachineRunner.CompleteStatus.TERMINATED) {
                paneManager.getVisibleConsolePane(spWork).displayTerminatedMessage();
            }
        });
    }

    @Override
    public void onTerminateAction() {
        virtualMachineRunner.terminate();
    }
}

