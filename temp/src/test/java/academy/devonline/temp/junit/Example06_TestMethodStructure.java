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

package academy.devonline.temp.junit;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 * @link https://junit.org/junit5/docs/current/user-guide/
 */
@Disabled
class Example06_TestMethodStructure {

    private final List<Integer> list = new ArrayList<>();

    @Test
    void Should_remove_element_from_list() {
        // prepare
        list.add(1);
        list.add(2);

        // test
        list.remove(Integer.valueOf(2));

        // verify
        assertEquals(1, list.size());
    }
}
