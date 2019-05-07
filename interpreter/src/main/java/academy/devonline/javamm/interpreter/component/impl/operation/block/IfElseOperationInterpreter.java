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

package academy.devonline.javamm.interpreter.component.impl.operation.block;

import academy.devonline.javamm.code.component.ExpressionContext;
import academy.devonline.javamm.code.fragment.operation.IfElseOperation;
import academy.devonline.javamm.interpreter.component.CalculatorFacade;

import static java.util.Objects.requireNonNull;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
public class IfElseOperationInterpreter extends AbstractBlockOperationInterpreter<IfElseOperation> {

    private final CalculatorFacade calculatorFacade;

    public IfElseOperationInterpreter(final ExpressionContext expressionContext,
                                      final CalculatorFacade calculatorFacade) {
        super(expressionContext);
        this.calculatorFacade = requireNonNull(calculatorFacade);
    }

    @Override
    public Class<IfElseOperation> getOperationClass() {
        return IfElseOperation.class;
    }

    @Override
    protected void interpretOperation(final IfElseOperation operation) {
        if (calculatorFacade.isTrue(expressionContext, operation.getCondition())) {
            interpretBlock(operation.getTrueBlock());
        } else {
            // Functional
            operation.getFalseBlock().ifPresent(this::interpretBlock);
            /*
            // Imperative
            if(operation.getFalseBlock().isPresent()){
                interpretBlock(operation.getFalseBlock().get());
            }*/
        }
    }
}
