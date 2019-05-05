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

package academy.devonline.javamm.compiler.component.impl.util;

import academy.devonline.javamm.code.fragment.SourceLine;
import academy.devonline.javamm.compiler.component.impl.error.JavammLineSyntaxError;

import static academy.devonline.javamm.code.syntax.Keywords.KEYWORDS;
import static java.lang.String.format;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
public final class SyntaxValidationUtils {

    private SyntaxValidationUtils() {
    }

    public static void validateThatFirstCharacterIsLetter(final DeveloperObject developerObject,
                                                          final String name,
                                                          final SourceLine sourceLine) {
        if (!Character.isLetter(name.charAt(0))) {
            throw new JavammLineSyntaxError(format("The %s name must start with letter: '%s'",
                developerObject.name().toLowerCase(), name), sourceLine);
        }
    }

    public static void validateThatNameIsNotKeyword(final DeveloperObject developerObject,
                                                    final String name,
                                                    final SourceLine sourceLine) {
        if (KEYWORDS.contains(name)) {
            throw new JavammLineSyntaxError(format("The keyword '%s' can't be used as a %s name", name,
                developerObject.name().toLowerCase()), sourceLine);
        }
    }

    /**
     * @author devonline
     * @link http://devonline.academy/javamm
     */
    public enum DeveloperObject {

        VARIABLE,

        FUNCTION;
    }
}
