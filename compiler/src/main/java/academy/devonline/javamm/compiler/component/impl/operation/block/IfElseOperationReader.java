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
import academy.devonline.javamm.code.fragment.Operation;
import academy.devonline.javamm.code.fragment.SourceLine;
import academy.devonline.javamm.code.fragment.operation.Block;
import academy.devonline.javamm.code.fragment.operation.IfElseOperation;
import academy.devonline.javamm.compiler.component.ExpressionResolver;
import academy.devonline.javamm.compiler.component.impl.error.JavammLineSyntaxError;

import java.util.List;
import java.util.ListIterator;
import java.util.Optional;

import static academy.devonline.javamm.code.syntax.Keywords.ELSE;
import static academy.devonline.javamm.code.syntax.Keywords.IF;
import static academy.devonline.javamm.compiler.component.impl.util.SyntaxParseUtils.getTokensBetweenBrackets;
import static academy.devonline.javamm.compiler.component.impl.util.SyntaxValidationUtils.validateThatLineEndsWithOpeningCurlyBrace;
import static java.lang.String.format;
import static java.util.Objects.requireNonNull;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
public class IfElseOperationReader extends AbstractBlockOperationReader<IfElseOperation> {

    private final ExpressionResolver expressionResolver;

    public IfElseOperationReader(final ExpressionResolver expressionResolver) {
        this.expressionResolver = requireNonNull(expressionResolver);
    }

    @Override
    public boolean canRead(final SourceLine sourceLine) {
        return IF.equals(sourceLine.getFirst());
    }

    @Override
    protected void validate(final SourceLine sourceLine) {
        validateIfOrElseIf(sourceLine, false);
    }

    @Override
    protected IfElseOperation get(final SourceLine sourceLine, final ListIterator<SourceLine> iterator) {
        final Expression condition = getCondition(sourceLine);
        final Block trueBlock = getBlockOperationReader().read(sourceLine, iterator);
        final Optional<Block> falseBlock = getFalseOptionalBlock(iterator);
        // Functional
        return falseBlock
            .map(block -> new IfElseOperation(sourceLine, condition, trueBlock, block))
            .orElseGet(() -> new IfElseOperation(sourceLine, condition, trueBlock));
        /*
        // Imperative
        if(falseBlock.isPresent()){
            return new IfElseOperation(sourceLine, condition, trueBlock, falseBlock.get());
        } else {
            return new IfElseOperation(sourceLine, condition, trueBlock);
        }*/
    }

    private Expression getCondition(final SourceLine sourceLine) {
        final List<String> expressionTokens = getTokensBetweenBrackets("(", ")", sourceLine, false);
        return expressionResolver.resolve(expressionTokens, sourceLine);
    }

    /*
    -- case 1 ----------------------------------------------------------
    if(a > 1) {
    -- case 2 --------------------------------------------------------------
    if(a > 1) {
    }
    else if(a > 8){
    }
    -- case 3 --------------------------------------------------------------
    if(a > 1) {
    }
    else {
    }
    -- case 4 --------------------------------------------------------------
    if(a > 1)
    var a = 6
     */
    private Optional<Block> getFalseOptionalBlock(final ListIterator<SourceLine> iterator) {
        if (iterator.hasNext()) {
            final SourceLine sourceLine = iterator.next();
            if (ELSE.equals(sourceLine.getFirst())) {
                if (isIfAfterElse(sourceLine)) {
                    validateIfOrElseIf(sourceLine, true);
                    final Operation elseIfOperation = get(sourceLine, iterator);
                    return Optional.of(new Block(elseIfOperation, sourceLine));
                } else {
                    validateElse(sourceLine);
                    return Optional.of(getBlockOperationReader().read(sourceLine, iterator));
                }
            } else {
                iterator.previous();
            }
        }
        return Optional.empty();
    }

    protected void validateIfOrElseIf(final SourceLine sourceLine,
                                      final boolean isElseIf) {
        validateThatLineEndsWithOpeningCurlyBrace(sourceLine);
        final int expectedOpeningCurlyBraceIndex = isElseIf ? 2 : 1;
        if (!"(".equals(sourceLine.getToken(expectedOpeningCurlyBraceIndex))) {
            throw new JavammLineSyntaxError(format("'(' expected after '%s'", IF), sourceLine);
        }
        if (!")".equals(sourceLine.getToken(sourceLine.getTokenCount() - 2))) {
            throw new JavammLineSyntaxError("')' expected before '{'", sourceLine);
        }
    }

    protected void validateElse(final SourceLine sourceLine) {
        validateThatLineEndsWithOpeningCurlyBrace(sourceLine);
        if (sourceLine.getTokenCount() > 2) {
            throw new JavammLineSyntaxError(format("'{' expected after '%s'", ELSE), sourceLine);
        }
    }

    private boolean isIfAfterElse(final SourceLine sourceLine) {
        return sourceLine.getTokenCount() > 1 && IF.equals(sourceLine.getToken(1));
    }
}
