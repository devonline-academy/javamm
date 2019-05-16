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
import academy.devonline.javamm.code.fragment.Operation;
import academy.devonline.javamm.code.fragment.operation.Block;
import academy.devonline.javamm.interpreter.component.BlockOperationInterpreter;
import academy.devonline.javamm.interpreter.component.BlockOperationInterpreterAware;
import academy.devonline.javamm.interpreter.component.impl.operation.AbstractOperationInterpreter;
import academy.devonline.javamm.interpreter.model.LocalContext;

import static academy.devonline.javamm.interpreter.model.CurrentRuntimeProvider.getCurrentRuntime;
import static java.util.Objects.requireNonNull;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
public abstract class AbstractBlockOperationInterpreter<T extends Operation> extends AbstractOperationInterpreter<T>
    implements BlockOperationInterpreterAware {

    private BlockOperationInterpreter blockOperationInterpreter;

    protected AbstractBlockOperationInterpreter(final ExpressionContext expressionContext) {
        super(expressionContext);
    }

    protected final BlockOperationInterpreter getBlockOperationInterpreter() {
        return requireNonNull(blockOperationInterpreter, "blockOperationInterpreter is not set");
    }

    @Override
    public final void setBlockOperationInterpreter(final BlockOperationInterpreter blockOperationInterpreter) {
        this.blockOperationInterpreter = requireNonNull(blockOperationInterpreter);
    }

    protected final void interpretBlock(final Block block) {
        final LocalContext currentLocalContext = getCurrentRuntime().getCurrentLocalContext();
        try {
            getCurrentRuntime().setCurrentLocalContext(currentLocalContext.createChildLocalContext());
            getBlockOperationInterpreter().interpret(block);
        } finally {
            getCurrentRuntime().setCurrentLocalContext(currentLocalContext);
        }
    }
}
