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

import academy.devonline.javamm.code.fragment.Expression;
import academy.devonline.javamm.code.fragment.Lexeme;
import academy.devonline.javamm.code.fragment.Operator;
import academy.devonline.javamm.code.fragment.Parenthesis;
import academy.devonline.javamm.code.fragment.SourceLine;
import academy.devonline.javamm.code.fragment.operator.BinaryOperator;
import academy.devonline.javamm.code.fragment.operator.UnaryOperator;
import academy.devonline.javamm.compiler.component.ComplexLexemeValidator;
import academy.devonline.javamm.compiler.component.impl.error.JavammLineSyntaxError;

import java.util.List;
import java.util.ListIterator;

import static academy.devonline.javamm.code.fragment.Parenthesis.CLOSING_PARENTHESIS;
import static academy.devonline.javamm.code.fragment.Parenthesis.OPENING_PARENTHESIS;
import static academy.devonline.javamm.compiler.component.impl.util.SyntaxValidationUtils.validateThatLexemeIsVariableExpression;
import static java.lang.String.format;
import static java.util.stream.Collectors.toList;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
public class ComplexLexemeValidatorImpl implements ComplexLexemeValidator {

    @Override
    public void validate(final List<Lexeme> lexemes, final SourceLine sourceLine) {
        requireNotEmpty(lexemes);
        validateThatFirstLexemeIsNotBinaryOperator(lexemes, sourceLine);
        validateThatLastLexemeIsNotOperator(lexemes, sourceLine);

        final ListIterator<Lexeme> iterator = lexemes.listIterator();
        while (iterator.hasNext()) {
            final Lexeme current = iterator.next();
            if (iterator.hasNext()) {
                final Lexeme next = iterator.next();
                //
                validateThatExpressionFoundBetweenBinaryOperators(current, next, sourceLine);
                validateThatBinaryOperatorFoundBetweenExpressions(current, next, sourceLine);
                validateThatExpressionFoundForBinaryOperatorAfterOpeningParenthesis(current, next, sourceLine);
                validateThatExpressionFoundForOperatorBeforeClosingParenthesis(current, next, sourceLine);
                validateThatExpressionFoundBetweenUnaryAndBinaryOperators(current, next, sourceLine);
                validateThatParenthesesAreCorrectlyPlaced(current, next, sourceLine);
                validateThatBinaryAssigmentOperatorAppliedToVariableExpression(current, next, sourceLine);

                iterator.previous();
            }
        }

        validateThatBinaryOperatorFoundBetweenExpressionsIgnoringParentheses(lexemes, sourceLine);
    }

    private void validateThatBinaryAssigmentOperatorAppliedToVariableExpression(final Lexeme current,
                                                                                final Lexeme next,
                                                                                final SourceLine sourceLine) {
        if (next instanceof BinaryOperator && ((BinaryOperator) next).isAssignment()) {
            validateThatLexemeIsVariableExpression(current, (Operator) next, sourceLine);
        }
    }

    private void validateThatBinaryOperatorFoundBetweenExpressionsIgnoringParentheses(final List<Lexeme> lexemes,
                                                                                      final SourceLine sourceLine) {
        final List<Lexeme> lexemesWithoutParentheses = lexemes.stream()
            .filter(l -> !(l instanceof Parenthesis))
            .collect(toList());
        final ListIterator<Lexeme> iterator = lexemesWithoutParentheses.listIterator();
        while (iterator.hasNext()) {
            final Lexeme current = iterator.next();
            if (iterator.hasNext()) {
                final Lexeme next = iterator.next();
                validateThatBinaryOperatorFoundBetweenExpressions(current, next, sourceLine);
                iterator.previous();
            }
        }
    }

    private void validateThatParenthesesAreCorrectlyPlaced(final Lexeme current,
                                                           final Lexeme next,
                                                           final SourceLine sourceLine) {
        if (current instanceof Parenthesis && next instanceof Parenthesis) {
            if (current != next) {
                throw new JavammLineSyntaxError("Parentheses are incorrectly placed", sourceLine);
            }
        }
    }

    private void validateThatExpressionFoundBetweenUnaryAndBinaryOperators(final Lexeme current,
                                                                           final Lexeme next,
                                                                           final SourceLine sourceLine) {
        if (current instanceof UnaryOperator && next instanceof BinaryOperator) {
            throw new JavammLineSyntaxError(
                format("An expression is expected for binary operator: '%s'",
                    ((BinaryOperator) next).getCode()), sourceLine);
        }
    }

    private void validateThatExpressionFoundForOperatorBeforeClosingParenthesis(final Lexeme current,
                                                                                final Lexeme next,
                                                                                final SourceLine sourceLine) {
        if (current instanceof Operator && next == CLOSING_PARENTHESIS) {
            final Operator operator = (Operator) current;
            throw new JavammLineSyntaxError(
                format("An expression is expected for %s operator: '%s'", operator.getType(), operator.getCode()),
                sourceLine);
        }
    }

    private void validateThatExpressionFoundForBinaryOperatorAfterOpeningParenthesis(final Lexeme current,
                                                                                     final Lexeme next,
                                                                                     final SourceLine sourceLine) {
        if (current == OPENING_PARENTHESIS && next instanceof BinaryOperator) {
            throw new JavammLineSyntaxError(
                format("An expression is expected for binary operator: '%s'",
                    ((BinaryOperator) next).getCode()), sourceLine);
        }
    }

    private void validateThatBinaryOperatorFoundBetweenExpressions(final Lexeme current,
                                                                   final Lexeme next,
                                                                   final SourceLine sourceLine) {
        if (current instanceof Expression && next instanceof Expression) {
            throw new JavammLineSyntaxError(
                format("A binary operator is expected between expressions: '%s' and '%s'", current, next), sourceLine);
        }
    }

    private void validateThatExpressionFoundBetweenBinaryOperators(final Lexeme current,
                                                                   final Lexeme next,
                                                                   final SourceLine sourceLine) {
        if (current instanceof BinaryOperator && next instanceof BinaryOperator) {
            throw new JavammLineSyntaxError(
                format("An expression is expected between binary operators: '%s' and '%s'",
                    ((BinaryOperator) current).getCode(), ((BinaryOperator) next).getCode()), sourceLine);
        }
    }

    private void validateThatLastLexemeIsNotOperator(final List<Lexeme> lexemes,
                                                     final SourceLine sourceLine) {
        final Lexeme last = lexemes.get(lexemes.size() - 1);
        if (last instanceof Operator) {
            final Operator operator = (Operator) last;
            throw new JavammLineSyntaxError(
                format("Expression can't end with %s operator: '%s'", operator.getType(), operator.getCode()),
                sourceLine);
        }
    }

    private void validateThatFirstLexemeIsNotBinaryOperator(final List<Lexeme> lexemes,
                                                            final SourceLine sourceLine) {
        final Lexeme first = lexemes.get(0);
        if (first instanceof BinaryOperator) {
            throw new JavammLineSyntaxError(
                format("Expression can't start with binary operator: '%s'",
                    ((BinaryOperator) first).getCode()), sourceLine);
        }
    }

    private void requireNotEmpty(final List<Lexeme> lexemes) {
        if (lexemes.isEmpty()) {
            throw new IllegalArgumentException("Empty lexemes not allowed");
        }
    }
}
