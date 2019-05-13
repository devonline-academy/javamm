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
import academy.devonline.javamm.compiler.component.ComplexExpressionBuilder;
import academy.devonline.javamm.compiler.component.ComplexLexemeValidator;
import academy.devonline.javamm.compiler.component.ExpressionBuilder;
import academy.devonline.javamm.compiler.component.ExpressionOperationBuilder;
import academy.devonline.javamm.compiler.component.ExpressionResolver;
import academy.devonline.javamm.compiler.component.FunctionNameBuilder;
import academy.devonline.javamm.compiler.component.FunctionReader;
import academy.devonline.javamm.compiler.component.LexemeBuilder;
import academy.devonline.javamm.compiler.component.OperationReader;
import academy.devonline.javamm.compiler.component.PrecedenceOperatorResolver;
import academy.devonline.javamm.compiler.component.SingleTokenExpressionBuilder;
import academy.devonline.javamm.compiler.component.SourceLineReader;
import academy.devonline.javamm.compiler.component.TokenParser;
import academy.devonline.javamm.compiler.component.UnaryOperatorUpdater;
import academy.devonline.javamm.compiler.component.VariableBuilder;
import academy.devonline.javamm.compiler.component.impl.BlockOperationReaderImpl;
import academy.devonline.javamm.compiler.component.impl.CompilerImpl;
import academy.devonline.javamm.compiler.component.impl.ComplexLexemeValidatorImpl;
import academy.devonline.javamm.compiler.component.impl.ExpressionOperationBuilderImpl;
import academy.devonline.javamm.compiler.component.impl.ExpressionResolverImpl;
import academy.devonline.javamm.compiler.component.impl.FunctionNameBuilderImpl;
import academy.devonline.javamm.compiler.component.impl.FunctionReaderImpl;
import academy.devonline.javamm.compiler.component.impl.LexemeBuilderImpl;
import academy.devonline.javamm.compiler.component.impl.PrecedenceOperatorResolverImpl;
import academy.devonline.javamm.compiler.component.impl.SingleTokenExpressionBuilderImpl;
import academy.devonline.javamm.compiler.component.impl.SourceLineReaderImpl;
import academy.devonline.javamm.compiler.component.impl.TokenParserImpl;
import academy.devonline.javamm.compiler.component.impl.UnaryOperatorUpdaterImpl;
import academy.devonline.javamm.compiler.component.impl.VariableBuilderImpl;
import academy.devonline.javamm.compiler.component.impl.expression.builder.PostfixNotationComplexExpressionBuilder;
import academy.devonline.javamm.compiler.component.impl.operation.block.DoWhileOperationReader;
import academy.devonline.javamm.compiler.component.impl.operation.block.ForOperationReader;
import academy.devonline.javamm.compiler.component.impl.operation.block.IfElseOperationReader;
import academy.devonline.javamm.compiler.component.impl.operation.block.SimpleBlockOperationReader;
import academy.devonline.javamm.compiler.component.impl.operation.block.SwitchOperationReader;
import academy.devonline.javamm.compiler.component.impl.operation.block.WhileOperationReader;
import academy.devonline.javamm.compiler.component.impl.operation.simple.BreakOperationReader;
import academy.devonline.javamm.compiler.component.impl.operation.simple.ContinueOperationReader;
import academy.devonline.javamm.compiler.component.impl.operation.simple.FinalDeclarationOperationReader;
import academy.devonline.javamm.compiler.component.impl.operation.simple.PrintlnOperationReader;
import academy.devonline.javamm.compiler.component.impl.operation.simple.VariableAssignmentOperationReader;
import academy.devonline.javamm.compiler.component.impl.operation.simple.VariableDeclarationOperationReader;

import java.util.Set;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
public class CompilerConfigurator {

    private TokenParser tokenParser = new TokenParserImpl();

    private SourceLineReader sourceLineReader = new SourceLineReaderImpl(tokenParser);

    private VariableBuilder variableBuilder = new VariableBuilderImpl();

    private PrecedenceOperatorResolver precedenceOperatorResolver = new PrecedenceOperatorResolverImpl();

    private ComplexExpressionBuilder complexExpressionBuilder =
        new PostfixNotationComplexExpressionBuilder(precedenceOperatorResolver);

    private SingleTokenExpressionBuilder singleTokenExpressionBuilder =
        new SingleTokenExpressionBuilderImpl(variableBuilder);

    private LexemeBuilder lexemeBuilder = new LexemeBuilderImpl(singleTokenExpressionBuilder);

    private Set<ExpressionBuilder> expressionBuilders = Set.of(
        singleTokenExpressionBuilder
    );

    private ComplexLexemeValidator complexLexemeValidator = new ComplexLexemeValidatorImpl();

    private UnaryOperatorUpdater unaryOperatorUpdater = new UnaryOperatorUpdaterImpl();

    private ExpressionResolver expressionResolver = new ExpressionResolverImpl(
        expressionBuilders, lexemeBuilder, unaryOperatorUpdater, complexLexemeValidator, complexExpressionBuilder);

    private ExpressionOperationBuilder expressionOperationBuilder = new ExpressionOperationBuilderImpl();

    private Set<OperationReader> operationReaders = Set.of(
        new PrintlnOperationReader(expressionResolver),
        new VariableDeclarationOperationReader(variableBuilder, expressionResolver),
        new FinalDeclarationOperationReader(variableBuilder, expressionResolver),
        new VariableAssignmentOperationReader(expressionResolver),
        new ContinueOperationReader(),
        new BreakOperationReader(),

        new IfElseOperationReader(expressionResolver),
        new WhileOperationReader(expressionResolver),
        new DoWhileOperationReader(expressionResolver),
        new SimpleBlockOperationReader(),
        new ForOperationReader(
            Set.of(
                new PrintlnOperationReader(expressionResolver),
                new VariableDeclarationOperationReader(variableBuilder, expressionResolver),
                new FinalDeclarationOperationReader(variableBuilder, expressionResolver),
                new VariableAssignmentOperationReader(expressionResolver)
            ),
            expressionResolver,
            Set.of(
                new PrintlnOperationReader(expressionResolver),
                new VariableAssignmentOperationReader(expressionResolver)
            ),
            expressionOperationBuilder
        ),
        new SwitchOperationReader(expressionResolver)
    );

    private BlockOperationReader blockOperationReader = new BlockOperationReaderImpl(
        operationReaders,
        expressionResolver,
        expressionOperationBuilder);

    private FunctionNameBuilder functionNameBuilder = new FunctionNameBuilderImpl();

    private int maxFunctionParameterCount = 5;

    private FunctionReader functionReader = new FunctionReaderImpl(
        functionNameBuilder,
        variableBuilder,
        blockOperationReader,
        maxFunctionParameterCount);

    private Compiler compiler = new CompilerImpl(functionNameBuilder, sourceLineReader, functionReader);

    public Compiler getCompiler() {
        return compiler;
    }

    public ComplexExpressionBuilder getComplexExpressionBuilder() {
        return complexExpressionBuilder;
    }

    public ExpressionResolver getExpressionResolver() {
        return expressionResolver;
    }
}
