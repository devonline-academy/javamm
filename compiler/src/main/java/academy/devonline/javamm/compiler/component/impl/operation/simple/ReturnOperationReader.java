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

package academy.devonline.javamm.compiler.component.impl.operation.simple;

import academy.devonline.javamm.code.fragment.Expression;
import academy.devonline.javamm.code.fragment.SourceLine;
import academy.devonline.javamm.code.fragment.operation.ReturnOperation;
import academy.devonline.javamm.compiler.component.ExpressionResolver;
import academy.devonline.javamm.compiler.component.impl.operation.AbstractOperationReader;

import java.util.ListIterator;
import java.util.Optional;

import static academy.devonline.javamm.code.syntax.Keywords.RETURN;
import static java.util.Objects.requireNonNull;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
public class ReturnOperationReader extends AbstractOperationReader<ReturnOperation> {

    private final ExpressionResolver expressionResolver;

    public ReturnOperationReader(final ExpressionResolver expressionResolver) {
        this.expressionResolver = requireNonNull(expressionResolver);
    }

    @Override
    protected Optional<String> getOperationKeyword() {
        return Optional.of(RETURN);
    }

    @Override
    protected ReturnOperation get(final SourceLine sourceLine, final ListIterator<SourceLine> iterator) {
        if (sourceLine.getTokenCount() == 1) {
            return new ReturnOperation(sourceLine);
        } else {
            final Expression expression = expressionResolver.resolve(sourceLine.subList(1), sourceLine);
            return new ReturnOperation(sourceLine, expression);
        }
    }
}
