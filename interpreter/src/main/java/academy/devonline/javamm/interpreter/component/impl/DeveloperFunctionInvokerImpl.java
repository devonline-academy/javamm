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
import academy.devonline.javamm.code.fragment.Expression;
import academy.devonline.javamm.code.fragment.SourceLine;
import academy.devonline.javamm.code.fragment.Variable;
import academy.devonline.javamm.code.fragment.function.DeveloperFunction;
import academy.devonline.javamm.interpreter.component.BlockOperationInterpreter;
import academy.devonline.javamm.interpreter.component.DeveloperFunctionInvoker;
import academy.devonline.javamm.interpreter.component.RuntimeBuilder;
import academy.devonline.javamm.interpreter.component.impl.error.JavammLineRuntimeError;
import academy.devonline.javamm.interpreter.component.impl.operation.exception.InterruptOperationException;
import academy.devonline.javamm.interpreter.model.CurrentRuntime;
import academy.devonline.javamm.interpreter.model.LocalContext;

import java.util.List;

import static academy.devonline.javamm.interpreter.model.CurrentRuntimeProvider.getCurrentRuntime;
import static java.lang.String.format;
import static java.util.Objects.requireNonNull;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
public class DeveloperFunctionInvokerImpl implements DeveloperFunctionInvoker {

    private final RuntimeBuilder runtimeBuilder;

    private final BlockOperationInterpreter blockOperationInterpreter;

    private final ExpressionContext expressionContext;

    public DeveloperFunctionInvokerImpl(final RuntimeBuilder runtimeBuilder,
                                        final BlockOperationInterpreter blockOperationInterpreter,
                                        final ExpressionContext expressionContext) {
        this.runtimeBuilder = requireNonNull(runtimeBuilder);
        this.blockOperationInterpreter = requireNonNull(blockOperationInterpreter);
        this.expressionContext = requireNonNull(expressionContext);
    }

    @Override
    public Object invoke(final DeveloperFunction developerFunction, final List<Expression> arguments) {
        final CurrentRuntime currentRuntime = getCurrentRuntime();
        final SourceLine sourceLine = currentRuntime.getCurrentSourceLine();
        final LocalContext localContext = currentRuntime.getCurrentLocalContext();

        final LocalContext functionLocalContext = runtimeBuilder.buildLocalContext();
        setParameters(developerFunction, arguments, functionLocalContext);
        try {
            currentRuntime.setCurrentSourceLine(developerFunction.getDeclarationSourceLine());
            currentRuntime.setCurrentLocalContext(functionLocalContext);
            return interpretBody(developerFunction);
        } finally {
            currentRuntime.setCurrentLocalContext(localContext);
            currentRuntime.setCurrentSourceLine(sourceLine);
        }
    }

    @Override
    public Object invokeMain(final DeveloperFunction developerFunction) {
        final CurrentRuntime currentRuntime = getCurrentRuntime();
        final LocalContext localContext = runtimeBuilder.buildLocalContext();

        currentRuntime.setCurrentSourceLine(developerFunction.getDeclarationSourceLine());
        currentRuntime.setCurrentLocalContext(localContext);
        return interpretBody(developerFunction);
    }

    protected void setParameters(final DeveloperFunction developerFunction,
                                 final List<Expression> parameters,
                                 final LocalContext functionLocalContext) {
        for (int i = 0; i < developerFunction.getParameters().size(); i++) {
            final Variable variableName = developerFunction.getParameters().get(i);
            final Object value = parameters.get(i).getValue(expressionContext);
            functionLocalContext.setVariableValue(variableName, value);
        }
    }

    protected Object interpretBody(final DeveloperFunction developerFunction) {
        try {
            blockOperationInterpreter.interpret(developerFunction.getBody());
            return null;
        } catch (final InterruptOperationException exception) {
            throw new JavammLineRuntimeError(format("Operation '%s' not expected here", exception.getOperation()));
        }
    }
}
