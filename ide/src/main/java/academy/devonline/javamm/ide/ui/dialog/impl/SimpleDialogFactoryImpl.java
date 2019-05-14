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

package academy.devonline.javamm.ide.ui.dialog.impl;

import academy.devonline.javamm.ide.ui.dialog.SimpleDialogFactory;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
public class SimpleDialogFactoryImpl implements SimpleDialogFactory {

    @Override
    public void showInfoDialog(final String message) {
        final Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Information");
        alert.setContentText(message);
        alert.showAndWait();
    }

    @Override
    public void showErrorDialog(final String message) {
        final Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Error");
        alert.setContentText(message);
        alert.showAndWait();
    }

    @Override
    public ButtonBar.ButtonData showYesNoCancelDialog(final String title,
                                                      final String question) {
        final Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(title);
        alert.setContentText(question);
        final ButtonType okButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        final ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.NO);
        final ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(okButton, noButton, cancelButton);
        return alert.showAndWait().map(ButtonType::getButtonData).orElseThrow();
    }
}
