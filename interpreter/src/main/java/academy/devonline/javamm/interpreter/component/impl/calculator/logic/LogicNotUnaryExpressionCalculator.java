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

package academy.devonline.javamm.interpreter.component.impl.calculator.logic;

import academy.devonline.javamm.code.component.ExpressionContext;
import academy.devonline.javamm.code.fragment.Expression;
import academy.devonline.javamm.code.fragment.operator.UnaryOperator;
import academy.devonline.javamm.interpreter.component.UnaryExpressionCalculator;
import academy.devonline.javamm.interpreter.component.impl.calculator.AbstractUnaryExpressionCalculator;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
public final class LogicNotUnaryExpressionCalculator extends AbstractUnaryExpressionCalculator
    implements UnaryExpressionCalculator {

    public LogicNotUnaryExpressionCalculator() {
        super(UnaryOperator.LOGIC_NOT);
    }

    @Override
    public Object calculate(final ExpressionContext expressionContext,
                            final Expression expression) {
        final Object value = expression.getValue(expressionContext);
        if (value instanceof Boolean) {
            return !(Boolean) value;
        } else {
            throw createNotSupportedTypesError(value);
        }
    }
}
