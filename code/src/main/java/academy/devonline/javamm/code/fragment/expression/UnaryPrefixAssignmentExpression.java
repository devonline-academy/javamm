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

package academy.devonline.javamm.code.fragment.expression;

import academy.devonline.javamm.code.component.ExpressionContext;
import academy.devonline.javamm.code.fragment.Expression;
import academy.devonline.javamm.code.fragment.operator.UnaryOperator;

import java.util.List;

import static java.lang.String.format;
import static java.util.Objects.requireNonNull;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
public class UnaryPrefixAssignmentExpression implements Expression {

    private final VariableExpression operand;

    private final UnaryOperator operator;

    public UnaryPrefixAssignmentExpression(final UnaryOperator operator,
                                           final VariableExpression operand) {
        this.operand = requireNonNull(operand);
        if (!operator.isAssignment()) {
            throw new IllegalArgumentException(format("'%s' is not assignment operator", operator.getCode()));
        }
        this.operator = operator;
    }

    public final VariableExpression getOperand() {
        return operand;
    }

    public final UnaryOperator getOperator() {
        return operator;
    }

    @Override
    public Object getValue(final ExpressionContext expressionContext) {
        final Object value = new PostfixNotationComplexExpression(List.of(operand, operator), toString())
            .getValue(expressionContext);
        operand.setValue(expressionContext, value);
        return value;
    }

    @Override
    public String toString() {
        return operator + operand.toString();
    }
}
