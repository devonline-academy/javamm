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

package academy.devonline.javamm.compiler.component.impl.expression.builder;

import academy.devonline.javamm.code.fragment.Lexeme;
import academy.devonline.javamm.code.fragment.Operator;
import academy.devonline.javamm.code.fragment.Parenthesis;
import academy.devonline.javamm.code.fragment.SourceLine;
import academy.devonline.javamm.code.fragment.expression.ComplexExpression;
import academy.devonline.javamm.code.fragment.expression.PostfixNotationComplexExpression;
import academy.devonline.javamm.code.fragment.operator.UnaryOperator;
import academy.devonline.javamm.compiler.component.ComplexExpressionBuilder;
import academy.devonline.javamm.compiler.component.PrecedenceOperatorResolver;
import academy.devonline.javamm.compiler.component.impl.error.JavammLineSyntaxError;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.joining;

/**
 * Infix      ->   Postfix
 * 7 - 2 * 3  ->   7 2 3 * -
 *
 * @author devonline
 * @link http://devonline.academy/javamm
 * @link http://interactivepython.org/runestone/static/pythonds/BasicDS/InfixPrefixandPostfixExpressions.html
 */
public class PostfixNotationComplexExpressionBuilder implements ComplexExpressionBuilder {

    private final PrecedenceOperatorResolver precedenceOperatorResolver;

    public PostfixNotationComplexExpressionBuilder(final PrecedenceOperatorResolver precedenceOperatorResolver) {
        this.precedenceOperatorResolver = requireNonNull(precedenceOperatorResolver);
    }

    @Override
    public ComplexExpression build(final List<Lexeme> lexemes, final SourceLine sourceLine) {
        final List<Lexeme> result = new ArrayList<>();
        final Deque<Lexeme> stack = new ArrayDeque<>();
        for (final Lexeme lexeme : lexemes) {
            if (lexeme instanceof Operator) {
                popMorePrecedenceOperators((Operator) lexeme, result, stack);
                stack.addFirst(lexeme);
            } else if (lexeme instanceof Parenthesis) {
                final Parenthesis parenthesis = (Parenthesis) lexeme;
                if (parenthesis.isOpen()) {
                    stack.addFirst(parenthesis);
                } else {
                    popUntilParenthesisFound(result, stack, sourceLine);
                }
            } else {
                result.add(lexeme);
            }
        }
        popAllOperators(result, stack, sourceLine);
        return new PostfixNotationComplexExpression(
            result,
            lexemes.stream().map(Object::toString).collect(joining(" "))
        );
    }

    private void popMorePrecedenceOperators(final Operator currentOperator,
                                            final List<Lexeme> result,
                                            final Deque<Lexeme> stack) {
        while (!stack.isEmpty()) {
            final Lexeme headStackElement = stack.getFirst();
            if (headStackElement instanceof Operator) {
                final Operator headStackOperator = (Operator) headStackElement;
                if (headStackOperator instanceof UnaryOperator && currentOperator instanceof UnaryOperator) {
                    break;
                } else {
                    final int currentOperatorPrecedence = precedenceOperatorResolver.getPrecedence(currentOperator);
                    final int headStackOperatorPrecedence = precedenceOperatorResolver.getPrecedence(headStackOperator);
                    if (currentOperatorPrecedence <= headStackOperatorPrecedence) {
                        result.add(stack.removeFirst());
                    } else {
                        break;
                    }
                }
            } else {
                break;
            }
        }
    }

    private void popUntilParenthesisFound(final List<Lexeme> result,
                                          final Deque<Lexeme> stack,
                                          final SourceLine sourceLine) {
        while (!stack.isEmpty()) {
            final Lexeme lexeme = stack.removeFirst();
            if (lexeme == Parenthesis.OPENING_PARENTHESIS) {
                return;
            } else {
                result.add(lexeme);
            }
        }
        throw new JavammLineSyntaxError("Missing (", sourceLine);
    }

    private void popAllOperators(final List<Lexeme> result,
                                 final Deque<Lexeme> stack,
                                 final SourceLine sourceLine) {
        while (!stack.isEmpty()) {
            final Lexeme headStackElement = stack.removeFirst();
            if (headStackElement instanceof Operator) {
                result.add(headStackElement);
            } else {
                throw new JavammLineSyntaxError("Missing )", sourceLine);
            }
        }
    }
}
