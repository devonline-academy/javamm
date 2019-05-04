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

import java.io.Serializable;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
@SuppressWarnings("CheckStyle")
public final class Example05MultipleInheritance2 {

    private Example05MultipleInheritance2() {
    }

    public static void main(final String[] args) {
        final IntegerProvider integerProvider = new IntegerProvider();
        final NumberAndComparableProvider<Integer> numberAndComparableProvider = integerProvider;
    }

    /**
     * @author devonline
     * @link http://devonline.academy/javamm
     */
    private interface NumberAndComparableProvider<T extends Number & Comparable<T> & Serializable> {

        T getValue();
    }

    /**
     * @author devonline
     * @link http://devonline.academy/javamm
     */
    private static final class IntegerProvider implements NumberAndComparableProvider<Integer> {

        @Override
        public Integer getValue() {
            return 12;
        }
    }

    /**
     * @author devonline
     * @link http://devonline.academy/javamm
     */
    /*private static final class BooleanProvider implements NumberAndComparableProvider<Boolean> {

        @Override
        public Boolean getValue() {
            return true;
        }
    }*/
}
