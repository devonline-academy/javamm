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
public final class Example01 {

    private Example01() {
    }

    public static void main(final String[] args) {
        final List<Integer> values = List.of(0, 1, 2, 3, 4);
        // ---------------------------------------------------------------
        final Iterator<Integer> iterator = values.iterator();
        while (iterator.hasNext()) {
            System.out.print(iterator.next() + " ");
        }
        System.out.println();
        // ---------------------------------------------------------------
        for (final Iterator<Integer> it = values.iterator(); it.hasNext(); ) {
            System.out.print(it.next() + " ");
        }
        System.out.println();
        // ---------------------------------------------------------------
        for (final Integer value : values) {
            System.out.print(value + " ");
        }
        System.out.println();
    }
}
