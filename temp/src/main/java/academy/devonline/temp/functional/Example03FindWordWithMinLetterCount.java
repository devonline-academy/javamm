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

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Supplier;

import static java.util.Comparator.naturalOrder;
import static java.util.stream.Collectors.toMap;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
@SuppressWarnings("CheckStyle")
public final class Example03FindWordWithMinLetterCount {

    private Example03FindWordWithMinLetterCount() {
    }

    public static void main(final String[] args) {
        final List<String> strings = List.of("Hello", "world", "Java", "the", "best", "language");

        //Imperative
        String word = null;
        int length = Integer.MAX_VALUE;
        for (final String string : strings) {
            if (string.length() < length) {
                length = string.length();
                word = string;
            }
        }
        System.out.println(word == null ? "NOT FOUND" : word);

        // Functional: filter
        System.out.println(strings
            .stream()
            .filter(s -> strings.stream()
                .map(String::length)
                .sorted()
                .findFirst().orElse(-1) == s.length())
            .findFirst()
            .orElse("NOT FOUND"));

        // Functional: filter
        System.out.println(strings
            .stream()
            .filter(s -> strings.stream().map(String::length).min(naturalOrder()).orElse(-1) == s.length())
            .findFirst()
            .orElse("NOT FOUND"));

        // Functional: map
        System.out.println(strings
            .stream()
            .collect(toMap(
                String::length,
                s -> s,
                (s1, s2) -> s1,
                (Supplier<Map<Integer, String>>) TreeMap::new))
            .entrySet()
            .iterator()
            .next()
            .getValue()
        );
        // Functional: Reduce
        System.out.println(strings
            .stream()
            .reduce((s1, s2) -> s1.length() < s2.length() ? s1 : s2)
            .orElse("NOT FOUND"));

        System.out.println(strings.stream().reduce((s1, s2) -> s1.length() < s2.length() ? s1 : s2).orElse("NOT FOUND"));
        // Functional: Min
        System.out.println(strings
            .stream()
            .min(Comparator.comparing(String::length))
            .orElse("NOT FOUND"));

        System.out.println(strings.stream().min(Comparator.comparing(String::length)).orElse("NOT FOUND"));

        // Imperative: Using Collections
        System.out.println(Collections.min(strings, Comparator.comparing(String::length)));
    }
}
