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

package academy.devonline.javamm.interpreter.component.impl.calculator.arithmetic;

import academy.devonline.javamm.code.component.ExpressionContext;
import academy.devonline.javamm.code.fragment.Expression;
import academy.devonline.javamm.code.fragment.operator.UnaryOperator;
import academy.devonline.javamm.interpreter.component.UnaryExpressionCalculator;
import academy.devonline.javamm.interpreter.component.impl.calculator.AbstractUnaryExpressionCalculator;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
public final class MinusUnaryExpressionCalculator extends AbstractUnaryExpressionCalculator
        implements UnaryExpressionCalculator {

    public MinusUnaryExpressionCalculator() {
        super(UnaryOperator.ARITHMETIC_UNARY_MINUS);
    }

    @Override
    public Object calculate(final ExpressionContext expressionContext,
                            final Expression expression) {
        final Object value = expression.getValue(expressionContext);
        if (value instanceof Integer) {
            return -(Integer) value;
        } else if (value instanceof Double) {
            return -(Double) value;
        } else {
            throw createNotSupportedTypesError(value);
        }
    }
}
