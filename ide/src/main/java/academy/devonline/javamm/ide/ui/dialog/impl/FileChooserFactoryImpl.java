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

import academy.devonline.javamm.ide.ui.dialog.FileChooserFactory;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.requireNonNull;
import static java.util.Optional.ofNullable;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
public final class FileChooserFactoryImpl implements FileChooserFactory {

    private final String openFileTitle;

    private final String saveFileTitle;

    private final List<FileChooser.ExtensionFilter> extensionFilters;

    private File currentDirectory;

    private FileChooserFactoryImpl(final String openFileTitle,
                                   final String saveFileTitle,
                                   final List<FileChooser.ExtensionFilter> extensionFilters,
                                   final File currentDirectory) {
        this.openFileTitle = requireNonNull(openFileTitle);
        this.saveFileTitle = requireNonNull(saveFileTitle);
        this.extensionFilters = List.copyOf(extensionFilters);
        this.currentDirectory = currentDirectory;
    }

    @Override
    public Optional<File> showOpenDialog(final Window ownerWindow) {
        final FileChooser fileChooser = getNewFileChooser(openFileTitle);
        final File openFile = fileChooser.showOpenDialog(ownerWindow);
        if (openFile != null) {
            currentDirectory = openFile.getParentFile();
        }
        return Optional.ofNullable(openFile);
    }

    @Override
    public Optional<File> showSaveDialog(final Window ownerWindow,
                                         final String initialFileName) {
        final FileChooser fileChooser = getNewFileChooser(saveFileTitle);
        fileChooser.setInitialFileName(initialFileName);
        final File saveFile = fileChooser.showSaveDialog(ownerWindow);
        if (saveFile != null) {
            currentDirectory = saveFile.getParentFile();
        }
        return Optional.ofNullable(saveFile);
    }

    private FileChooser getNewFileChooser(final String title) {
        final FileChooser fileChooser = new FileChooser();
        if (currentDirectory != null && currentDirectory.exists()) {
            fileChooser.setInitialDirectory(currentDirectory);
        }
        fileChooser.setTitle(title);
        fileChooser.getExtensionFilters().addAll(extensionFilters);
        return fileChooser;
    }

    /**
     * @author devonline
     * @link http://devonline.academy/javamm
     */
    public static final class Builder implements FileChooserFactory.Builder {

        private String openFileTitle;

        private String saveFileTitle;

        private List<FileChooser.ExtensionFilter> extensionFilters;

        private File initialDirectory;

        @Override
        public Builder setOpenFileTitle(final String openFileTitle) {
            this.openFileTitle = requireNonNull(openFileTitle);
            return this;
        }

        @Override
        public Builder setSaveFileTitle(final String saveFileTitle) {
            this.saveFileTitle = requireNonNull(saveFileTitle);
            return this;
        }

        @Override
        public Builder setExtensionFilters(final FileChooser.ExtensionFilter... extensionFilters) {
            this.extensionFilters = List.of(extensionFilters);
            return this;
        }

        @Override
        public Builder setInitialDirectory(final File initialDirectory) {
            this.initialDirectory = requireNonNull(initialDirectory);
            return this;
        }

        @Override
        public FileChooserFactory build() {
            return new FileChooserFactoryImpl(
                ofNullable(openFileTitle).orElse("Open file"),
                ofNullable(saveFileTitle).orElse("Save file"),
                ofNullable(extensionFilters).orElse(
                    List.of(new FileChooser.ExtensionFilter("All files (*.*)", "*.*"))),
                initialDirectory
            );
        }
    }
}
