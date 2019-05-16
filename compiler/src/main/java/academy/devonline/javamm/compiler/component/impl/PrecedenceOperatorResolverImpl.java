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

package academy.devonline.javamm.compiler.component.impl;

import academy.devonline.javamm.code.exception.ConfigException;
import academy.devonline.javamm.code.fragment.Operator;
import academy.devonline.javamm.code.fragment.operator.BinaryOperator;
import academy.devonline.javamm.code.fragment.operator.UnaryOperator;
import academy.devonline.javamm.compiler.component.PrecedenceOperatorResolver;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Stream;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
public class PrecedenceOperatorResolverImpl implements PrecedenceOperatorResolver {

    private final Map<Operator, Integer> operatorPrecedenceMap;

    public PrecedenceOperatorResolverImpl(final Map<Operator, Integer> operatorPrecedenceMap) {
        validateAllOperators(operatorPrecedenceMap);
        this.operatorPrecedenceMap = Map.copyOf(operatorPrecedenceMap);
    }

    private void validateAllOperators(final Map<Operator, Integer> map) {
        Stream.of(BinaryOperator.values(), UnaryOperator.values())
            .flatMap(Arrays::stream)
            .forEach(operator -> {
                if (!map.containsKey(operator)) {
                    throw new ConfigException("Precedence not defined for " + operator);
                }
            });
    }

    @Override
    public int getPrecedence(final Operator operator) {
        final Integer precedence = operatorPrecedenceMap.get(operator);
        if (precedence == null) {
            throw new ConfigException("Precedence not defined for " + operator.getCode());
        }
        return precedence;
    }
}
