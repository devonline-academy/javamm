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

import academy.devonline.javamm.code.fragment.Expression;
import academy.devonline.javamm.code.fragment.SourceLine;
import academy.devonline.javamm.code.fragment.operation.Block;
import academy.devonline.javamm.code.fragment.operation.DoWhileOperation;
import academy.devonline.javamm.compiler.component.ExpressionResolver;
import academy.devonline.javamm.compiler.component.impl.error.JavammLineSyntaxError;
import academy.devonline.javamm.compiler.component.impl.error.JavammStructSyntaxError;

import java.util.List;
import java.util.ListIterator;
import java.util.Optional;

import static academy.devonline.javamm.code.syntax.Keywords.DO;
import static academy.devonline.javamm.code.syntax.Keywords.WHILE;
import static academy.devonline.javamm.compiler.component.impl.util.SyntaxParseUtils.getTokensBetweenBrackets;
import static academy.devonline.javamm.compiler.component.impl.util.SyntaxValidationUtils.validateThatLineEndsWithOpeningCurlyBrace;
import static java.lang.String.format;
import static java.util.Objects.requireNonNull;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
public class DoWhileOperationReader extends AbstractBlockOperationReader<DoWhileOperation> {

    private final ExpressionResolver expressionResolver;

    public DoWhileOperationReader(final ExpressionResolver expressionResolver) {
        this.expressionResolver = requireNonNull(expressionResolver);
    }

    @Override
    protected Optional<String> getOperationKeyword() {
        return Optional.of(DO);
    }

    @Override
    protected void validate(final SourceLine sourceLine) {
        validateThatLineEndsWithOpeningCurlyBrace(sourceLine);
        if (sourceLine.getTokenCount() != 2) {
            throw new JavammLineSyntaxError(format("'{' expected after '%s'", DO), sourceLine);
        }
    }

    @Override
    protected DoWhileOperation get(final SourceLine sourceLine, final ListIterator<SourceLine> iterator) {
        final Block body = getBlockOperationReader().read(sourceLine, iterator, true);
        final SourceLine whileSourceLine = getWhileSourceLine(iterator, sourceLine.getModuleName());
        final Expression condition = getCondition(whileSourceLine);
        return new DoWhileOperation(whileSourceLine, condition, body);
    }

    protected SourceLine getWhileSourceLine(final ListIterator<SourceLine> iterator,
                                            final String moduleName) {
        if (iterator.hasNext()) {
            final SourceLine sourceLine = iterator.next();
            if (WHILE.equals(sourceLine.getFirst())) {
                validateWhile(sourceLine);
                return sourceLine;
            } else {
                throw new JavammLineSyntaxError(format("'%s' expected", WHILE), sourceLine);
            }
        } else {
            throw new JavammStructSyntaxError(format("'%s' expected at the end of file", WHILE), moduleName);
        }
    }

    protected Expression getCondition(final SourceLine whileSourceLine) {
        final List<String> tokens = getTokensBetweenBrackets("(", ")", whileSourceLine, false);
        return expressionResolver.resolve(tokens, whileSourceLine);
    }

    protected void validateWhile(final SourceLine sourceLine) {
        if (!"(".equals(sourceLine.getToken(1))) {
            throw new JavammLineSyntaxError(format("'(' expected after '%s'", WHILE), sourceLine);
        }
        if (!")".equals(sourceLine.getToken(sourceLine.getTokenCount() - 1))) {
            throw new JavammLineSyntaxError("')' expected at the end of the line", sourceLine);
        }
    }
}
