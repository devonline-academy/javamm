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
import javafx.scene.layout.VBox;

import static java.util.Objects.requireNonNull;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
public final class ActionPane extends VBox implements ActionStateManager {

    private ActionListener actionListener;

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
}
