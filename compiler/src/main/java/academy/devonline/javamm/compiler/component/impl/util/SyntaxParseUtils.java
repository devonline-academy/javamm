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

import java.util.List;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
public final class SyntaxParseUtils {

    private SyntaxParseUtils() {
    }

    public static List<String> getTokensBetweenBrackets(final String openingBracket,
                                                        final String closingBracket,
                                                        final SourceLine sourceLine,
                                                        final boolean allowEmptyResult) {
        return getTokensBetweenBrackets(
            openingBracket, closingBracket, sourceLine.getTokens(), sourceLine, allowEmptyResult
        );
    }

    public static List<String> getTokensBetweenBrackets(final String openingBracket,
                                                        final String closingBracket,
                                                        final List<String> tokens,
                                                        final SourceLine sourceLine,
                                                        final boolean allowEmptyResult) {
        return List.copyOf(tokens);
    }
}
