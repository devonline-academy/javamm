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

import academy.devonline.javamm.code.fragment.ByteCode;
import academy.devonline.javamm.code.fragment.FunctionName;
import academy.devonline.javamm.code.fragment.function.DeveloperFunction;
import academy.devonline.javamm.interpreter.Interpreter;
import academy.devonline.javamm.interpreter.JavammRuntimeError;
import academy.devonline.javamm.interpreter.TerminateInterpreterException;
import academy.devonline.javamm.interpreter.component.BlockOperationInterpreter;
import academy.devonline.javamm.interpreter.component.RuntimeBuilder;
import academy.devonline.javamm.interpreter.component.impl.error.JavammLineRuntimeError;
import academy.devonline.javamm.interpreter.component.impl.error.JavammStructRuntimeError;
import academy.devonline.javamm.interpreter.component.impl.operation.exception.InterruptOperationException;
import academy.devonline.javamm.interpreter.model.CurrentRuntime;
import academy.devonline.javamm.interpreter.model.LocalContext;

import java.util.Optional;

import static academy.devonline.javamm.code.syntax.Keywords.FUNCTION;
import static academy.devonline.javamm.interpreter.model.CurrentRuntimeProvider.releaseCurrentRuntime;
import static academy.devonline.javamm.interpreter.model.CurrentRuntimeProvider.setCurrentRuntime;
import static java.lang.String.format;
import static java.util.Objects.requireNonNull;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
public class InterpreterImpl implements Interpreter {

    private final BlockOperationInterpreter blockOperationInterpreter;

    private final RuntimeBuilder runtimeBuilder;

    public InterpreterImpl(final BlockOperationInterpreter blockOperationInterpreter,
                           final RuntimeBuilder runtimeBuilder) {
        this.blockOperationInterpreter = requireNonNull(blockOperationInterpreter);
        this.runtimeBuilder = requireNonNull(runtimeBuilder);
    }

    @Override
    public void interpret(final ByteCode byteCode) throws JavammRuntimeError, TerminateInterpreterException {
        final FunctionName mainFunctionName = byteCode.getMainFunctionName();
        final Optional<DeveloperFunction> mainFunctionOptional = byteCode.getFunction(mainFunctionName);
        if (mainFunctionOptional.isPresent()) {
            final CurrentRuntime currentRuntime = runtimeBuilder.buildCurrentRuntime();
            final LocalContext localContext = runtimeBuilder.buildLocalContext();
            currentRuntime.setCurrentLocalContext(localContext);

            final DeveloperFunction mainFunction = mainFunctionOptional.get();
            currentRuntime.setCurrentSourceLine(mainFunction.getDeclarationSourceLine());
            setCurrentRuntime(currentRuntime);

            try {
                blockOperationInterpreter.interpret(mainFunction.getBody());
            } catch (final InterruptOperationException exception) {
                throw new JavammLineRuntimeError(format("Operation '%s' not expected here", exception.getOperation()));
            } finally {
                releaseCurrentRuntime();
            }
        } else {
            throw new JavammStructRuntimeError(
                format("Main function not found, please define the main function as: '%s %s'",
                    FUNCTION, mainFunctionName));
        }
    }
}
