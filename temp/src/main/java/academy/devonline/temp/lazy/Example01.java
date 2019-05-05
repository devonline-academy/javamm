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

package academy.devonline.temp.lazy;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
@SuppressWarnings("CheckStyle")
public final class Example01 {

    private Example01() {
    }

    public static void main(final String[] args) {
        lazyLogicAnd1();
        System.out.println("--------------------------------");
        lazyLogicAnd2();
    }

    private static void lazyLogicAnd1() {
        final int a = 1;
        int b = 2;

        if (a < 0 && b++ < 10) { // ||
            System.out.println("'a < 0 && b++ < 10' = TRUE");
        }

        System.out.println("a = " + a);
        System.out.println("b = " + b);
    }

    private static void lazyLogicAnd2() {
        final int a = 1;
        int b = 2;

        if (a > 0 && b++ < 10) {
            System.out.println("'a > 0 && b++ < 10' = TRUE");
        }

        System.out.println("a = " + a);
        System.out.println("b = " + b);
    }
}
