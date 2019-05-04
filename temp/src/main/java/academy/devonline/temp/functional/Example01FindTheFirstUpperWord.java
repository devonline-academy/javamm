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

import static java.lang.Character.isUpperCase;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
@SuppressWarnings("CheckStyle")
public final class Example01FindTheFirstUpperWord {

    private Example01FindTheFirstUpperWord() {
    }

    public static void main(final String[] args) {
        final List<String> strings = List.of("hello", "world", "java", "the", "best", "language");

        //Imperative
        boolean found = false;
        for (final String string : strings) {
            if (string.length() > 0 && isUpperCase(string.charAt(0))) {
                System.out.println(string);
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("Not found");
        }

        //Functional
        System.out.println(strings.stream()
            .filter(s -> s.length() > 0 && isUpperCase(s.charAt(0)))
            .findFirst()
            .orElse("Not found")
        );
    }
}
