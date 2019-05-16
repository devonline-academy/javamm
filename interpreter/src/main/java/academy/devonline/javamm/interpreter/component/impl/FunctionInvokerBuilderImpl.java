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
import academy.devonline.javamm.code.fragment.Expression;
import academy.devonline.javamm.code.fragment.FunctionName;
import academy.devonline.javamm.code.fragment.function.DeveloperFunction;
import academy.devonline.javamm.interpreter.component.DeveloperFunctionInvoker;
import academy.devonline.javamm.interpreter.component.FunctionInvoker;
import academy.devonline.javamm.interpreter.component.FunctionInvokerBuilder;
import academy.devonline.javamm.interpreter.component.impl.error.JavammLineRuntimeError;
import academy.devonline.javamm.interpreter.component.impl.error.JavammStructRuntimeError;

import java.util.List;
import java.util.Optional;

import static academy.devonline.javamm.code.syntax.Keywords.FUNCTION;
import static java.lang.String.format;
import static java.util.Objects.requireNonNull;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
public class FunctionInvokerBuilderImpl implements FunctionInvokerBuilder {

    private final DeveloperFunctionInvoker developerFunctionInvoker;

    public FunctionInvokerBuilderImpl(final DeveloperFunctionInvoker developerFunctionInvoker) {
        this.developerFunctionInvoker = requireNonNull(developerFunctionInvoker);
    }

    @Override
    public FunctionInvoker build(final ByteCode byteCode) {
        return new FunctionInvokerImpl(developerFunctionInvoker, byteCode);
    }

    /**
     * @author devonline
     * @link http://devonline.academy/javamm
     */
    private static final class FunctionInvokerImpl implements FunctionInvoker {

        private final DeveloperFunctionInvoker developerFunctionInvoker;

        private final ByteCode byteCode;

        private FunctionInvokerImpl(final DeveloperFunctionInvoker developerFunctionInvoker,
                                    final ByteCode byteCode) {
            this.byteCode = requireNonNull(byteCode);
            this.developerFunctionInvoker = requireNonNull(developerFunctionInvoker);
        }

        @Override
        public Object invoke(final FunctionName functionName,
                             final List<Expression> arguments,
                             final boolean isMainFunction) {
            final Optional<DeveloperFunction> developerFunctionOptional = byteCode.getFunction(functionName);
            if (developerFunctionOptional.isPresent()) {
                final DeveloperFunction developerFunction = developerFunctionOptional.get();
                if (isMainFunction) {
                    return developerFunctionInvoker.invokeMain(developerFunction);
                } else {
                    return developerFunctionInvoker.invoke(developerFunction, arguments);
                }
            } else {
                if (isMainFunction) {
                    throw new JavammStructRuntimeError(format(
                        "Main function not found, please define the main function as: '%s %s'",
                        FUNCTION, functionName));
                } else {
                    throw new JavammLineRuntimeError(format("Function '%s' not defined", functionName));
                }
            }
        }
    }
}
