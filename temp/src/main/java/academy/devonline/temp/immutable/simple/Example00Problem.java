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

package academy.devonline.temp.immutable.simple;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
public final class Example00Problem {

    private Example00Problem() {
    }

    public static void main(final String[] args) {
        final String immutable = "Hello";
        final StringBuilder mutable = new StringBuilder("Hello");

        if (isValid(immutable, mutable)) {
            System.out.println(immutable);
            System.out.println(mutable);
        } else {
            System.out.println("Not valid");
        }
    }

    private static boolean isValid(final String immutable,
                                   final StringBuilder mutable) {
        if (!"Hello java".equals(immutable + " java")) {
            return false;
        }
        if (!"Hello java".equals(mutable.append(" java").toString())) {
            return false;
        }
        return true;
    }
}
