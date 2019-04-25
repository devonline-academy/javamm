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
@SuppressWarnings("CheckStyle")
public final class Example03ComplexMain {

    private Example03ComplexMain() {
    }

    public static void main(final String[] args) {
        final Example03Complex example = new Example03Complex.Builder()
                .setParam1(2)
                .setParam2("string")
                .setParam3(true)
                .setParam4(null)
                .setParam5(0)
                .build();
        System.out.println(example.getParam1());
        System.out.println(example.getParam2());
        System.out.println(example.getParam3());
        System.out.println(example.getParam4());
        System.out.println(example.getParam5());
    }
}
