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

package academy.devonline.temp.functional;

import java.util.List;

import static java.util.Comparator.naturalOrder;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
@SuppressWarnings("CheckStyle")
public final class Example02FindTheMinLetterCount {

    private Example02FindTheMinLetterCount() {
    }

    public static void main(final String[] args) {
        final List<String> strings = List.of("Hello", "world", "Java", "the", "best", "language");

        //Imperative
        int length = Integer.MAX_VALUE;
        for (final String string : strings) {
            if (string.length() < length) {
                length = string.length();
            }
        }
        System.out.println(length == Integer.MAX_VALUE ? -1 : length);

        //Functional
        System.out.println(strings.stream()
            .map(String::length)
            .min(naturalOrder())
            .orElse(-1)
        );
    }
}
