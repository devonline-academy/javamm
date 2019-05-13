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
import academy.devonline.javamm.compiler.Compiler;
import academy.devonline.javamm.compiler.JavammSyntaxError;
import academy.devonline.javamm.compiler.component.FunctionNameBuilder;
import academy.devonline.javamm.compiler.component.FunctionReader;
import academy.devonline.javamm.compiler.component.SourceLineReader;
import academy.devonline.javamm.compiler.component.impl.error.JavammLineSyntaxError;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import static academy.devonline.javamm.code.fragment.SourceLine.EMPTY_SOURCE_LINE;
import static java.lang.String.format;
import static java.util.Objects.requireNonNull;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
public class CompilerImpl implements Compiler {

    private final FunctionNameBuilder functionNameBuilder;

    private final SourceLineReader sourceLineReader;

    private final FunctionReader functionReader;

    public CompilerImpl(final FunctionNameBuilder functionNameBuilder,
                        final SourceLineReader sourceLineReader,
                        final FunctionReader functionReader) {
        this.functionNameBuilder = requireNonNull(functionNameBuilder);
        this.sourceLineReader = requireNonNull(sourceLineReader);
        this.functionReader = requireNonNull(functionReader);
    }

    @Override
    public ByteCode compile(final SourceCode... sourceCodes) throws JavammSyntaxError {
        final FunctionName mainFunctionName = functionNameBuilder.build("main", List.of(), EMPTY_SOURCE_LINE);
        final Map<FunctionName, DeveloperFunction> functionMap = new LinkedHashMap<>();
        for (final SourceCode sourceCode : sourceCodes) {
            final List<SourceLine> lines = sourceLineReader.read(sourceCode);
            final ListIterator<SourceLine> iterator = lines.listIterator();
            while (iterator.hasNext()) {
                final DeveloperFunction function = functionReader.read(iterator);
                if (functionMap.put(function.getName(), function) != null) {
                    throw new JavammLineSyntaxError(format(
                        "Function '%s' is already defined", function.getName()),
                        function.getDeclarationSourceLine());
                }
            }
        }
        return new ByteCodeImpl(functionMap, mainFunctionName);
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
