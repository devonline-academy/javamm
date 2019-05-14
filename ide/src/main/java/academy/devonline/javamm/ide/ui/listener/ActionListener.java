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

package academy.devonline.javamm.ide.ui.listener;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
public interface ActionListener {

    void onNewAction();

    /**
     * @return true if 'open' action is completed successful
     */
    boolean onOpenAction();

    /**
     * @return true if 'save' action is completed successful
     */
    boolean onSaveAction();

    /**
     * @return true if 'exit' action is completed successful
     */
    boolean onExitAction();

    void onUndoAction();

    void onRedoAction();

    void onFormatAction();

    void onRunAction();

    void onTerminateAction();
}
