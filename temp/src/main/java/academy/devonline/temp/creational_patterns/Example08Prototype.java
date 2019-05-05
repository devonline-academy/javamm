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

package academy.devonline.temp.creational_patterns;

import java.util.List;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
public final class Example08Prototype implements Cloneable {

    private final int value;

    private final String string;

    private final List<String> list;

    public Example08Prototype(final int value, final String string, final List<String> list) {
        this.value = value;
        this.string = string;
        this.list = list;
    }

    @Override
    public Example08Prototype clone() {
        return new Example08Prototype(value, string, list);
    }
}
