/*
 * Copyright (c) 2019. http://devonline.academy
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package academy.devonline.javamm.interpreter.component.impl.calculator.logic;

import academy.devonline.javamm.code.component.ExpressionContext;
import academy.devonline.javamm.code.fragment.Expression;
import academy.devonline.javamm.code.fragment.operator.BinaryOperator;
import academy.devonline.javamm.interpreter.component.BinaryExpressionCalculator;
import academy.devonline.javamm.interpreter.component.impl.calculator.AbstractBinaryExpressionCalculator;
import academy.devonline.javamm.interpreter.component.impl.error.JavammLineRuntimeError;

import static academy.devonline.javamm.code.util.TypeUtils.getType;
import static java.lang.String.format;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
public final class LogicOrBinaryExpressionCalculator extends AbstractBinaryExpressionCalculator
        implements BinaryExpressionCalculator {

    public LogicOrBinaryExpressionCalculator() {
        super(BinaryOperator.LOGIC_OR);
    }

    @Override
    public Object calculate(final ExpressionContext expressionContext,
                            final Expression expression1,
                            final Expression expression2) {
        final Object value1 = expression1.getValue(expressionContext);
        if (value1 instanceof Boolean) {
            if (!(Boolean) value1) {
                final Object value2 = expression2.getValue(expressionContext);
                if (value2 instanceof Boolean) {
                    return value2;
                } else {
                    throw createNotSupportedTypesError(false, value2);
                }
            } else {
                return Boolean.TRUE;
            }
        } else {
            throw new JavammLineRuntimeError(format(
                    "First operand for operator '%s' has invalid type: %s",
                    getOperator().getCode(), getType(value1)));
        }
    }
}
