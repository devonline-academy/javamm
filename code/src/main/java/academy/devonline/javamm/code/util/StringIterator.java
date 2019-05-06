/*
 * Copyright (c) 2019. http://devonline.academy
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package academy.devonline.javamm.code.util;

import static java.util.Objects.requireNonNull;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
public final class StringIterator {

    private final String string;

    private int index;

    public StringIterator(final String string) {
        this.string = requireNonNull(string);
        this.index = 0;
    }

    public boolean hasNext() {
        return index < string.length();
    }

    public char next() {
        return string.charAt(index++);
    }

    public String tail() {
        return string.substring(index);
    }

    public char previous() {
        return string.charAt(--index);
    }
}
