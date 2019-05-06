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

package academy.devonline.javamm.interpreter.component.impl.expression.evaluator;

import academy.devonline.javamm.code.fragment.Expression;
import academy.devonline.javamm.code.fragment.Lexeme;
import academy.devonline.javamm.code.fragment.expression.ConstantExpression;
import academy.devonline.javamm.code.fragment.expression.PostfixNotationExpression;
import academy.devonline.javamm.code.fragment.operator.BinaryOperator;
import academy.devonline.javamm.code.fragment.operator.UnaryOperator;
import academy.devonline.javamm.interpreter.component.CalculatorFacade;
import academy.devonline.javamm.interpreter.component.ExpressionEvaluator;

import java.util.ArrayDeque;
import java.util.Deque;

import static java.util.Objects.requireNonNull;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
public class PostfixNotationExpressionEvaluator extends AbstractExpressionEvaluator
    implements ExpressionEvaluator<PostfixNotationExpression> {

    private final CalculatorFacade calculatorFacade;

    public PostfixNotationExpressionEvaluator(final CalculatorFacade calculatorFacade) {
        this.calculatorFacade = requireNonNull(calculatorFacade);
    }

    @Override
    public Class<PostfixNotationExpression> getExpressionClass() {
        return PostfixNotationExpression.class;
    }

    @Override
    public Object evaluate(final PostfixNotationExpression expression) {
        final Deque<Expression> stack = new ArrayDeque<>();
        for (final Lexeme lexeme : expression.getLexemes()) {
            if (lexeme instanceof BinaryOperator) {
                calculateBinaryOperation(stack, (BinaryOperator) lexeme);
            } else if (lexeme instanceof UnaryOperator) {
                calculateUnaryOperation(stack, (UnaryOperator) lexeme);
            } else {
                stack.addFirst((Expression) lexeme);
            }
        }
        return stack.getFirst().getValue(getExpressionContext());
    }

    private void calculateBinaryOperation(final Deque<Expression> stack, final BinaryOperator operator) {
        final Expression operand2 = stack.removeFirst();
        final Expression operand1 = stack.removeFirst();
        final Object value = calculatorFacade.calculate(getExpressionContext(), operand1, operator, operand2);
        stack.addFirst(ConstantExpression.valueOf(value));
    }

    private void calculateUnaryOperation(final Deque<Expression> stack, final UnaryOperator operator) {
        final Expression operand = stack.removeFirst();
        final Object value = calculatorFacade.calculate(getExpressionContext(), operator, operand);
        stack.addFirst(ConstantExpression.valueOf(value));
    }
}
