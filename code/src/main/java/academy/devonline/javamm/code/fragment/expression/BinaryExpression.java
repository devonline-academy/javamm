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

import academy.devonline.javamm.code.fragment.Expression;
import academy.devonline.javamm.code.fragment.operator.BinaryOperator;

import static java.lang.String.format;
import static java.util.Objects.requireNonNull;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
public class BinaryExpression implements Expression {

    private final Expression operand1;

    private final BinaryOperator operator;

    private final Expression operand2;

    public BinaryExpression(final Expression operand1,
                            final BinaryOperator operator,
                            final Expression operand2) {
        this.operand1 = requireNonNull(operand1);
        this.operator = requireNonNull(operator);
        this.operand2 = requireNonNull(operand2);
    }

    public Expression getOperand1() {
        return operand1;
    }

    public BinaryOperator getOperator() {
        return operator;
    }

    public Expression getOperand2() {
        return operand2;
    }

    @Override
    public String toString() {
        return format("%s %s %s", operand1, operator, operand2);
    }
}
