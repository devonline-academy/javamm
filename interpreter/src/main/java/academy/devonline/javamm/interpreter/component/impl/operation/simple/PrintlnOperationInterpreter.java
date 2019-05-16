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

import academy.devonline.javamm.code.component.Console;
import academy.devonline.javamm.code.component.ExpressionContext;
import academy.devonline.javamm.code.fragment.operation.PrintlnOperation;
import academy.devonline.javamm.interpreter.component.impl.operation.AbstractOperationInterpreter;

import static java.util.Objects.requireNonNull;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
public final class PrintlnOperationInterpreter extends AbstractOperationInterpreter<PrintlnOperation> {

    private final Console console;

    public PrintlnOperationInterpreter(final ExpressionContext expressionContext,
                                       final Console console) {
        super(expressionContext);
        this.console = requireNonNull(console);
    }

    @Override
    public Class<PrintlnOperation> getOperationClass() {
        return PrintlnOperation.class;
    }

    @Override
    protected void interpretOperation(final PrintlnOperation operation) {
        console.outPrintln(
            operation.getExpression()
                .map(expression -> expression.getValue(expressionContext))
                .orElse("")
        );
    }
}
