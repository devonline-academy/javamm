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

package academy.devonline.javamm.code.syntax;

import java.util.Set;
import java.util.stream.Stream;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toUnmodifiableSet;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
public final class Delimiters {

    public static final Set<Character> STRING_DELIMITERS = Set.of('\'', '"');

    public static final Set<String> OPERATOR_TOKEN_DELIMITERS =
        Set.of(
            "+", "++", "+=", "-", "--", "-=", "*", "*=", "/", "/=", "%", "%=",
            ">", ">>", ">=", ">>>", ">>=", ">>>=", "<", "<<", "<=", "<<=",
            "!", "!=", "=", "==", "&", "&&", "&=", "|", "||", "|=", "^", "^=", "~"
        );

    /**
     * https://www.cis.upenn.edu/~matuszek/General/JavaSyntax/parentheses.html
     * any -> brackets
     * () - parentheses
     * {} - curly braces
     * [] - square brackets
     * <> - angle brackets
     */
    public static final Set<String> NOT_OPERATOR_TOKEN_DELIMITERS = Set.of(
        "(", ")",
        "{", "}",
        "[", "]",
        ":",
        ",",
        ";"
    );

    public static final Set<String> SIGNIFICANT_TOKEN_DELIMITERS =
        Stream.of(
            OPERATOR_TOKEN_DELIMITERS.stream(),
            NOT_OPERATOR_TOKEN_DELIMITERS.stream()
        ).flatMap(identity()).collect(toUnmodifiableSet());

    private Delimiters() {
    }
}
