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
import academy.devonline.javamm.code.fragment.expression.UnaryPostfixAssignmentExpression;
import academy.devonline.javamm.code.fragment.expression.UnaryPrefixAssignmentExpression;
import academy.devonline.javamm.code.fragment.expression.VariableExpression;
import academy.devonline.javamm.code.fragment.operator.UnaryOperator;
import academy.devonline.javamm.compiler.component.UnaryOperatorUpdater;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import static academy.devonline.javamm.code.fragment.operator.BinaryOperator.ARITHMETIC_ADDITION;
import static academy.devonline.javamm.code.fragment.operator.BinaryOperator.ARITHMETIC_SUBTRACTION;
import static academy.devonline.javamm.code.fragment.operator.UnaryOperator.ARITHMETIC_UNARY_MINUS;
import static academy.devonline.javamm.code.fragment.operator.UnaryOperator.ARITHMETIC_UNARY_PLUS;
import static academy.devonline.javamm.code.fragment.operator.UnaryOperator.DECREMENT;
import static academy.devonline.javamm.code.fragment.operator.UnaryOperator.INCREMENT;
import static academy.devonline.javamm.compiler.component.impl.util.SyntaxValidationUtils.validateThatLexemeIsVariableExpression;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
public class UnaryOperatorUpdaterImpl implements UnaryOperatorUpdater {

    private static final Set<UnaryOperator> INCREMENT_OR_DECREMENT = Set.of(
        INCREMENT,
        DECREMENT
    );

    private static final Set<Operator> AMBIGUOUS_OPERATORS = Set.of(
        ARITHMETIC_UNARY_PLUS,
        ARITHMETIC_UNARY_MINUS,

        ARITHMETIC_ADDITION,
        ARITHMETIC_SUBTRACTION
    );

    @Override
    public List<Lexeme> update(final List<Lexeme> lexemes, final SourceLine sourceLine) {
        final List<Lexeme> result = new ArrayList<>();
        final ListIterator<Lexeme> iterator = lexemes.listIterator();
        while (iterator.hasNext()) {
            final Lexeme current = iterator.next();
            if (current instanceof Operator && AMBIGUOUS_OPERATORS.contains(current) &&
                (result.isEmpty() || isOperatorOrOpeningParenthesis(result.get(result.size() - 1)))) {
                result.add(UnaryOperator.of(((Operator) current).getCode()).orElseThrow());
            } else {
                if (iterator.hasNext()) {
                    processCurrentAndNext(result, iterator, current, sourceLine);
                } else {
                    result.add(current);
                }
            }
        }
        return result;
    }

    private void processCurrentAndNext(final List<Lexeme> result,
                                       final ListIterator<Lexeme> iterator,
                                       final Lexeme current,
                                       final SourceLine sourceLine) {
        final Lexeme nextLexeme = iterator.next();
        if (current instanceof UnaryOperator &&
            INCREMENT_OR_DECREMENT.contains(current) &&
            !(nextLexeme instanceof Parenthesis)) {
            final VariableExpression variableExpression =
                validateThatLexemeIsVariableExpression(nextLexeme, (Operator) current, sourceLine);
            result.add(new UnaryPrefixAssignmentExpression((UnaryOperator) current, variableExpression));
        } else if (nextLexeme instanceof UnaryOperator &&
            INCREMENT_OR_DECREMENT.contains(nextLexeme) &&
            !(current instanceof Parenthesis)) {
            final VariableExpression variableExpression =
                validateThatLexemeIsVariableExpression(current, (UnaryOperator) nextLexeme, sourceLine);
            result.add(new UnaryPostfixAssignmentExpression(variableExpression, (UnaryOperator) nextLexeme));
        } else {
            iterator.previous();
            result.add(current);
        }
    }

    private boolean isOperatorOrOpeningParenthesis(final Lexeme lexeme) {
        return lexeme instanceof Operator || lexeme == Parenthesis.OPENING_PARENTHESIS;
    }
}
