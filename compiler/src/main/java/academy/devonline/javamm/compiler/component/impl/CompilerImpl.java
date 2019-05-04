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

import academy.devonline.javamm.code.fragment.ByteCode;
import academy.devonline.javamm.code.fragment.SourceCode;
import academy.devonline.javamm.code.fragment.SourceLine;
import academy.devonline.javamm.code.fragment.operation.Block;
import academy.devonline.javamm.compiler.Compiler;
import academy.devonline.javamm.compiler.JavammSyntaxError;
import academy.devonline.javamm.compiler.component.BlockOperationReader;
import academy.devonline.javamm.compiler.component.SourceLineReader;

import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
public class CompilerImpl implements Compiler {

    private final SourceLineReader sourceLineReader;

    private final BlockOperationReader blockOperationReader;

    public CompilerImpl(final SourceLineReader sourceLineReader,
                        final BlockOperationReader blockOperationReader) {
        this.sourceLineReader = requireNonNull(sourceLineReader);
        this.blockOperationReader = requireNonNull(blockOperationReader);
    }

    @Override
    public ByteCode compile(final SourceCode... sourceCodes) throws JavammSyntaxError {
        final List<SourceLine> sourceLines = sourceLineReader.read(sourceCodes[0]);
        final Block block = blockOperationReader.read(SourceLine.EMPTY_SOURCE_LINE, sourceLines.listIterator());
        return () -> block;
    }
}
