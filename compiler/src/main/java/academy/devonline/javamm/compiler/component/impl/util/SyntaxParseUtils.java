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

import java.util.Collections;
import java.util.List;

import static java.lang.String.format;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
public final class SyntaxParseUtils {

    private static final String MISSING_TEMPLATE = "Missing %s";

    private SyntaxParseUtils() {
    }

    public static List<String> getTokensBetweenBrackets(final String firstOpeningBracket,
                                                        final String lastClosingBracket,
                                                        final SourceLine sourceLine,
                                                        final boolean allowEmptyResult) {
        return getTokensBetweenBrackets(
            firstOpeningBracket, lastClosingBracket, sourceLine.getTokens(), sourceLine, allowEmptyResult
        );
    }

    public static List<String> getTokensBetweenBrackets(final String firstOpeningBracket,
                                                        final String lastClosingBracket,
                                                        final List<String> tokens,
                                                        final SourceLine sourceLine,
                                                        final boolean allowEmptyResult) {
        if (tokens.isEmpty()) {
            throw new JavammLineSyntaxError(format(MISSING_TEMPLATE, firstOpeningBracket), sourceLine);
        }
        final int start = tokens.indexOf(firstOpeningBracket) + 1;
        if (start == 0) {
            throw new JavammLineSyntaxError(format(MISSING_TEMPLATE, firstOpeningBracket), sourceLine);
        }
        final int end = tokens.lastIndexOf(lastClosingBracket);
        if (end == -1) {
            throw new JavammLineSyntaxError(format(MISSING_TEMPLATE, lastClosingBracket), sourceLine);
        }
        if (start < end) {
            return Collections.unmodifiableList(tokens.subList(start, end));
        } else if (start == end) {
            if (allowEmptyResult) {
                return List.of();
            } else {
                throw new JavammLineSyntaxError(format(
                    "An expression is expected between %s and %s",
                    firstOpeningBracket, lastClosingBracket), sourceLine);
            }
        } else {
            throw new JavammLineSyntaxError(format(
                "Expected %s before %s", firstOpeningBracket, lastClosingBracket), sourceLine);
        }
    }
}
