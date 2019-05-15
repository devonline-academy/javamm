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
import academy.devonline.javamm.code.fragment.expression.CaseValueExpression;
import academy.devonline.javamm.code.fragment.operation.Block;
import academy.devonline.javamm.code.fragment.operation.SwitchOperation;
import academy.devonline.javamm.compiler.component.BlockOperationReader;
import academy.devonline.javamm.compiler.component.ExpressionResolver;
import academy.devonline.javamm.compiler.component.impl.error.JavammLineSyntaxError;

import java.util.List;
import java.util.ListIterator;

import static academy.devonline.javamm.code.syntax.Keywords.CASE;
import static academy.devonline.javamm.compiler.component.impl.util.SyntaxValidationUtils.validateThatLineEndsWithOpeningCurlyBrace;
import static java.lang.String.format;
import static java.util.Objects.requireNonNull;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
public class SwitchCaseOperationReader {

    private final ExpressionResolver expressionResolver;

    protected SwitchCaseOperationReader(final ExpressionResolver expressionResolver) {
        this.expressionResolver = requireNonNull(expressionResolver);
    }

    protected boolean canRead(final SourceLine sourceLine) {
        return CASE.equals(sourceLine.getFirst());
    }

    protected void readTo(final SwitchOperation.Builder builder,
                          final ListIterator<SourceLine> iterator,
                          final SourceLine sourceLine,
                          final BlockOperationReader blockOperationReader) {
        validate(sourceLine);
        final CaseValueExpression caseValueExpression = getCaseValueExpression(sourceLine);
        final Block caseBlock = blockOperationReader.read(sourceLine, iterator);
        try {
            builder.addCase(caseValueExpression, caseBlock);
        } catch (final IllegalArgumentException e) {
            throw new JavammLineSyntaxError(e.getMessage(), sourceLine);
        }
    }

    protected void validate(final SourceLine sourceLine) {
        validateThatLineEndsWithOpeningCurlyBrace(sourceLine);
        if (!":".equals(sourceLine.getToken(sourceLine.getTokenCount() - 2))) {
            throw new JavammLineSyntaxError("':' expected before '{'", sourceLine);
        }
        if (sourceLine.getTokenCount() != 4) {
            throw new JavammLineSyntaxError(format("An constant expected between '%s' and ':'", CASE), sourceLine);
        }
    }

    protected CaseValueExpression getCaseValueExpression(final SourceLine sourceLine) {
        final List<String> caseTokens = sourceLine.subList(1, 2);
        final Expression expression = expressionResolver.resolve(caseTokens, sourceLine);
        if (expression instanceof CaseValueExpression) {
            return (CaseValueExpression) expression;
        } else {
            throw new JavammLineSyntaxError(format("An constant expected between '%s' and ':'", CASE), sourceLine);
        }
    }
}
