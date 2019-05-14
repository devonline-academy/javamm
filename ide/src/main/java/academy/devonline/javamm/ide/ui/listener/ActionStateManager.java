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
public interface ActionStateManager {

    void setNewActionDisable(boolean disable);

    void setOpenActionDisable(boolean disable);

    void setSaveActionDisable(boolean disable);

    void setExitActionDisable(boolean disable);


    void setUndoActionDisable(boolean disable);

    void setRedoActionDisable(boolean disable);

    void setFormatActionDisable(boolean disable);


    void setRunActionDisable(boolean disable);

    void setTerminateActionDisable(boolean disable);
}
