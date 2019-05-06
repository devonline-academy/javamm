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

package academy.devonline.javamm.code.fragment;

import java.util.Optional;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
public enum Parenthesis implements Lexeme {

    OPENING_PARENTHESIS(true),

    CLOSING_PARENTHESIS(false);

    private final boolean open;

    Parenthesis(final boolean open) {
        this.open = open;
    }

    public static Optional<Parenthesis> of(final String value) {
        if ("(".equals(value)) {
            return Optional.of(OPENING_PARENTHESIS);
        } else if (")".equals(value)) {
            return Optional.of(CLOSING_PARENTHESIS);
        } else {
            return Optional.empty();
        }
    }

    public boolean isOpen() {
        return open;
    }

    @Override
    public String toString() {
        return open ? "(" : ")";
    }
}
