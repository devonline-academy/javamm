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

package academy.devonline.javamm.compiler.component.impl;

import academy.devonline.javamm.code.component.AbstractFunctionStorage;
import academy.devonline.javamm.code.fragment.ByteCode;
import academy.devonline.javamm.code.fragment.FunctionName;
import academy.devonline.javamm.code.fragment.SourceCode;
import academy.devonline.javamm.code.fragment.SourceLine;
import academy.devonline.javamm.code.fragment.function.DeveloperFunction;
import academy.devonline.javamm.code.fragment.operation.Block;
import academy.devonline.javamm.compiler.Compiler;
import academy.devonline.javamm.compiler.JavammSyntaxError;
import academy.devonline.javamm.compiler.component.BlockOperationReader;
import academy.devonline.javamm.compiler.component.FunctionNameBuilder;
import academy.devonline.javamm.compiler.component.SourceLineReader;

import java.util.List;
import java.util.Map;

import static java.util.Objects.requireNonNull;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
public class CompilerImpl implements Compiler {

    private final FunctionNameBuilder functionNameBuilder;

    private final SourceLineReader sourceLineReader;

    private final BlockOperationReader blockOperationReader;

    public CompilerImpl(final FunctionNameBuilder functionNameBuilder,
                        final SourceLineReader sourceLineReader,
                        final BlockOperationReader blockOperationReader) {
        this.functionNameBuilder = requireNonNull(functionNameBuilder);
        this.sourceLineReader = requireNonNull(sourceLineReader);
        this.blockOperationReader = requireNonNull(blockOperationReader);
    }

    @Override
    public ByteCode compile(final SourceCode... sourceCodes) throws JavammSyntaxError {
        final SourceCode sourceCode = sourceCodes[0];
        final List<SourceLine> sourceLines = sourceLineReader.read(sourceCode);
        final SourceLine sourceLine = new SourceLine(sourceCode.getModuleName(), 0, List.of());
        final Block block = blockOperationReader.read(sourceLine, sourceLines.listIterator(), false);

        final FunctionName mainFunctionName = functionNameBuilder.build("main", List.of(), sourceLine);
        final DeveloperFunction mainFunction = new DeveloperFunction.Builder()
            .setName(mainFunctionName)
            .setBody(block)
            .build();
        return new ByteCodeImpl(Map.of(mainFunctionName, mainFunction), mainFunctionName);
    }

    /**
     * @author devonline
     * @link http://devonline.academy/javamm
     */
    private static final class ByteCodeImpl extends AbstractFunctionStorage<DeveloperFunction> implements ByteCode {

        private final FunctionName mainFunctionName;

        private ByteCodeImpl(final Map<FunctionName, DeveloperFunction> functionMap,
                             final FunctionName mainFunctionName) {
            super(functionMap);
            this.mainFunctionName = requireNonNull(mainFunctionName);
        }

        @Override
        public FunctionName getMainFunctionName() {
            return mainFunctionName;
        }
    }
}
