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

package academy.devonline.temp.iterator;

import java.util.Iterator;
import java.util.List;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
@SuppressWarnings("CheckStyle")
public final class Example02 {

    private Example02() {
    }

    public static void main(final String[] args) {
        final List<Integer> values = List.of(0, 1, 2, 3, 4);
        final Iterator<Integer> iterator = values.iterator();
        simpleIteration(iterator);
        complexIteration(iterator);
    }

    private static void simpleIteration(final Iterator<Integer> iterator) {
        if (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }

    private static void complexIteration(final Iterator<Integer> iterator) {
        while (iterator.hasNext()) {
            final Integer value = iterator.next();
            System.out.println(value);
            if (value == 3) {
                break;
            }
        }
    }
}
