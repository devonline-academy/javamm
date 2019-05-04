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

import java.util.Comparator;
import java.util.List;

/**
 * PECS – Producer Extends Consumer Super
 *
 * @author devonline
 * @link http://devonline.academy/javamm
 */
@SuppressWarnings("CheckStyle")
public final class Example07ExtendsVsSuper {

    private Example07ExtendsVsSuper() {
    }

    public static void main(final String[] args) {
        final List<Integer> items = List.of(3, 4, 0, 1, 5);

        final Integer result1 = findMin(items, Comparator.naturalOrder());
        System.out.println(result1);
        final Integer result2 = findMin(items, Comparator.reverseOrder());
        System.out.println(result2);
        // --------------------------------------------------------------------------------
        final Number result3 = findMin(items, Comparator.naturalOrder());
        System.out.println(result3);
        final Number result4 = findMin(List.of(1.1, 0.4, 3.4), Comparator.naturalOrder());
        System.out.println(result4);

        // --------------------------------------------------------------------------------
        final Comparator<Number> numberComparator = Comparator.comparingInt(Number::intValue);

        final Number result5 = findMin(items, numberComparator);
        System.out.println(result5);
        final Number result6 = findMin(List.of(1.1, 0.4, 3.4), numberComparator);
        System.out.println(result6);
        // --------------------------------------------------------------------------------
        final Comparator<Object> objectComparator = Comparator.comparingInt(Object::hashCode);

        final Number result7 = findMin(items, objectComparator);
        System.out.println(result7);
        final Number result8 = findMin(List.of(1.1, 0.4, 3.4), objectComparator);
        System.out.println(result8);
    }

    // PECS – Producer Extends Consumer Super
    private static <T> T findMin(final List<? extends T> items,
                                 final Comparator<? super T> comparator) {
        T min = items.get(0);
        for (final T item : items) {
            if (comparator.compare(item, min) < 0) {
                min = item;
            }
        }
        return min;
    }
}
