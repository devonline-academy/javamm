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
import academy.devonline.javamm.code.fragment.operation.SwitchOperation;
import academy.devonline.javamm.compiler.component.ExpressionResolver;
import academy.devonline.javamm.compiler.component.impl.error.JavammLineSyntaxError;
import academy.devonline.javamm.compiler.component.impl.error.JavammStructSyntaxError;

import java.util.List;
import java.util.ListIterator;

import static academy.devonline.javamm.code.syntax.Keywords.SWITCH;
import static academy.devonline.javamm.compiler.component.impl.util.SyntaxParseUtils.getTokensBetweenBrackets;
import static academy.devonline.javamm.compiler.component.impl.util.SyntaxParseUtils.isClosingBlockOperation;
import static academy.devonline.javamm.compiler.component.impl.util.SyntaxValidationUtils.validateThatLineContainsOneTokenOnly;
import static academy.devonline.javamm.compiler.component.impl.util.SyntaxValidationUtils.validateThatLineEndsWithOpeningCurlyBrace;
import static java.lang.String.format;
import static java.util.Objects.requireNonNull;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
public class SwitchOperationReader extends AbstractBlockOperationReader<SwitchOperation> {

    private final ExpressionResolver expressionResolver;

    private final SwitchCaseOperationReader switchCaseOperationReader;

    private final SwitchDefaultOperationReader switchDefaultOperationReader;

    public SwitchOperationReader(final ExpressionResolver expressionResolver) {
        this.expressionResolver = requireNonNull(expressionResolver);
        this.switchCaseOperationReader = buildSwitchCaseOperationReader(expressionResolver);
        this.switchDefaultOperationReader = buildSwitchDefaultOperationReader();
    }

    protected SwitchCaseOperationReader buildSwitchCaseOperationReader(final ExpressionResolver expressionResolver) {
        return new SwitchCaseOperationReader(expressionResolver);
    }

    protected SwitchDefaultOperationReader buildSwitchDefaultOperationReader() {
        return new SwitchDefaultOperationReader();
    }

    @Override
    public boolean canRead(final SourceLine sourceLine) {
        return SWITCH.equals(sourceLine.getFirst());
    }

    @Override
    protected void validate(final SourceLine sourceLine) {
        validateThatLineEndsWithOpeningCurlyBrace(sourceLine);
        if (!"(".equals(sourceLine.getToken(1))) {
            throw new JavammLineSyntaxError(format("'(' expected after '%s'", SWITCH), sourceLine);
        }
        if (!")".equals(sourceLine.getToken(sourceLine.getTokenCount() - 2))) {
            throw new JavammLineSyntaxError("')' expected before '{'", sourceLine);
        }
    }

    @Override
    protected SwitchOperation get(final SourceLine sourceLine, final ListIterator<SourceLine> iterator) {
        final SwitchOperation.Builder builder = new SwitchOperation.Builder()
            .setSourceLine(sourceLine);
        builder.setCondition(getConditionExpression(sourceLine));
        return readSwitchBody(builder, iterator, sourceLine.getModuleName());
    }

    protected Expression getConditionExpression(final SourceLine sourceLine) {
        final List<String> tokens = getTokensBetweenBrackets("(", ")", sourceLine, false);
        return expressionResolver.resolve(tokens, sourceLine);
    }

    protected SwitchOperation readSwitchBody(final SwitchOperation.Builder builder,
                                             final ListIterator<SourceLine> iterator,
                                             final String moduleName) {
        while (iterator.hasNext()) {
            final SourceLine sourceLine = iterator.next();
            if (isClosingBlockOperation(sourceLine)) {
                validateThatLineContainsOneTokenOnly("}", sourceLine);
                return builder.build();
            }
            if (switchCaseOperationReader.canRead(sourceLine)) {
                switchCaseOperationReader.readTo(builder, iterator, sourceLine, getBlockOperationReader());
            } else if (switchDefaultOperationReader.canRead(sourceLine)) {
                switchDefaultOperationReader.readTo(builder, iterator, sourceLine, getBlockOperationReader());
            } else {
                throw new JavammLineSyntaxError(format("Unsupported '%s' child statement", SWITCH), sourceLine);
            }
        }
        throw new JavammStructSyntaxError(format("'}' expected to close '%s' block statement at the end of file", SWITCH), moduleName);
    }
}
