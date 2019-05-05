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

package academy.devonline.javamm.interpreter.component.impl.operation.simple;

import academy.devonline.javamm.code.component.ExpressionContext;
import academy.devonline.javamm.code.fragment.Variable;
import academy.devonline.javamm.code.fragment.operation.VariableDeclarationOperation;
import academy.devonline.javamm.interpreter.component.impl.error.JavammLineRuntimeError;
import academy.devonline.javamm.interpreter.component.impl.operation.AbstractOperationInterpreter;
import academy.devonline.javamm.interpreter.model.LocalContext;

import static academy.devonline.javamm.interpreter.model.CurrentRuntimeProvider.getCurrentRuntime;
import static java.lang.String.format;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
public class VariableDeclarationOperationInterpreter
    extends AbstractOperationInterpreter<VariableDeclarationOperation> {

    public VariableDeclarationOperationInterpreter(final ExpressionContext expressionContext) {
        super(expressionContext);
    }

    @Override
    public Class<VariableDeclarationOperation> getOperationClass() {
        return VariableDeclarationOperation.class;
    }

    @Override
    public void interpretOperation(final VariableDeclarationOperation operation) {
        final LocalContext localContext = getCurrentRuntime().getCurrentLocalContext();
        final Variable variable = operation.getVariable();
        if (localContext.isVariableDefined(variable)) {
            throw new JavammLineRuntimeError(format("Variable '%s' already defined", variable));
        }
        if (operation.isConstant()) {
            localContext.setFinalValue(variable, operation.getExpression().getValue(expressionContext));
        } else {
            localContext.setVariableValue(variable, operation.getExpression().getValue(expressionContext));
        }
    }
}
