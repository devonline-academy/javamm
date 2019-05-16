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

import academy.devonline.javamm.code.fragment.Operator;
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
import academy.devonline.javamm.compiler.component.impl.operation.simple.ReturnOperationReader;
import academy.devonline.javamm.compiler.component.impl.operation.simple.VariableAssignmentOperationReader;
import academy.devonline.javamm.compiler.component.impl.operation.simple.VariableDeclarationOperationReader;

import java.util.Map;
import java.util.Set;

import static academy.devonline.javamm.code.fragment.operator.BinaryOperator.ARITHMETIC_ADDITION;
import static academy.devonline.javamm.code.fragment.operator.BinaryOperator.ARITHMETIC_DIVISION;
import static academy.devonline.javamm.code.fragment.operator.BinaryOperator.ARITHMETIC_MODULUS;
import static academy.devonline.javamm.code.fragment.operator.BinaryOperator.ARITHMETIC_MULTIPLICATION;
import static academy.devonline.javamm.code.fragment.operator.BinaryOperator.ARITHMETIC_SUBTRACTION;
import static academy.devonline.javamm.code.fragment.operator.BinaryOperator.ASSIGNMENT_ADDITION;
import static academy.devonline.javamm.code.fragment.operator.BinaryOperator.ASSIGNMENT_BITWISE_AND;
import static academy.devonline.javamm.code.fragment.operator.BinaryOperator.ASSIGNMENT_BITWISE_OR;
import static academy.devonline.javamm.code.fragment.operator.BinaryOperator.ASSIGNMENT_BITWISE_SHIFT_LEFT;
import static academy.devonline.javamm.code.fragment.operator.BinaryOperator.ASSIGNMENT_BITWISE_SHIFT_RIGHT;
import static academy.devonline.javamm.code.fragment.operator.BinaryOperator.ASSIGNMENT_BITWISE_SHIFT_RIGHT_ZERO_FILL;
import static academy.devonline.javamm.code.fragment.operator.BinaryOperator.ASSIGNMENT_BITWISE_XOR;
import static academy.devonline.javamm.code.fragment.operator.BinaryOperator.ASSIGNMENT_DIVISION;
import static academy.devonline.javamm.code.fragment.operator.BinaryOperator.ASSIGNMENT_MODULUS;
import static academy.devonline.javamm.code.fragment.operator.BinaryOperator.ASSIGNMENT_MULTIPLICATION;
import static academy.devonline.javamm.code.fragment.operator.BinaryOperator.ASSIGNMENT_SUBTRACTION;
import static academy.devonline.javamm.code.fragment.operator.BinaryOperator.BITWISE_AND;
import static academy.devonline.javamm.code.fragment.operator.BinaryOperator.BITWISE_OR;
import static academy.devonline.javamm.code.fragment.operator.BinaryOperator.BITWISE_SHIFT_LEFT;
import static academy.devonline.javamm.code.fragment.operator.BinaryOperator.BITWISE_SHIFT_RIGHT;
import static academy.devonline.javamm.code.fragment.operator.BinaryOperator.BITWISE_SHIFT_RIGHT_ZERO_FILL;
import static academy.devonline.javamm.code.fragment.operator.BinaryOperator.BITWISE_XOR;
import static academy.devonline.javamm.code.fragment.operator.BinaryOperator.LOGIC_AND;
import static academy.devonline.javamm.code.fragment.operator.BinaryOperator.LOGIC_OR;
import static academy.devonline.javamm.code.fragment.operator.BinaryOperator.PREDICATE_EQUALS;
import static academy.devonline.javamm.code.fragment.operator.BinaryOperator.PREDICATE_GREATER;
import static academy.devonline.javamm.code.fragment.operator.BinaryOperator.PREDICATE_GREATER_OR_EQUALS;
import static academy.devonline.javamm.code.fragment.operator.BinaryOperator.PREDICATE_LESS;
import static academy.devonline.javamm.code.fragment.operator.BinaryOperator.PREDICATE_LESS_OR_EQUALS;
import static academy.devonline.javamm.code.fragment.operator.BinaryOperator.PREDICATE_NOT_EQUALS;
import static academy.devonline.javamm.code.fragment.operator.BinaryOperator.PREDICATE_TYPEOF;
import static academy.devonline.javamm.code.fragment.operator.UnaryOperator.ARITHMETIC_UNARY_MINUS;
import static academy.devonline.javamm.code.fragment.operator.UnaryOperator.ARITHMETIC_UNARY_PLUS;
import static academy.devonline.javamm.code.fragment.operator.UnaryOperator.BITWISE_INVERSE;
import static academy.devonline.javamm.code.fragment.operator.UnaryOperator.DECREMENT;
import static academy.devonline.javamm.code.fragment.operator.UnaryOperator.INCREMENT;
import static academy.devonline.javamm.code.fragment.operator.UnaryOperator.LOGIC_NOT;
import static java.util.Map.entry;
import static java.util.Map.ofEntries;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 * @link https://introcs.cs.princeton.edu/java/11precedence/
 */
@SuppressWarnings("checkstyle:MagicNumber")
public class CompilerConfigurator {

    private static final int MAX_PRECEDENCE = 20;

    private final TokenParser tokenParser = new TokenParserImpl();

    private final SourceLineReader sourceLineReader = new SourceLineReaderImpl(tokenParser);

    private final VariableBuilder variableBuilder = new VariableBuilderImpl();

    // https://introcs.cs.princeton.edu/java/11precedence/
    private final Map<Operator, Integer> operatorPrecedenceMap = ofEntries(
        entry(INCREMENT, MAX_PRECEDENCE - 1),
        entry(DECREMENT, MAX_PRECEDENCE - 1),
        entry(ARITHMETIC_UNARY_PLUS, MAX_PRECEDENCE - 1),
        entry(ARITHMETIC_UNARY_MINUS, MAX_PRECEDENCE - 1),
        entry(LOGIC_NOT, MAX_PRECEDENCE - 1),
        entry(BITWISE_INVERSE, MAX_PRECEDENCE - 1),
        //
        entry(ARITHMETIC_MULTIPLICATION, MAX_PRECEDENCE - 2),
        entry(ARITHMETIC_DIVISION, MAX_PRECEDENCE - 2),
        entry(ARITHMETIC_MODULUS, MAX_PRECEDENCE - 2),
        //
        entry(ARITHMETIC_ADDITION, MAX_PRECEDENCE - 3),
        entry(ARITHMETIC_SUBTRACTION, MAX_PRECEDENCE - 3),
        //
        entry(BITWISE_SHIFT_LEFT, MAX_PRECEDENCE - 4),
        entry(BITWISE_SHIFT_RIGHT, MAX_PRECEDENCE - 4),
        entry(BITWISE_SHIFT_RIGHT_ZERO_FILL, MAX_PRECEDENCE - 4),
        //
        entry(PREDICATE_GREATER, MAX_PRECEDENCE - 5),
        entry(PREDICATE_GREATER_OR_EQUALS, MAX_PRECEDENCE - 5),
        entry(PREDICATE_LESS, MAX_PRECEDENCE - 5),
        entry(PREDICATE_LESS_OR_EQUALS, MAX_PRECEDENCE - 5),
        entry(PREDICATE_TYPEOF, MAX_PRECEDENCE - 5),
        //
        entry(PREDICATE_NOT_EQUALS, MAX_PRECEDENCE - 6),
        entry(PREDICATE_EQUALS, MAX_PRECEDENCE - 6),
        //
        entry(BITWISE_AND, MAX_PRECEDENCE - 7),
        //
        entry(BITWISE_XOR, MAX_PRECEDENCE - 8),
        //
        entry(BITWISE_OR, MAX_PRECEDENCE - 9),
        //
        entry(LOGIC_AND, MAX_PRECEDENCE - 10),
        //
        entry(LOGIC_OR, MAX_PRECEDENCE - 11),
        //
        entry(ASSIGNMENT_MULTIPLICATION, MAX_PRECEDENCE - 12),
        entry(ASSIGNMENT_DIVISION, MAX_PRECEDENCE - 12),
        entry(ASSIGNMENT_MODULUS, MAX_PRECEDENCE - 12),
        entry(ASSIGNMENT_ADDITION, MAX_PRECEDENCE - 12),
        entry(ASSIGNMENT_SUBTRACTION, MAX_PRECEDENCE - 12),
        entry(ASSIGNMENT_BITWISE_SHIFT_LEFT, MAX_PRECEDENCE - 12),
        entry(ASSIGNMENT_BITWISE_SHIFT_RIGHT, MAX_PRECEDENCE - 12),
        entry(ASSIGNMENT_BITWISE_SHIFT_RIGHT_ZERO_FILL, MAX_PRECEDENCE - 12),
        entry(ASSIGNMENT_BITWISE_AND, MAX_PRECEDENCE - 12),
        entry(ASSIGNMENT_BITWISE_XOR, MAX_PRECEDENCE - 12),
        entry(ASSIGNMENT_BITWISE_OR, MAX_PRECEDENCE - 12)
    );

    private final PrecedenceOperatorResolver precedenceOperatorResolver =
        new PrecedenceOperatorResolverImpl(operatorPrecedenceMap);

    private final ComplexExpressionBuilder complexExpressionBuilder =
        new PostfixNotationComplexExpressionBuilder(precedenceOperatorResolver);

    private final SingleTokenExpressionBuilder singleTokenExpressionBuilder =
        new SingleTokenExpressionBuilderImpl(variableBuilder);

    private final FunctionNameBuilder functionNameBuilder = new FunctionNameBuilderImpl();

    private final LexemeBuilder lexemeBuilder =
        new LexemeBuilderImpl(singleTokenExpressionBuilder, functionNameBuilder);

    private final Set<ExpressionBuilder> expressionBuilders = Set.of(
        singleTokenExpressionBuilder
    );

    private final ComplexLexemeValidator complexLexemeValidator = new ComplexLexemeValidatorImpl();

    private final UnaryOperatorUpdater unaryOperatorUpdater = new UnaryOperatorUpdaterImpl();

    private final ExpressionResolver expressionResolver = new ExpressionResolverImpl(
        expressionBuilders, lexemeBuilder, unaryOperatorUpdater, complexLexemeValidator, complexExpressionBuilder);

    private final ExpressionOperationBuilder expressionOperationBuilder = new ExpressionOperationBuilderImpl();

    private final Set<OperationReader> operationReaders = Set.of(
        new PrintlnOperationReader(expressionResolver),
        new VariableDeclarationOperationReader(variableBuilder, expressionResolver),
        new FinalDeclarationOperationReader(variableBuilder, expressionResolver),
        new VariableAssignmentOperationReader(expressionResolver),
        new ContinueOperationReader(),
        new BreakOperationReader(),
        new ReturnOperationReader(expressionResolver),

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

    private final BlockOperationReader blockOperationReader = new BlockOperationReaderImpl(
        operationReaders,
        expressionResolver,
        expressionOperationBuilder);

    private final int maxFunctionParameterCount = 5;

    private final FunctionReader functionReader = new FunctionReaderImpl(
        functionNameBuilder,
        variableBuilder,
        blockOperationReader,
        maxFunctionParameterCount);

    private final Compiler compiler = new CompilerImpl(functionNameBuilder, sourceLineReader, functionReader);

    public Compiler getCompiler() {
        return compiler;
    }

    public ComplexExpressionBuilder getComplexExpressionBuilder() {
        return complexExpressionBuilder;
    }

    public ExpressionResolver getExpressionResolver() {
        return expressionResolver;
    }

    public PrecedenceOperatorResolver getPrecedenceOperatorResolver() {
        return precedenceOperatorResolver;
    }
}
