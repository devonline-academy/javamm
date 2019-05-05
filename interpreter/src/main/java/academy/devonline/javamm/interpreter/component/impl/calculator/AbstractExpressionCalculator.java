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

package academy.devonline.javamm.interpreter.component.impl.calculator;

import academy.devonline.javamm.code.fragment.Operator;
import academy.devonline.javamm.interpreter.component.ExpressionCalculator;
import academy.devonline.javamm.interpreter.component.impl.error.JavammLineRuntimeError;

import static academy.devonline.javamm.code.util.TypeUtils.getType;
import static java.lang.String.format;
import static java.util.Objects.requireNonNull;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
abstract class AbstractExpressionCalculator implements ExpressionCalculator {

    private final Operator operator;

    AbstractExpressionCalculator(final Operator operator) {
        this.operator = requireNonNull(operator);
    }

    @Override
    public Operator getOperator() {
        return operator;
    }

    protected final JavammLineRuntimeError createNotSupportedTypesError(final Object value1,
                                                                        final Object value2) {
        return new JavammLineRuntimeError(format("Operator '%s' is not supported for types: %s and %s",
            operator.getCode(),
            getType(value1),
            getType(value2)));
    }

    protected final JavammLineRuntimeError createNotSupportedTypesError(final Object value) {
        return new JavammLineRuntimeError(format("Operator '%s' is not supported for type: %s",
            operator.getCode(),
            getType(value)));
    }
}
