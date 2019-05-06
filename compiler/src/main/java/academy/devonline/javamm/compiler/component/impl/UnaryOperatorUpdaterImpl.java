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
import academy.devonline.javamm.code.fragment.expression.UnaryPostfixAssignmentExpression;
import academy.devonline.javamm.code.fragment.expression.UnaryPrefixAssignmentExpression;
import academy.devonline.javamm.code.fragment.expression.VariableExpression;
import academy.devonline.javamm.code.fragment.operator.UnaryOperator;
import academy.devonline.javamm.compiler.component.UnaryOperatorUpdater;
import academy.devonline.javamm.compiler.component.impl.error.JavammLineSyntaxError;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static academy.devonline.javamm.code.fragment.operator.BinaryOperator.ARITHMETIC_ADDITION;
import static academy.devonline.javamm.code.fragment.operator.BinaryOperator.ARITHMETIC_SUBTRACTION;
import static academy.devonline.javamm.code.fragment.operator.UnaryOperator.ARITHMETIC_UNARY_MINUS;
import static academy.devonline.javamm.code.fragment.operator.UnaryOperator.ARITHMETIC_UNARY_PLUS;
import static academy.devonline.javamm.code.fragment.operator.UnaryOperator.DECREMENT;
import static academy.devonline.javamm.code.fragment.operator.UnaryOperator.INCREMENT;
import static academy.devonline.javamm.compiler.component.impl.util.SyntaxValidationUtils.validateThatLexemeIsVariableExpression;
import static java.lang.String.format;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
public class UnaryOperatorUpdaterImpl implements UnaryOperatorUpdater {

    private static final Set<UnaryOperator> INCREMENT_OR_DECREMENT = Set.of(
        INCREMENT, DECREMENT
    );

    private static final Set<Operator> AMBIGUOUS_OPERATORS = Set.of(
        ARITHMETIC_UNARY_PLUS,
        ARITHMETIC_UNARY_MINUS,

        ARITHMETIC_ADDITION,
        ARITHMETIC_SUBTRACTION
    );

    @Override
    public List<Lexeme> update(final List<Lexeme> lexemes, final SourceLine sourceLine) {
        final ArrayList<Lexeme> result = replaceUnaryPlusAndMinusIfFound(lexemes);
        findAndReplaceIncrementOrDecrement(result, sourceLine);
        return List.copyOf(result);
    }

    private ArrayList<Lexeme> replaceUnaryPlusAndMinusIfFound(final List<Lexeme> lexemes) {
        final ArrayList<Lexeme> result = new ArrayList<>();
        for (final Lexeme lexeme : lexemes) {
            if (lexeme instanceof Operator && AMBIGUOUS_OPERATORS.contains(lexeme) &&
                (result.isEmpty() || isOperatorOrOpeningParenthesis(result.get(result.size() - 1)))) {
                result.add(UnaryOperator.of(((Operator) lexeme).getCode()).orElseThrow());
            } else {
                result.add(lexeme);
            }
        }
        return result;
    }

    /*
    a ++        ->      a | ++ | null
    --------------------------------------
    ++ a        ->      null | ++ | a
    --------------------------------------
    a ++ + 5    ->      null | a | ++
                        a | ++ | +
                        a++ | + | 5
                        + | 5 | null
    --------------------------------------
    5 + a ++    ->      null | 5 | +
                        5 | + | a
                        + | a | ++
                        a | ++ | null
    --------------------------------------
    5 + a ++ + 5->      null | 5 | +
                        5 | + | a
                        + | a | ++
                        a | ++ | +
                        a++ | + | 5
                        + | 5 | null
    --------------------------------------
    ++ a + 5    ->      null | ++ | a
                        ++a | + | 5
                        + | 5 | null
    --------------------------------------
    5 + ++ a    ->      null | 5 | +
                        5 | + | ++
                        + | ++ | a
    --------------------------------------
    5 + ++ a + 5->      null | 5 | +
                        5 | + | ++
                        + | ++ | a
                        ++a | + | 5
                        + | 5 | null
    --------------------------------------
    + 5 + ++ a + ++ a + ++ a + 5

                ->      null | + | 5
                        + | 5 | +
                        5 | + | ++
                        + | ++ | a
                        ++a | + | ++
                        + | ++ | a
                        ++a | + | ++
                        + | ++ | a
                        ++a | + | 5
                        + | 5 | null
     */
    private void findAndReplaceIncrementOrDecrement(final ArrayList<Lexeme> result,
                                                    final SourceLine sourceLine) {
        for (int i = 0; i < result.size(); i++) {
            final Lexeme current = result.get(i);
            if (current instanceof UnaryOperator && INCREMENT_OR_DECREMENT.contains(current)) {
                final UnaryOperator unaryOperator = (UnaryOperator) current;
                final Lexeme prev = i > 0 ? result.get(i - 1) : null;
                final Lexeme next = i < result.size() - 1 ? result.get(i + 1) : null;
                final Type type = getType(prev, next);
                if (type == Type.PREV) {
                    final VariableExpression variableExpression =
                        validateThatLexemeIsVariableExpression(prev, unaryOperator, sourceLine);
                    result.remove(i);
                    result.set(i - 1, new UnaryPostfixAssignmentExpression(variableExpression, unaryOperator));
                    i--; // do not skip  ->  a++ | + | 5
                } else if (type == Type.NEXT) {
                    final VariableExpression variableExpression =
                        validateThatLexemeIsVariableExpression(next, unaryOperator, sourceLine);
                    result.remove(i);
                    result.set(i, new UnaryPrefixAssignmentExpression(unaryOperator, variableExpression));
                } else {
                    throw new JavammLineSyntaxError(
                        format("An expression is expected for unary operator: '%s'", unaryOperator.getCode()), sourceLine);
                }
            }
        }
    }

    @SuppressWarnings("CheckStyle")
    private Type getType(final Lexeme prev, final Lexeme next) {
        if (prev instanceof Expression && next instanceof Expression) {
            if (prev instanceof VariableExpression) {
                return Type.PREV;
            } else if (next instanceof VariableExpression) {
                return Type.NEXT;
            } else {
                return Type.PREV;
            }
        } else if (prev instanceof Expression) {
            return Type.PREV;
        } else if (next instanceof Expression) {
            return Type.NEXT;
        } else {
            return Type.ERROR;
        }
    }

    private boolean isOperatorOrOpeningParenthesis(final Lexeme lexeme) {
        return lexeme instanceof Operator || lexeme == Parenthesis.OPENING_PARENTHESIS;
    }

    /**
     * @author devonline
     * @link http://devonline.academy/javamm
     */
    private enum Type {

        PREV,

        NEXT,

        ERROR
    }
}
