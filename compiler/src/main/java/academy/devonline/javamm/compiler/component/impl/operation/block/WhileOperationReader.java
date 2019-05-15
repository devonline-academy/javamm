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
import academy.devonline.javamm.code.fragment.operation.WhileOperation;
import academy.devonline.javamm.compiler.component.ExpressionResolver;
import academy.devonline.javamm.compiler.component.impl.error.JavammLineSyntaxError;

import java.util.List;
import java.util.ListIterator;
import java.util.Optional;

import static academy.devonline.javamm.code.syntax.Keywords.WHILE;
import static academy.devonline.javamm.compiler.component.impl.util.SyntaxParseUtils.getTokensBetweenBrackets;
import static academy.devonline.javamm.compiler.component.impl.util.SyntaxValidationUtils.validateThatLineEndsWithOpeningCurlyBrace;
import static java.lang.String.format;
import static java.util.Objects.requireNonNull;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
public class WhileOperationReader extends AbstractBlockOperationReader<WhileOperation> {

    private final ExpressionResolver expressionResolver;

    public WhileOperationReader(final ExpressionResolver expressionResolver) {
        this.expressionResolver = requireNonNull(expressionResolver);
    }

    @Override
    protected Optional<String> getOperationKeyword() {
        return Optional.of(WHILE);
    }

    @Override
    protected void validate(final SourceLine sourceLine) {
        validateThatLineEndsWithOpeningCurlyBrace(sourceLine);
        if (!"(".equals(sourceLine.getToken(1))) {
            throw new JavammLineSyntaxError(format("'(' expected after '%s'", WHILE), sourceLine);
        }
        if (!")".equals(sourceLine.getToken(sourceLine.getTokenCount() - 2))) {
            throw new JavammLineSyntaxError("')' expected before '{'", sourceLine);
        }
    }

    @Override
    protected WhileOperation get(final SourceLine sourceLine,
                                 final ListIterator<SourceLine> iterator) {
        final Expression condition = getConditionExpression(sourceLine);
        final Block body = getBlockOperationReader().read(sourceLine, iterator);
        return new WhileOperation(sourceLine, condition, body);
    }

    protected Expression getConditionExpression(final SourceLine sourceLine) {
        final List<String> tokens = getTokensBetweenBrackets("(", ")", sourceLine, false);
        return expressionResolver.resolve(tokens, sourceLine);
    }
}
