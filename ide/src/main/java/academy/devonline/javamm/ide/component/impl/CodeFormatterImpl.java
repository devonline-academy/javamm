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

package academy.devonline.javamm.ide.component.impl;

import academy.devonline.javamm.code.util.SortedOperatorMapBuilder;
import academy.devonline.javamm.code.util.StringIterator;
import academy.devonline.javamm.ide.component.CodeFormatter;

import java.util.ArrayList;
import java.util.Collections;
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
import static academy.devonline.javamm.code.syntax.Delimiters.trim;
import static academy.devonline.javamm.ide.util.TabReplaceUtils.replaceTabulations;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
public class CodeFormatterImpl implements CodeFormatter {

    private static final List<String> NOT_REQUIRED_WHITESPACE_BEFORE_TOKENS =
        List.of("(", ")", "[", "]", ",", ";", ":", "--", "++");

    private static final List<String> NOT_REQUIRED_WHITESPACE_AFTER_TOKENS =
        List.of("(", "[", "!", "--", "++", "~");

    private final Map<String, SortedSet<String>> sortedOperatorMap =
        new SortedOperatorMapBuilder(SIGNIFICANT_TOKEN_DELIMITERS)
            .buildUnmodifiableMapWithSortedValues((o1, o2) -> {
                final int res = o2.length() - o1.length();
                return res == 0 ? o1.compareTo(o2) : res;
            });

    private final String tab = replaceTabulations("\t");

    CodeFormatterImpl() {
    }

    @Override
    public List<String> getFormattedCode(final List<String> sourceCode) {
        final List<String> formattedLines = new ArrayList<>(sourceCode.size());
        int tabCount = 0;
        boolean multilineCommentStarted = false;
        for (final String row : sourceCode) {
            final String trimmedRow = trim(row);
            final FormatResult result = parseLine(trimmedRow, multilineCommentStarted);
            if (trimmedRow.endsWith("}") && !trimmedRow.contains("{")) {
                tabCount--;
            }
            addFormattedLineIfNotEmpty(formattedLines, getFormattedLine(tabCount, result));
            if (trimmedRow.endsWith("{")) {
                tabCount++;
            }
            multilineCommentStarted = result.multilineCommentStarted;
        }
        return Collections.unmodifiableList(formattedLines);
    }

    private String getFormattedLine(final int tabCount, final FormatResult result) {
        return tab.repeat(tabCount < 0 ? 0 : tabCount) + result.formattedLine;
    }

    private void addFormattedLineIfNotEmpty(final List<String> formattedLines,
                                            final String formattedLine) {
        if (formattedLine.trim().isEmpty() && !formattedLines.isEmpty()) {
            if (!formattedLines.get(formattedLines.size() - 1).trim().isEmpty()) {
                formattedLines.add(formattedLine);
            }
        } else {
            formattedLines.add(formattedLine);
        }
    }

    private FormatResult parseLine(final String line,
                                   final boolean multilineCommentStarted) {
        if (isLineBlank(line)) {
            return new FormatResult("", multilineCommentStarted);
        } else if (!multilineCommentStarted && isStartLineComment(line)) {
            return new FormatResult(line, false);
        } else {
            final FormatResult.Builder resultBuilder = new FormatResult.Builder(multilineCommentStarted);
            final StringIterator iterator = new StringIterator(line);
            populateResultBuilder(resultBuilder, iterator);
            return resultBuilder.build();
        }
    }

    private void populateResultBuilder(final FormatResult.Builder resultBuilder,
                                       final StringIterator iterator) {
        final StringBuilder tokenBuilder = new StringBuilder();
        while (iterator.hasNext()) {
            final char ch = iterator.next();
            if (resultBuilder.multilineCommentStarted) {
                checkIfMultilineCommentEnds(resultBuilder, iterator, tokenBuilder, ch);
            } else if (isIgnoredDelimiter(ch)) {
                addTokenIfPresent(resultBuilder, tokenBuilder);
            } else if (isStringDelimiter(ch)) {
                addTokenIfPresent(resultBuilder, tokenBuilder);
                resultBuilder.appendFormattedLine(readStringToken(ch, iterator));
            } else if (isOperator(ch)) {
                addTokenIfPresent(resultBuilder, tokenBuilder);
                final String operator = readOperatorToken(ch, iterator);
                if (START_LINE_COMMENT.equals(operator)) {
                    resultBuilder.multilineCommentStarted = false;
                    resultBuilder.formattedLineBuilder.append(START_LINE_COMMENT).append(iterator.tail());
                    return;
                } else if (START_MULTILINE_COMMENT.equals(operator)) {
                    resultBuilder.multilineCommentStarted = true;
                    tokenBuilder.append(operator);
                } else {
                    resultBuilder.appendFormattedLine(operator);
                }
            } else {
                tokenBuilder.append(ch);
            }
        }
        addTokenIfPresent(resultBuilder, tokenBuilder);
    }

    private void checkIfMultilineCommentEnds(final FormatResult.Builder resultBuilder,
                                             final StringIterator iterator,
                                             final StringBuilder tokenBuilder,
                                             final char ch) {
        if (END_MULTILINE_COMMENT.charAt(0) == ch &&
            END_MULTILINE_COMMENT.equals(readOperatorToken(ch, iterator))) {
            resultBuilder.multilineCommentStarted = false;
            tokenBuilder.append(END_MULTILINE_COMMENT);
            addTokenIfPresent(resultBuilder, tokenBuilder);
            return;
        }
        tokenBuilder.append(ch);
    }

    private boolean isLineBlank(final String line) {
        return line.chars().allMatch(ch -> IGNORED_DELIMITERS.contains((char) ch));
    }

    private boolean isStartLineComment(final String line) {
        return line.startsWith(START_LINE_COMMENT);
    }

    private boolean isOperator(final char ch) {
        return sortedOperatorMap.containsKey(String.valueOf(ch));
    }

    private boolean isIgnoredDelimiter(final char ch) {
        return IGNORED_DELIMITERS.contains(ch);
    }

    private boolean isStringDelimiter(final char ch) {
        return STRING_DELIMITERS.contains(ch);
    }

    private String readStringToken(final char firstChar,
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

    private String readOperatorToken(final char operator,
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

    private void addTokenIfPresent(final FormatResult.Builder resultBuilder,
                                   final StringBuilder tokenBuilder) {
        if (tokenBuilder.length() > 0) {
            resultBuilder.appendFormattedLine(tokenBuilder.toString());
            tokenBuilder.delete(0, tokenBuilder.length());
        }
    }

    /**
     * @author devonline
     * @link http://devonline.academy/javamm
     */
    private static final class FormatResult {

        private final String formattedLine;

        private final boolean multilineCommentStarted;

        private FormatResult(final String formattedLine,
                             final boolean multilineCommentStarted) {
            this.formattedLine = formattedLine;
            this.multilineCommentStarted = multilineCommentStarted;
        }

        /**
         * @author devonline
         * @link http://devonline.academy/javamm
         */
        private static final class Builder {

            private final StringBuilder formattedLineBuilder = new StringBuilder();

            private boolean multilineCommentStarted;

            private Builder(final boolean multilineCommentStarted) {
                this.multilineCommentStarted = multilineCommentStarted;
            }

            private void appendFormattedLine(final String value) {
                if (formattedLineBuilder.length() == 0) {
                    formattedLineBuilder.append(value);
                } else {
                    if (NOT_REQUIRED_WHITESPACE_BEFORE_TOKENS.contains(value)) {
                        final int lastCharIndex = formattedLineBuilder.length() - 1;
                        if (formattedLineBuilder.charAt(lastCharIndex) == ' ') {
                            formattedLineBuilder.deleteCharAt(lastCharIndex);
                        }
                    }
                    formattedLineBuilder.append(value);
                }
                if (!NOT_REQUIRED_WHITESPACE_AFTER_TOKENS.contains(value)) {
                    formattedLineBuilder.append(' ');
                }
            }

            private FormatResult build() {
                return new FormatResult(formattedLineBuilder.toString().trim(), multilineCommentStarted);
            }
        }
    }
}


