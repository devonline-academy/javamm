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

package academy.devonline.javamm.ide.util;

import academy.devonline.javamm.code.exception.ConfigException;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.net.URL;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
public final class ResourceUtils {

    private ResourceUtils() {
    }

    public static URL getClasspathResource(final String classpathResourceName) {
        final URL url = ResourceUtils.class.getResource(classpathResourceName);
        if (url == null) {
            throw new ConfigException("Class path resource not found: " + classpathResourceName);
        }
        return url;
    }

    public static void loadFromFxmlResource(final Object component, final String resource) {
        final FXMLLoader fxmlLoader = new FXMLLoader(component.getClass().getResource(resource));
        fxmlLoader.setRoot(component);
        fxmlLoader.setController(component);
        try {
            fxmlLoader.load();
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }
}
