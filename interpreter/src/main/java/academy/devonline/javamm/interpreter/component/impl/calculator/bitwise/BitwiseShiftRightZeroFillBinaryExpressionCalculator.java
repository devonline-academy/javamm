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

package academy.devonline.javamm.interpreter.component.impl.calculator.bitwise;

import academy.devonline.javamm.code.component.ExpressionContext;
import academy.devonline.javamm.code.fragment.Expression;
import academy.devonline.javamm.code.fragment.operator.BinaryOperator;
import academy.devonline.javamm.interpreter.component.BinaryExpressionCalculator;
import academy.devonline.javamm.interpreter.component.impl.calculator.AbstractBinaryExpressionCalculator;

import static academy.devonline.javamm.code.fragment.operator.BinaryOperator.ASSIGNMENT_BITWISE_SHIFT_RIGHT_ZERO_FILL;
import static academy.devonline.javamm.code.fragment.operator.BinaryOperator.BITWISE_SHIFT_RIGHT_ZERO_FILL;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
public final class BitwiseShiftRightZeroFillBinaryExpressionCalculator extends AbstractBinaryExpressionCalculator
    implements BinaryExpressionCalculator {

    private BitwiseShiftRightZeroFillBinaryExpressionCalculator(final BinaryOperator operator) {
        super(operator);
    }

    public static BitwiseShiftRightZeroFillBinaryExpressionCalculator createArithmeticCalculator() {
        return new BitwiseShiftRightZeroFillBinaryExpressionCalculator(BITWISE_SHIFT_RIGHT_ZERO_FILL);
    }

    public static BitwiseShiftRightZeroFillBinaryExpressionCalculator createAssignmentCalculator() {
        return new BitwiseShiftRightZeroFillBinaryExpressionCalculator(ASSIGNMENT_BITWISE_SHIFT_RIGHT_ZERO_FILL);
    }

    @Override
    public Object calculate(final ExpressionContext expressionContext,
                            final Expression expression1,
                            final Expression expression2) {
        final Object value1 = expression1.getValue(expressionContext);
        final Object value2 = expression2.getValue(expressionContext);
        if (value1 instanceof Integer && value2 instanceof Integer) {
            return (Integer) value1 >>> (Integer) value2;
        } else {
            throw createNotSupportedTypesError(value1, value2);
        }
    }
}
