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

package academy.devonline.javamm.compiler;

import academy.devonline.javamm.compiler.component.BlockOperationReader;
import academy.devonline.javamm.compiler.component.ExpressionBuilder;
import academy.devonline.javamm.compiler.component.ExpressionResolver;
import academy.devonline.javamm.compiler.component.OperationReader;
import academy.devonline.javamm.compiler.component.SingleTokenExpressionBuilder;
import academy.devonline.javamm.compiler.component.SourceLineReader;
import academy.devonline.javamm.compiler.component.TokenParser;
import academy.devonline.javamm.compiler.component.impl.BlockOperationReaderImpl;
import academy.devonline.javamm.compiler.component.impl.CompilerImpl;
import academy.devonline.javamm.compiler.component.impl.ExpressionResolverImpl;
import academy.devonline.javamm.compiler.component.impl.SingleTokenExpressionBuilderImpl;
import academy.devonline.javamm.compiler.component.impl.SourceLineReaderImpl;
import academy.devonline.javamm.compiler.component.impl.TokenParserImpl;
import academy.devonline.javamm.compiler.component.impl.operation.simple.PrintlnOperationReader;

import java.util.Set;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
public class CompilerConfigurator {

    private TokenParser tokenParser = new TokenParserImpl();

    private SourceLineReader sourceLineReader = new SourceLineReaderImpl(tokenParser);

    private SingleTokenExpressionBuilder singleTokenExpressionBuilder = new SingleTokenExpressionBuilderImpl();

    private Set<ExpressionBuilder> expressionBuilders = Set.of(
        singleTokenExpressionBuilder
    );

    private ExpressionResolver expressionResolver = new ExpressionResolverImpl(expressionBuilders);

    private Set<OperationReader> operationReaders = Set.of(
        new PrintlnOperationReader(expressionResolver)
    );

    private BlockOperationReader blockOperationReader = new BlockOperationReaderImpl(operationReaders);

    private Compiler compiler = new CompilerImpl(sourceLineReader, blockOperationReader);

    public Compiler getCompiler() {
        return compiler;
    }
}
