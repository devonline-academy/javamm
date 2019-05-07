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

import academy.devonline.javamm.code.fragment.Variable;
import academy.devonline.javamm.code.fragment.expression.VariableExpression;
import academy.devonline.javamm.interpreter.component.ExpressionEvaluator;
import academy.devonline.javamm.interpreter.component.impl.error.JavammLineRuntimeError;
import academy.devonline.javamm.interpreter.model.LocalContext;

import static academy.devonline.javamm.interpreter.model.CurrentRuntimeProvider.getCurrentRuntime;
import static java.lang.String.format;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
public class VariableExpressionEvaluator implements ExpressionEvaluator<VariableExpression> {

    @Override
    public Class<VariableExpression> getExpressionClass() {
        return VariableExpression.class;
    }

    @Override
    public Object evaluate(final VariableExpression variableExpression) {
        final LocalContext localContext = getCurrentRuntime().getCurrentLocalContext();
        final Variable variable = variableExpression.getVariable();
        if (localContext.isVariableDefined(variable)) {
            return localContext.getVariableValue(variable);
        } else {
            throw new JavammLineRuntimeError(format("Variable '%s' is not defined", variable));
        }
    }
}
