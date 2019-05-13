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

import academy.devonline.javamm.code.fragment.FunctionName;
import academy.devonline.javamm.code.fragment.SourceLine;
import academy.devonline.javamm.code.fragment.Variable;
import academy.devonline.javamm.code.fragment.function.DeveloperFunction;
import academy.devonline.javamm.compiler.component.BlockOperationReader;
import academy.devonline.javamm.compiler.component.FunctionNameBuilder;
import academy.devonline.javamm.compiler.component.FunctionReader;
import academy.devonline.javamm.compiler.component.VariableBuilder;
import academy.devonline.javamm.compiler.component.impl.error.JavammLineSyntaxError;

import java.util.List;
import java.util.ListIterator;

import static academy.devonline.javamm.code.syntax.Keywords.FUNCTION;
import static academy.devonline.javamm.compiler.component.impl.util.SyntaxParseUtils.getTokensBetweenBrackets;
import static academy.devonline.javamm.compiler.component.impl.util.SyntaxParseUtils.groupTokensByComma;
import static academy.devonline.javamm.compiler.component.impl.util.SyntaxValidationUtils.validateThatLineEndsWithOpeningCurlyBrace;
import static java.lang.String.format;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toUnmodifiableList;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
public class FunctionReaderImpl implements FunctionReader {

    private final FunctionNameBuilder functionNameBuilder;

    private final VariableBuilder variableBuilder;

    private final BlockOperationReader blockOperationReader;

    private final int maxFunctionParameterCount;

    public FunctionReaderImpl(final FunctionNameBuilder functionNameBuilder,
                              final VariableBuilder variableBuilder,
                              final BlockOperationReader blockOperationReader,
                              final int maxFunctionParameterCount) {
        this.functionNameBuilder = requireNonNull(functionNameBuilder);
        this.variableBuilder = requireNonNull(variableBuilder);
        this.blockOperationReader = requireNonNull(blockOperationReader);
        this.maxFunctionParameterCount = maxFunctionParameterCount;
    }

    @Override
    public DeveloperFunction read(final ListIterator<SourceLine> iterator) {
        final SourceLine sourceLine = iterator.next();
        validateFunctionHeader(sourceLine);
        final DeveloperFunction.Builder builder = readFunctionHeader(sourceLine);
        return builder
            .setBody(blockOperationReader.read(sourceLine, iterator, true))
            .build();
    }

    protected void validateFunctionHeader(final SourceLine sourceLine) {
        if (!FUNCTION.equals(sourceLine.getFirst())) {
            throw new JavammLineSyntaxError(format("'%s' expected at the beginning of the line", FUNCTION), sourceLine);
        }
        validateThatLineEndsWithOpeningCurlyBrace(sourceLine);
        final int indexOfOpeningParenthesis = sourceLine.indexOf("(");
        if (indexOfOpeningParenthesis == -1) {
            throw new JavammLineSyntaxError("Missing '('", sourceLine);
        } else if (indexOfOpeningParenthesis == 1) {
            throw new JavammLineSyntaxError(
                format("Function name expected between '%s' and '('", FUNCTION), sourceLine);
        } else if (indexOfOpeningParenthesis != 2) {
            throw new JavammLineSyntaxError("'(' expected after function name", sourceLine);
        }
        if (!")".equals(sourceLine.getToken(sourceLine.getTokenCount() - 2))) {
            throw new JavammLineSyntaxError("')' expected before '{'", sourceLine);
        }
    }

    protected DeveloperFunction.Builder readFunctionHeader(final SourceLine sourceLine) {
        final List<Variable> parameters = getFunctionParameters(sourceLine);
        final FunctionName functionName = functionNameBuilder.build(sourceLine.getToken(1), parameters, sourceLine);
        return new DeveloperFunction.Builder()
            .setName(functionName)
            .setParameters(parameters)
            .setDeclarationSourceLine(sourceLine);
    }

    // Functional
    protected List<Variable> getFunctionParameters(final SourceLine sourceLine) {
        return validateFunctionParameterCount(
            groupTokensByComma(
                getTokensBetweenBrackets("(", ")", sourceLine, true),
                sourceLine
            ).stream()
                .peek(tokens -> validateFunctionParamsSyntax(tokens, sourceLine))
                .map(list -> variableBuilder.build(list.get(0), sourceLine))
                .collect(toUnmodifiableList()),
            sourceLine
        );
    }

    /*
    // Imperative
    protected List<Variable> getFunctionParameters(final SourceLine sourceLine) {
        final List<String> parameterTokens = getTokensBetweenBrackets("(", ")", sourceLine, true);
        final List<List<String>> groupedParameterTokens = groupTokensByComma(parameterTokens, sourceLine);
        final List<Variable> variables = new ArrayList<>();
        for (final List<String> groupedParameterToken : groupedParameterTokens) {
            validateFunctionParamsSyntax(groupedParameterToken, sourceLine);
            final Variable variable = variableBuilder.build(groupedParameterToken.get(0), sourceLine);
            variables.add(variable);
        }
        validateFunctionParameterCount(variables, sourceLine);
        return List.copyOf(variables);
    }*/

    protected void validateFunctionParamsSyntax(final List<String> tokens, final SourceLine sourceLine) {
        if (tokens.size() > 1) {
            throw new JavammLineSyntaxError("Expressions not allowed here", sourceLine);
        }
    }

    protected List<Variable> validateFunctionParameterCount(final List<Variable> variables,
                                                            final SourceLine sourceLine) {
        if (variables.size() > maxFunctionParameterCount) {
            throw new JavammLineSyntaxError(format("Max allowed function parameter count is %s",
                maxFunctionParameterCount), sourceLine);
        }
        return variables;
    }
}
