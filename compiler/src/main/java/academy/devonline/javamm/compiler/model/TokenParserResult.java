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

package academy.devonline.javamm.compiler.model;

import java.util.List;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
public final class TokenParserResult {

    private final List<String> tokens;

    private final boolean multilineCommentStarted;

    public TokenParserResult(final List<String> tokens, final boolean multilineCommentStarted) {
        this.tokens = List.copyOf(tokens);
        this.multilineCommentStarted = multilineCommentStarted;
    }

    public TokenParserResult(final boolean multilineCommentStarted) {
        this(List.of(), multilineCommentStarted);
    }

    public List<String> getTokens() {
        return tokens;
    }

    public boolean isNotEmpty() {
        return !tokens.isEmpty();
    }

    public boolean isMultilineCommentStarted() {
        return multilineCommentStarted;
    }
}
