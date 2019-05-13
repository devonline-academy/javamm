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

import academy.devonline.javamm.code.fragment.expression.FunctionInvokeExpression;
import academy.devonline.javamm.interpreter.component.ExpressionEvaluator;
import academy.devonline.javamm.interpreter.component.FunctionInvoker;

import static academy.devonline.javamm.interpreter.model.CurrentRuntimeProvider.getCurrentRuntime;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
public class FunctionInvokeExpressionEvaluator implements ExpressionEvaluator<FunctionInvokeExpression> {

    @Override
    public Class<FunctionInvokeExpression> getExpressionClass() {
        return FunctionInvokeExpression.class;
    }

    @Override
    public Object evaluate(final FunctionInvokeExpression expression) {
        final FunctionInvoker functionInvoker = getCurrentRuntime().getCurrentFunctionInvoker();
        return functionInvoker.invoke(expression.getFunctionName(), expression.getArguments(), false);
    }
}
