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
import academy.devonline.javamm.interpreter.Interpreter;
import academy.devonline.javamm.interpreter.JavammRuntimeError;
import academy.devonline.javamm.interpreter.TerminateInterpreterException;
import academy.devonline.javamm.interpreter.component.BlockOperationInterpreter;

import static java.util.Objects.requireNonNull;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
public class InterpreterImpl implements Interpreter {

    private final BlockOperationInterpreter blockOperationInterpreter;

    public InterpreterImpl(final BlockOperationInterpreter blockOperationInterpreter) {
        this.blockOperationInterpreter = requireNonNull(blockOperationInterpreter);
    }

    @Override
    public void interpret(final ByteCode byteCode) throws JavammRuntimeError, TerminateInterpreterException {
        blockOperationInterpreter.interpret(byteCode.getCode());
    }
}
