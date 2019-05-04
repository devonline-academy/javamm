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

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
@SuppressWarnings("CheckStyle")
public final class Example03CastSolution {

    private Example03CastSolution() {
    }

    public static void main(final String[] args) {
        final List<Integer> items = List.of(3, 4, 0, 1, 5);

        System.out.println(findMin(items));
        System.out.println(findMin(List.of("hello", "world", "java")));
        //System.out.println(findMin(List.of(new AtomicInteger(3), new AtomicInteger(0), new AtomicInteger(1))));
    }

    private static <T extends Comparable<T>> T findMin(final List<T> items) {
        T min = items.get(0);
        for (final T item : items) {
            if (item.compareTo(min) < 0) {
                min = item;
            }
        }
        return min;
    }
}
