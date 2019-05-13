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

import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
public final class UIUtils {

    private UIUtils() {
    }

    public static void centerByScreen(final Stage stage,
                                      final double widthPercentage,
                                      final double heightPercentage) {
        final Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        final double width = primaryScreenBounds.getWidth() * widthPercentage;
        final double height = primaryScreenBounds.getHeight() * heightPercentage;

        stage.setWidth(width);
        stage.setHeight(height);
        stage.setX((primaryScreenBounds.getWidth() - width) / 2);
        stage.setY((primaryScreenBounds.getHeight() - height) / 2);
    }
}
