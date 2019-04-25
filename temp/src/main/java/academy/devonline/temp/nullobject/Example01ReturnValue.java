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

package academy.devonline.temp.nullobject;

import java.util.List;
import java.util.Optional;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
public class Example01ReturnValue {

    private final String requiredObject;

    private final String optionalObject;

    private final List<String> requiredList;

    private final List<String> optionalList;

    public Example01ReturnValue(final String requiredObject,
                                final String optionalObject,
                                final List<String> requiredList,
                                final List<String> optionalList) {
        this.requiredObject = requiredObject;
        this.optionalObject = optionalObject;
        this.requiredList = requiredList;
        this.optionalList = optionalList == null ? List.of() : List.copyOf(optionalList);
    }

    public Example01ReturnValue() {
        this("required", null, List.of("required"), null);
    }

    public String getRequiredObject() {
        return requiredObject;
    }

    public Optional<String> getOptionalObject() {
        return Optional.ofNullable(optionalObject);
    }

    public List<String> getRequiredList() {
        return requiredList;
    }

    public List<String> getOptionalList() {
        return optionalList;
    }
}
