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

package academy.devonline.temp.immutable.list;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
public class Example03UnmodifiableList {

    private final List<Integer> params;

    private final List<Integer> list = new ArrayList<>();

    public Example03UnmodifiableList(final List<Integer> params) {
        this.params = List.copyOf(params);
    }

    public void add(final int value) {
        list.add(value);
    }

    public List<Integer> getParams() {
        return params;
    }

    public List<Integer> getList() {
        if (list.isEmpty()) {
            return List.of();
        } else {
            return Collections.unmodifiableList(list);
        }
    }
}
