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
import academy.devonline.javamm.code.fragment.operator.BinaryOperator;
import academy.devonline.javamm.code.fragment.operator.UnaryOperator;
import academy.devonline.javamm.compiler.component.LexemeBuilder;
import academy.devonline.javamm.compiler.component.SingleTokenExpressionBuilder;
import academy.devonline.javamm.compiler.component.impl.error.JavammLineSyntaxError;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static academy.devonline.javamm.code.fragment.operator.BinaryOperator.ARITHMETIC_ADDITION;
import static academy.devonline.javamm.code.fragment.operator.BinaryOperator.ARITHMETIC_SUBTRACTION;
import static java.lang.String.format;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.joining;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
public class LexemeBuilderImpl implements LexemeBuilder {

    private static final Set<Operator> AMBIGUOUS_OPERATORS = Set.of(
        ARITHMETIC_ADDITION,
        ARITHMETIC_SUBTRACTION
    );

    private final SingleTokenExpressionBuilder singleTokenExpressionBuilder;

    public LexemeBuilderImpl(final SingleTokenExpressionBuilder singleTokenExpressionBuilder) {
        this.singleTokenExpressionBuilder = requireNonNull(singleTokenExpressionBuilder);
    }

    @Override
    public List<Lexeme> build(final List<String> tokens, final SourceLine sourceLine) {
        final List<Lexeme> result = new ArrayList<>();
        for (final String token : tokens) {
            final Lexeme lexeme = buildSimpleLexeme(token, sourceLine);
            if (lexeme instanceof Operator && AMBIGUOUS_OPERATORS.contains(lexeme) &&
                (result.isEmpty() || isOperatorOrOpeningParenthesis(result.get(result.size() - 1)))) {
                result.add(UnaryOperator.of(((Operator) lexeme).getCode()).orElseThrow());
            } else {
                result.add(lexeme);
            }
        }
        return List.copyOf(result);
    }

    private boolean isOperatorOrOpeningParenthesis(final Lexeme lexeme) {
        return lexeme instanceof Operator || lexeme == Parenthesis.OPENING_PARENTHESIS;
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
