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

package academy.devonline.temp.immutable.list;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
@SuppressWarnings("CheckStyle")
public final class Example07ValidUsageForArray {

    private Example07ValidUsageForArray() {
    }

    public static void main(final String[] args) {
        println();
        println(1);
        println(2, 3, 4);
        println(5, "hello", true);

        println(new Object[] {null});
        //println(null);

        println2(new Object[] {null});
        //println2(null);
    }

    private static void println(final Object... args) {
        for (final Object arg : args) {
            System.out.println(arg);
        }
    }

    private static void println2(final Object... args) {
        if (args != null) {
            for (final Object arg : args) {
                System.out.println(arg);
            }
        } else {
            System.out.println((String) null);
        }
    }
}
