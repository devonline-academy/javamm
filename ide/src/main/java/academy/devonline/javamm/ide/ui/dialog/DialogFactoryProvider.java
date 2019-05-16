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

import academy.devonline.javamm.ide.ui.dialog.impl.FileChooserFactoryImpl;
import academy.devonline.javamm.ide.ui.dialog.impl.SimpleDialogFactoryImpl;

import java.util.function.Supplier;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
public final class DialogFactoryProvider {

    private static SimpleDialogFactory simpleDialogFactory = new SimpleDialogFactoryImpl();

    private static Supplier<FileChooserFactory.Builder> fileChooserFactoryBuilderSupplier =
        FileChooserFactoryImpl.Builder::new;

    private DialogFactoryProvider() {
    }

    public static SimpleDialogFactory getSimpleDialogFactory() {
        return simpleDialogFactory;
    }

    /*public static void setSimpleDialogFactory(final SimpleDialogFactory simpleDialogFactory) {
        DialogFactoryProvider.simpleDialogFactory = requireNonNull(simpleDialogFactory);
    }*/

    public static FileChooserFactory.Builder createFileChooserFactoryBuilder() {
        return fileChooserFactoryBuilderSupplier.get();
    }

    /*public static void setFileChooserFactoryBuilderSupplier(
        final Supplier<FileChooserFactory.Builder> fileChooserFactoryBuilderSupplier) {
        DialogFactoryProvider.fileChooserFactoryBuilderSupplier = requireNonNull(fileChooserFactoryBuilderSupplier);
    }*/
}
