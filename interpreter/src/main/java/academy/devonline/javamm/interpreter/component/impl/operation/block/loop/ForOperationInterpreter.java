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

package academy.devonline.javamm.interpreter.component.impl.operation.block.loop;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */

import academy.devonline.javamm.code.component.ExpressionContext;
import academy.devonline.javamm.code.fragment.operation.Block;
import academy.devonline.javamm.code.fragment.operation.ForOperation;
import academy.devonline.javamm.code.fragment.operation.VariableDeclarationOperation;
import academy.devonline.javamm.interpreter.component.CalculatorFacade;
import academy.devonline.javamm.interpreter.model.LocalContext;

import static academy.devonline.javamm.interpreter.model.CurrentRuntimeProvider.getCurrentRuntime;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
public class ForOperationInterpreter extends AbstractLoopBlockOperationInterpreter<ForOperation> {

    public ForOperationInterpreter(final ExpressionContext expressionContext,
                                   final CalculatorFacade calculatorFacade) {
        super(expressionContext, calculatorFacade);
    }

    @Override
    public Class<ForOperation> getOperationClass() {
        return ForOperation.class;
    }

    @Override
    protected void interpretOperation(final ForOperation operation) {
        if (operation.getInitOperation().isPresent() &&
            operation.getInitOperation().get() instanceof VariableDeclarationOperation) {
            final LocalContext currentLocalContext = getCurrentRuntime().getCurrentLocalContext();
            try {
                getCurrentRuntime().setCurrentLocalContext(currentLocalContext.createChildLocalContext());
                interpretForOperation(operation);
            } finally {
                getCurrentRuntime().setCurrentLocalContext(currentLocalContext);
            }
        } else {
            interpretForOperation(operation);
        }
    }

    private void interpretForOperation(final ForOperation operation) {
        for (interpretInitOperation(operation); isConditionTrue(operation); interpretUpdateOperation(operation)) {
            interpretLoopBody(operation);
        }
    }

    private void interpretInitOperation(final ForOperation operation) {
        operation.getInitOperation().ifPresent(init ->
            getBlockOperationInterpreter().interpret(new Block(init, init.getSourceLine())));
    }

    private void interpretUpdateOperation(final ForOperation operation) {
        operation.getUpdateOperation().ifPresent(update ->
            getBlockOperationInterpreter().interpret(new Block(update, update.getSourceLine())));
    }
}
