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

import academy.devonline.javamm.code.fragment.operator.BinaryOperator;
import academy.devonline.javamm.code.fragment.operator.UnaryOperator;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static academy.devonline.javamm.code.fragment.operator.BinaryOperator.PREDICATE_TYPEOF;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toUnmodifiableSet;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
public final class Delimiters {

    public static final String START_LINE_COMMENT = "//";

    public static final String START_MULTILINE_COMMENT = "/*";

    public static final String END_MULTILINE_COMMENT = "*/";

    public static final Set<Character> IGNORED_DELIMITERS = Set.of(' ', '\u00A0', '\n', '\t', '\r');

    public static final Set<Character> STRING_DELIMITERS = Set.of('\'', '"');

    /*Set.of(
            "+", "++", "+=", "-", "--", "-=", "*", "*=", "/", "/=", "%", "%=",
            ">", ">>", ">=", ">>>", ">>=", ">>>=", "<", "<<", "<=", "<<=",
            "!", "!=", "=", "==", "&", "&&", "&=", "|", "||", "|=", "^", "^=", "~"
    );*/
    public static final Set<String> OPERATOR_TOKEN_DELIMITERS =
        Stream.of(
            Arrays.stream(BinaryOperator.values()).filter(op -> op != PREDICATE_TYPEOF)
                .map(BinaryOperator::getCode),
            Arrays.stream(UnaryOperator.values())
                .map(UnaryOperator::getCode)
        ).flatMap(identity()).collect(Collectors.toUnmodifiableSet());

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
            NOT_OPERATOR_TOKEN_DELIMITERS.stream(),
            Stream.of(START_LINE_COMMENT, START_MULTILINE_COMMENT, END_MULTILINE_COMMENT)
        ).flatMap(identity()).collect(toUnmodifiableSet());

    private Delimiters() {
    }

    public static String trim(final String row) {
        if (row.isEmpty()) {
            return row;
        }
        int start = 0;
        int end = row.length() - 1;
        while (IGNORED_DELIMITERS.contains(row.charAt(start))) {
            start++;
            if (start == row.length()) {
                return "";
            }
        }
        while (IGNORED_DELIMITERS.contains(row.charAt(end))) {
            end--;
        }
        final char[] dst = new char[end - start + 1];
        row.getChars(start, end + 1, dst, 0);
        return String.valueOf(dst);
    }
}
