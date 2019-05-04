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

package academy.devonline.temp.generics;

import java.util.List;
import java.util.Objects;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
@SuppressWarnings("CheckStyle")
public final class Example02Solution {

    private Example02Solution() {
    }

    public static void main(final String[] args) {
        final List<Integer> items = List.of(0, 1, 2, 3, 4, 5);

        System.out.println(linearSearch(items, 5));
        //System.out.println(linearSearch(items, "5"));
    }


    // Java is statically typed language
    private static <T> int linearSearch(final List<T> items, final T query) {
        for (int i = 0; i < items.size(); i++) {
            if (Objects.equals(query, items.get(i))) {
                return i;
            }
        }
        return -1;
    }
}
