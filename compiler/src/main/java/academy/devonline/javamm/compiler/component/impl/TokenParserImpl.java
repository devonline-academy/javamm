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

package academy.devonline.javamm.compiler.component.impl;

import academy.devonline.javamm.code.util.SortedOperatorMapBuilder;
import academy.devonline.javamm.code.util.StringIterator;
import academy.devonline.javamm.compiler.component.TokenParser;
import academy.devonline.javamm.compiler.model.TokenParserResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;

import static academy.devonline.javamm.code.syntax.Delimiters.END_MULTILINE_COMMENT;
import static academy.devonline.javamm.code.syntax.Delimiters.IGNORED_DELIMITERS;
import static academy.devonline.javamm.code.syntax.Delimiters.SIGNIFICANT_TOKEN_DELIMITERS;
import static academy.devonline.javamm.code.syntax.Delimiters.START_LINE_COMMENT;
import static academy.devonline.javamm.code.syntax.Delimiters.START_MULTILINE_COMMENT;
import static academy.devonline.javamm.code.syntax.Delimiters.STRING_DELIMITERS;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
public class TokenParserImpl implements TokenParser {

    private final Map<String, SortedSet<String>> sortedOperatorMap =
        new SortedOperatorMapBuilder(SIGNIFICANT_TOKEN_DELIMITERS)
            .buildUnmodifiableMapWithSortedValues((o1, o2) -> {
                final int res = o2.length() - o1.length();
                return res == 0 ? o1.compareTo(o2) : res;
            });

    @Override
    public TokenParserResult parseLine(final String line,
                                       final boolean multilineCommentStarted) {
        if (isLineBlank(line)) {
            return new TokenParserResult(multilineCommentStarted);
        } else if (!multilineCommentStarted && isLineCommentStarted(line)) {
            return new TokenParserResult(false);
        } else {
            final List<String> tokens = new ArrayList<>();
            final StringIterator iterator = new StringIterator(line);
            final boolean multilineCommentStartedResult = extractTokens(
                tokens, iterator, multilineCommentStarted
            );
            return new TokenParserResult(tokens, multilineCommentStartedResult);
        }
    }

    // Internal implementation!
    private boolean extractTokens(final List<String> tokens,
                                  final StringIterator iterator,
                                  final boolean multilineCommentStarted) {
        final StringBuilder tokenBuilder = new StringBuilder();
        boolean multilineCommentStartedResult = multilineCommentStarted;
        while (iterator.hasNext()) {
            final char ch = iterator.next();
            if (multilineCommentStartedResult) {
                if (END_MULTILINE_COMMENT.charAt(0) == ch &&
                    END_MULTILINE_COMMENT.equals(readOperatorToken(ch, iterator))) {
                    multilineCommentStartedResult = false;
                }
            } else if (isIgnoredDelimiter(ch)) {
                addTokenIfPresent(tokens, tokenBuilder);
            } else if (isStringDelimiter(ch)) {
                addTokenIfPresent(tokens, tokenBuilder);
                tokens.add(readStringToken(ch, iterator));
            } else if (isOperator(ch)) {
                addTokenIfPresent(tokens, tokenBuilder);
                final String operator = readOperatorToken(ch, iterator);
                if (START_LINE_COMMENT.equals(operator)) {
                    return false;
                } else if (START_MULTILINE_COMMENT.equals(operator)) {
                    multilineCommentStartedResult = true;
                } else {
                    tokens.add(operator);
                }
            } else {
                tokenBuilder.append(ch);
            }
        }
        addTokenIfPresent(tokens, tokenBuilder);
        return multilineCommentStartedResult;
    }

    protected boolean isLineBlank(final String line) {
        return line.chars().allMatch(ch -> IGNORED_DELIMITERS.contains((char) ch));
    }

    protected boolean isLineCommentStarted(final String line) {
        return line.startsWith(START_LINE_COMMENT);
    }

    protected boolean isOperator(final char ch) {
        return sortedOperatorMap.containsKey(String.valueOf(ch));
    }

    protected boolean isIgnoredDelimiter(final char ch) {
        return IGNORED_DELIMITERS.contains(ch);
    }

    protected boolean isStringDelimiter(final char ch) {
        return STRING_DELIMITERS.contains(ch);
    }

    protected String readStringToken(final char firstChar,
                                     final StringIterator iterator) {
        final StringBuilder stringBuilder = new StringBuilder().append(firstChar);
        while (iterator.hasNext()) {
            final char ch = iterator.next();
            stringBuilder.append(ch);
            if (firstChar == ch) {
                return stringBuilder.toString();
            }
        }
        return stringBuilder.toString();
    }

    protected String readOperatorToken(final char operator,
                                       final StringIterator iterator) {
        final String op = String.valueOf(operator);
        final Set<String> possibleOperators = sortedOperatorMap.get(op);
        if (possibleOperators.isEmpty()) {
            return op;
        } else {
            return readOperatorToken(operator, iterator, possibleOperators);
        }
    }

    private String readOperatorToken(final char operator,
                                     final StringIterator iterator,
                                     final Set<String> possibleOperators) {
        final StringBuilder actualValue = new StringBuilder().append(operator);
        fillActualValue(actualValue, iterator, getMaxPossibleOperatorLength(possibleOperators) - 1);
        for (final String possibleOperator : possibleOperators) {
            resetActualValueWithIteratorIfRequired(iterator, actualValue, possibleOperator);
            if (possibleOperator.equals(actualValue.toString())) {
                return possibleOperator;
            }
        }
        final String op = String.valueOf(operator);
        resetActualValueWithIteratorIfRequired(iterator, actualValue, op);
        return op;
    }

    private void resetActualValueWithIteratorIfRequired(final StringIterator iterator,
                                                        final StringBuilder actualValue,
                                                        final String possibleOperator) {
        while (possibleOperator.length() < actualValue.length()) {
            actualValue.deleteCharAt(actualValue.length() - 1);
            iterator.previous();
        }
    }

    private int getMaxPossibleOperatorLength(final Set<String> possibleOperators) {
        return possibleOperators.iterator().next().length();
    }

    private void fillActualValue(final StringBuilder builder,
                                 final StringIterator iterator,
                                 final int count) {
        for (int i = 0; i < count && iterator.hasNext(); i++) {
            builder.append(iterator.next());
        }
    }

    private void addTokenIfPresent(final List<String> tokens,
                                   final StringBuilder tokenBuilder) {
        if (tokenBuilder.length() > 0) {
            tokens.add(tokenBuilder.toString());
            tokenBuilder.delete(0, tokenBuilder.length());
        }
    }
}

