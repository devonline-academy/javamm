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

import academy.devonline.javamm.code.fragment.expression.BinaryExpression;
import academy.devonline.javamm.interpreter.component.CalculatorFacade;
import academy.devonline.javamm.interpreter.component.ExpressionEvaluator;

import static java.util.Objects.requireNonNull;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
public class BinaryExpressionEvaluator extends AbstractExpressionEvaluator
    implements ExpressionEvaluator<BinaryExpression> {

    private final CalculatorFacade calculatorFacade;

    public BinaryExpressionEvaluator(final CalculatorFacade calculatorFacade) {
        this.calculatorFacade = requireNonNull(calculatorFacade);
    }

    @Override
    public Class<BinaryExpression> getExpressionClass() {
        return BinaryExpression.class;
    }

    @Override
    public Object evaluate(final BinaryExpression expression) {
        return calculatorFacade.calculate(
            getExpressionContext(),
            expression.getOperand1(),
            expression.getOperator(),
            expression.getOperand2());
    }
}
