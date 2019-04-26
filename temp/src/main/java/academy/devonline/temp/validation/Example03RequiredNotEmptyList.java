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

package academy.devonline.temp.validation;

import java.util.List;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
public class Example03RequiredNotEmptyList {

    private final List<String> requiredNotEmpty;

    private final List<String> requiredButEmptyAllowed;

    public Example03RequiredNotEmptyList(final List<String> requiredNotEmpty,
                                         final List<String> requiredButEmptyAllowed) {
        if (requiredNotEmpty.isEmpty()) {
            throw new IllegalArgumentException("Expected not empty list");
        }
        // requireNonNull() - not necessary
        this.requiredNotEmpty = List.copyOf(requiredNotEmpty);
        this.requiredButEmptyAllowed = List.copyOf(requiredButEmptyAllowed);
    }

    public List<String> getRequiredNotEmpty() {
        return requiredNotEmpty;
    }

    public List<String> getRequiredButEmptyAllowed() {
        return requiredButEmptyAllowed;
    }
}
