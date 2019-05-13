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

import academy.devonline.javamm.code.fragment.Lexeme;
import academy.devonline.javamm.code.fragment.Operator;
import academy.devonline.javamm.code.fragment.Parenthesis;
import academy.devonline.javamm.code.fragment.SourceLine;
import academy.devonline.javamm.code.fragment.expression.FunctionInvokeExpression;
import academy.devonline.javamm.code.fragment.operator.BinaryOperator;
import academy.devonline.javamm.code.fragment.operator.UnaryOperator;
import academy.devonline.javamm.compiler.component.ExpressionResolver;
import academy.devonline.javamm.compiler.component.FunctionNameBuilder;
import academy.devonline.javamm.compiler.component.LexemeBuilder;
import academy.devonline.javamm.compiler.component.SingleTokenExpressionBuilder;
import academy.devonline.javamm.compiler.component.impl.error.JavammLineSyntaxError;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;
import java.util.stream.Collectors;

import static academy.devonline.javamm.compiler.component.impl.util.SyntaxParseUtils.getTokensBetweenBracketsWhenOpeningOneAlreadyFound;
import static academy.devonline.javamm.compiler.component.impl.util.SyntaxParseUtils.groupTokensByComma;
import static java.lang.String.format;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.joining;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
public class LexemeBuilderImpl implements LexemeBuilder {

    private final SingleTokenExpressionBuilder singleTokenExpressionBuilder;

    private final FunctionNameBuilder functionNameBuilder;

    private ExpressionResolver expressionResolver;

    public LexemeBuilderImpl(final SingleTokenExpressionBuilder singleTokenExpressionBuilder,
                             final FunctionNameBuilder functionNameBuilder) {
        this.singleTokenExpressionBuilder = requireNonNull(singleTokenExpressionBuilder);
        this.functionNameBuilder = requireNonNull(functionNameBuilder);
    }

    @Override
    public void setExpressionResolver(final ExpressionResolver expressionResolver) {
        this.expressionResolver = expressionResolver;
    }

    @Override
    public List<Lexeme> build(final List<String> tokens, final SourceLine sourceLine) {
        final List<Lexeme> lexemes = new ArrayList<>();
        final ListIterator<String> iterator = tokens.listIterator();
        while (iterator.hasNext()) {
            final String currentLexeme = iterator.next();
            if (iterator.hasNext()) {
                processCurrentAndNextTokens(lexemes, iterator, currentLexeme, sourceLine);
            } else {
                lexemes.add(buildSimpleLexeme(currentLexeme, sourceLine));
            }
        }
        return List.copyOf(lexemes);
    }

    private void processCurrentAndNextTokens(final List<Lexeme> lexemes,
                                             final ListIterator<String> iterator,
                                             final String currentLexeme,
                                             final SourceLine sourceLine) {
        final String nextLexeme = iterator.next();
        if ("(".equals(nextLexeme)) {
            if (functionNameBuilder.isValid(currentLexeme)) {
                lexemes.add(readInvokeFunctionExpression(currentLexeme, iterator, sourceLine));
            } else {
                iterator.previous();
                lexemes.add(buildSimpleLexeme(currentLexeme, sourceLine));
            }
        } else {
            iterator.previous();
            lexemes.add(buildSimpleLexeme(currentLexeme, sourceLine));
        }
    }

    private Lexeme readInvokeFunctionExpression(final String functionName,
                                                final ListIterator<String> iterator,
                                                final SourceLine sourceLine) {
        final List<String> functionParams =
            getTokensBetweenBracketsWhenOpeningOneAlreadyFound("(", ")", iterator, sourceLine, true);
        final List<List<String>> params = groupTokensByComma(functionParams, sourceLine);
        return new FunctionInvokeExpression(
            functionNameBuilder.build(functionName, params, sourceLine),
            params.stream()
                .map(p -> expressionResolver.resolve(p, sourceLine))
                .collect(Collectors.toUnmodifiableList())
        );
    }

    @SuppressWarnings("unchecked")
    private Lexeme buildSimpleLexeme(final String token,
                                     final SourceLine sourceLine) {
        final Optional<Operator> optionalOperator =
            ((Optional) BinaryOperator.of(token)).or(() -> UnaryOperator.of(token));
        if (optionalOperator.isPresent()) {
            return optionalOperator.get();
        } else {
            final Optional<Parenthesis> optionalParenthesis = Parenthesis.of(token);
            if (optionalParenthesis.isPresent()) {
                return optionalParenthesis.get();
            } else {
                if (singleTokenExpressionBuilder.canBuild(List.of(token))) {
                    return singleTokenExpressionBuilder.build(List.of(token), sourceLine);
                } else {
                    throw new JavammLineSyntaxError(createErrorMessage(token), sourceLine);
                }
            }
        }
    }

    private String createErrorMessage(final String token) {
        return format(
            "Unsupported token: %s (%s)",
            token,
            token.codePoints().mapToObj(v -> "0x" + format("%04x", v)).collect(joining(" "))
        );
    }
}
