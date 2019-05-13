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
import academy.devonline.javamm.code.fragment.SourceLine;
import academy.devonline.javamm.code.fragment.expression.FunctionInvokeExpression;
import academy.devonline.javamm.code.fragment.expression.PostfixNotationComplexExpression;
import academy.devonline.javamm.code.fragment.expression.UnaryPostfixAssignmentExpression;
import academy.devonline.javamm.code.fragment.expression.UnaryPrefixAssignmentExpression;
import academy.devonline.javamm.code.fragment.expression.VariableExpression;
import academy.devonline.javamm.code.fragment.operation.ExpressionOperation;
import academy.devonline.javamm.code.fragment.operator.BinaryOperator;
import academy.devonline.javamm.compiler.component.ExpressionOperationBuilder;
import academy.devonline.javamm.compiler.component.impl.error.JavammLineSyntaxError;

import java.util.List;
import java.util.Set;

import static java.lang.String.format;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
public class ExpressionOperationBuilderImpl implements ExpressionOperationBuilder {

    private final Set<Class<? extends Expression>> allowedExpressionSet = Set.of(
        UnaryPrefixAssignmentExpression.class,
        UnaryPostfixAssignmentExpression.class,
        FunctionInvokeExpression.class
    );

    @Override
    public ExpressionOperation build(final Expression expression, final SourceLine sourceLine) {
        if (isValid(expression)) {
            return new ExpressionOperation(sourceLine, expression);
        } else {
            throw new JavammLineSyntaxError(
                format("Expression '%s' is not allowed here", sourceLine.getCommand()), sourceLine);
        }
    }

    private boolean isValid(final Expression expression) {
        if (allowedExpressionSet.contains(expression.getClass())) {
            return true;
        }
        if (expression instanceof PostfixNotationComplexExpression) {
            final List<Lexeme> lexemes = ((PostfixNotationComplexExpression) expression).getLexemes();
            if (lexemes.size() > 2) {
                final Lexeme last = lexemes.get(lexemes.size() - 1);
                return lexemes.get(0) instanceof VariableExpression &&
                    last instanceof BinaryOperator &&
                    ((BinaryOperator) last).isAssignment();
            }
        }
        return false;
    }
}
