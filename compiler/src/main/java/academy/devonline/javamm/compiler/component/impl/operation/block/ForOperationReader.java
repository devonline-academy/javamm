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

package academy.devonline.javamm.compiler.component.impl.operation.block;

import academy.devonline.javamm.code.fragment.SourceLine;
import academy.devonline.javamm.code.fragment.operation.ForInitOperation;
import academy.devonline.javamm.code.fragment.operation.ForOperation;
import academy.devonline.javamm.code.fragment.operation.ForUpdateOperation;
import academy.devonline.javamm.compiler.component.ExpressionOperationBuilder;
import academy.devonline.javamm.compiler.component.ExpressionResolver;
import academy.devonline.javamm.compiler.component.impl.error.JavammLineSyntaxError;
import academy.devonline.javamm.compiler.component.impl.operation.ForInitOperationReader;
import academy.devonline.javamm.compiler.component.impl.operation.ForUpdateOperationReader;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;
import java.util.Set;

import static academy.devonline.javamm.code.syntax.Keywords.FOR;
import static academy.devonline.javamm.compiler.component.impl.util.SyntaxParseUtils.getTokensBetweenBrackets;
import static academy.devonline.javamm.compiler.component.impl.util.SyntaxValidationUtils.validateThatLineEndsWithOpeningCurlyBrace;
import static java.lang.String.format;
import static java.util.Collections.emptyListIterator;
import static java.util.Objects.requireNonNull;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
public class ForOperationReader extends AbstractBlockOperationReader<ForOperation> {

    private final Collection<ForInitOperationReader> initOperationReaders;

    private final ExpressionResolver expressionResolver;

    private final Collection<ForUpdateOperationReader> updateOperationReaders;

    private final ExpressionOperationBuilder expressionOperationBuilder;

    public ForOperationReader(final Set<ForInitOperationReader> initOperationReaders,
                              final ExpressionResolver expressionResolver,
                              final Set<ForUpdateOperationReader> updateOperationReaders,
                              final ExpressionOperationBuilder expressionOperationBuilder) {
        this.initOperationReaders = List.copyOf(initOperationReaders);
        this.expressionResolver = requireNonNull(expressionResolver);
        this.updateOperationReaders = List.copyOf(updateOperationReaders);
        this.expressionOperationBuilder = requireNonNull(expressionOperationBuilder);
    }

    @Override
    protected Optional<String> getOperationKeyword() {
        return Optional.of(FOR);
    }

    @Override
    protected void validate(final SourceLine sourceLine) {
        validateThatLineEndsWithOpeningCurlyBrace(sourceLine);
        if (!"(".equals(sourceLine.getToken(1))) {
            throw new JavammLineSyntaxError(format("'(' expected after '%s'", FOR), sourceLine);
        }
        if (!")".equals(sourceLine.getToken(sourceLine.getTokenCount() - 2))) {
            throw new JavammLineSyntaxError("')' expected before '{'", sourceLine);
        }
    }

    @Override
    protected ForOperation get(final SourceLine sourceLine, final ListIterator<SourceLine> iterator) {
        final ForOperation.Builder builder = getForHeader(sourceLine);
        builder.setBody(getBlockOperationReader().read(sourceLine, iterator));
        return builder.build();
    }

    protected ForOperation.Builder getForHeader(final SourceLine sourceLine) {
        final ForOperation.Builder builder = new ForOperation.Builder()
            .setSourceLine(sourceLine);
        final ForTokens forTokens = getForTokens(sourceLine);
        if (!forTokens.getInitTokens().isEmpty()) {
            builder.setInitOperation(resolveForInitOperation(initOperationReaders,
                sourceLine.getModuleName(), sourceLine.getNumber(), forTokens.getInitTokens()));
        }
        if (!forTokens.getConditionTokens().isEmpty()) {
            builder.setCondition(expressionResolver.resolve(forTokens.getConditionTokens(), sourceLine));
        }
        if (!forTokens.getUpdateTokens().isEmpty()) {
            builder.setUpdateOperation(resolveForUpdateOperation(updateOperationReaders,
                sourceLine.getModuleName(), sourceLine.getNumber(), forTokens.getUpdateTokens()));
        }
        return builder;
    }

    protected ForTokens getForTokens(final SourceLine sourceLine) {
        final List<String> initTokens = new ArrayList<>();
        final List<String> conditionTokens = new ArrayList<>();
        final List<String> updateTokens = new ArrayList<>();

        final List<String> tokens = getTokensBetweenBrackets("(", ")", sourceLine, false);
        List<String> list = initTokens;
        int semicolonCount = 0;
        for (final String token : tokens) {
            if (";".equals(token)) {
                semicolonCount++;
                if (list == initTokens) {
                    list = conditionTokens;
                } else {
                    list = updateTokens;
                }
            } else {
                list.add(token);
            }
        }
        validateSemicolonCount(sourceLine, semicolonCount);
        return new ForTokens(initTokens, conditionTokens, updateTokens);
    }

    protected void validateSemicolonCount(final SourceLine sourceLine, final int semicolonCount) {
        if (semicolonCount < 2) {
            throw new JavammLineSyntaxError("Missing ';'", sourceLine);
        }
        if (semicolonCount > 2) {
            throw new JavammLineSyntaxError("Redundant ';'", sourceLine);
        }
    }

    protected ForInitOperation resolveForInitOperation(final Collection<ForInitOperationReader> operationReaders,
                                                       final String moduleName,
                                                       final int number,
                                                       final List<String> tokens) {
        final SourceLine sourceLine = new SourceLine(moduleName, number, tokens);
        return operationReaders.stream()
            .filter(operationReader -> operationReader.canRead(sourceLine))
            .findFirst()
            .map(operationReader -> operationReader.read(sourceLine, emptyListIterator()))
            .orElseGet(() -> Optional.of(expressionResolver.resolve(tokens, sourceLine))
                .map(expression -> expressionOperationBuilder.build(expression, sourceLine))
                .orElseThrow());
    }

    protected ForUpdateOperation resolveForUpdateOperation(final Collection<ForUpdateOperationReader> operationReaders,
                                                           final String moduleName,
                                                           final int number,
                                                           final List<String> tokens) {
        final SourceLine sourceLine = new SourceLine(moduleName, number, tokens);
        return operationReaders.stream()
            .filter(operationReader -> operationReader.canRead(sourceLine))
            .findFirst()
            .map(operationReader -> operationReader.read(sourceLine, emptyListIterator()))
            .orElseGet(() -> Optional.of(expressionResolver.resolve(tokens, sourceLine))
                .map(expression -> expressionOperationBuilder.build(expression, sourceLine))
                .orElseThrow());
    }

    /**
     * @author devonline
     * @link http://devonline.academy/javamm
     */
    protected static class ForTokens {

        private final List<String> initTokens;

        private final List<String> conditionTokens;

        private final List<String> updateTokens;

        protected ForTokens(final List<String> initTokens,
                            final List<String> conditionTokens,
                            final List<String> updateTokens) {
            this.initTokens = Collections.unmodifiableList(initTokens);
            this.conditionTokens = Collections.unmodifiableList(conditionTokens);
            this.updateTokens = Collections.unmodifiableList(updateTokens);
        }

        public List<String> getInitTokens() {
            return initTokens;
        }

        public List<String> getConditionTokens() {
            return conditionTokens;
        }

        public List<String> getUpdateTokens() {
            return updateTokens;
        }
    }
}
