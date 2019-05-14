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

package academy.devonline.javamm.ide.ui.dialog;

import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;
import java.util.Optional;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
public interface FileChooserFactory {

    Optional<File> showOpenDialog(Window ownerWindow);

    Optional<File> showSaveDialog(Window ownerWindow, String initialFileName);

    /**
     * @author devonline
     * @link http://devonline.academy/javamm
     */
    interface Builder {

        Builder setOpenFileTitle(String openFileTitle);

        Builder setSaveFileTitle(String saveFileTitle);

        Builder setExtensionFilters(FileChooser.ExtensionFilter... extensionFilters);

        Builder setInitialDirectory(File initialDirectory);

        FileChooserFactory build();
    }
}
