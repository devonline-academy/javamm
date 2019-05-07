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

import academy.devonline.javamm.code.component.ExpressionContext;
import academy.devonline.javamm.code.fragment.operation.AbstractLoopOperation;
import academy.devonline.javamm.interpreter.component.CalculatorFacade;
import academy.devonline.javamm.interpreter.component.impl.operation.block.AbstractBlockOperationInterpreter;

import static academy.devonline.javamm.interpreter.model.CurrentRuntimeProvider.getCurrentRuntime;
import static java.util.Objects.requireNonNull;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
abstract class AbstractLoopBlockOperationInterpreter<T extends AbstractLoopOperation>
    extends AbstractBlockOperationInterpreter<T> {

    private final CalculatorFacade calculatorFacade;

    AbstractLoopBlockOperationInterpreter(final ExpressionContext expressionContext,
                                          final CalculatorFacade calculatorFacade) {
        super(expressionContext);
        this.calculatorFacade = requireNonNull(calculatorFacade);
    }

    final boolean isConditionTrue(final AbstractLoopOperation loopOperation) {
        return calculatorFacade.isTrue(expressionContext, loopOperation.getCondition());
    }

    final void interpretLoopBody(final AbstractLoopOperation loopOperation) {
        checkForTerminate();
        interpretBlock(loopOperation.getBody());
        getCurrentRuntime().setCurrentOperation(loopOperation);
    }
}
