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

package academy.devonline.javamm.interpreter.component.impl.calculator.predicate;

import academy.devonline.javamm.code.component.ExpressionContext;
import academy.devonline.javamm.code.fragment.Expression;
import academy.devonline.javamm.code.fragment.expression.TypeExpression;
import academy.devonline.javamm.code.fragment.operator.BinaryOperator;
import academy.devonline.javamm.interpreter.component.BinaryExpressionCalculator;
import academy.devonline.javamm.interpreter.component.impl.calculator.AbstractBinaryExpressionCalculator;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
public final class TypeOfBinaryExpressionCalculator extends AbstractBinaryExpressionCalculator
        implements BinaryExpressionCalculator {

    public TypeOfBinaryExpressionCalculator() {
        super(BinaryOperator.PREDICATE_TYPEOF);
    }

    /*
    println ( a typeof integer )
    println ( a typeof typeVariable )
    println ( a typeof getType() )
    println ( a typeof typeArray[0] )
     */
    @Override
    public Object calculate(final ExpressionContext expressionContext,
                            final Expression expression1,
                            final Expression expression2) {
        final Object value1 = expression1.getValue(expressionContext);
        final Object value2 = expression2.getValue(expressionContext);
        if (value2 instanceof TypeExpression) {
            if (value1 == null) {
                return false;
            } else {
                final TypeExpression typeExpression = (TypeExpression) value2;
                return typeExpression.getType().isInstance(value1);
            }
        } else {
            throw createNotSupportedTypesError(value1, value2);
        }
    }
}
