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

package academy.devonline.javamm.interpreter.component.impl;

import academy.devonline.javamm.code.component.ExpressionContext;
import academy.devonline.javamm.code.exception.ConfigException;
import academy.devonline.javamm.code.fragment.Expression;
import academy.devonline.javamm.code.fragment.Operator;
import academy.devonline.javamm.code.fragment.operator.BinaryOperator;
import academy.devonline.javamm.code.fragment.operator.UnaryOperator;
import academy.devonline.javamm.interpreter.component.BinaryExpressionCalculator;
import academy.devonline.javamm.interpreter.component.CalculatorFacade;
import academy.devonline.javamm.interpreter.component.UnaryExpressionCalculator;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;

import static java.util.Objects.requireNonNull;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toUnmodifiableMap;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
public class CalculatorFacadeImpl implements CalculatorFacade {

    private final Map<BinaryOperator, BinaryExpressionCalculator> binaryExpressionCalculatorMap;

    private final Map<UnaryOperator, UnaryExpressionCalculator> unaryExpressionCalculatorMap;

    public CalculatorFacadeImpl(final Set<BinaryExpressionCalculator> binaryExpressionCalculators,
                                final Set<UnaryExpressionCalculator> unaryExpressionCalculators) {
        this.binaryExpressionCalculatorMap = binaryExpressionCalculators.stream()
            .collect(toUnmodifiableMap(BinaryExpressionCalculator::getOperator, identity()));
        //validateAllOperators(this.binaryExpressionCalculatorMap.keySet(), BinaryOperator.values());

        this.unaryExpressionCalculatorMap = unaryExpressionCalculators.stream()
            .collect(toUnmodifiableMap(UnaryExpressionCalculator::getOperator, identity()));
        //validateAllOperators(this.unaryExpressionCalculatorMap.keySet(), UnaryOperator.values());
    }

    private <T extends Operator> void validateAllOperators(final Set<T> operatorSet, final T[] values) {
        Arrays.stream(values).forEach(v -> {
            if (!operatorSet.contains(v)) {
                throw new ConfigException("Missing calculator for operator: " + v);
            }
        });
    }

    @Override
    public Object calculate(final ExpressionContext expressionContext,
                            final Expression operand1,
                            final BinaryOperator operator,
                            final Expression operand2) {
        final BinaryExpressionCalculator calculator = binaryExpressionCalculatorMap.get(requireNonNull(operator));
        return calculator.calculate(expressionContext, operand1, operand2);
    }

    @Override
    public Object calculate(final ExpressionContext expressionContext,
                            final UnaryOperator operator,
                            final Expression operand) {
        final UnaryExpressionCalculator calculator = unaryExpressionCalculatorMap.get(requireNonNull(operator));
        return calculator.calculate(expressionContext, operand);
    }
}
