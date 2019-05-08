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
import academy.devonline.javamm.code.fragment.operation.PrintlnOperation;
import academy.devonline.javamm.compiler.component.ExpressionResolver;
import academy.devonline.javamm.compiler.component.impl.error.JavammLineSyntaxError;
import academy.devonline.javamm.compiler.component.impl.operation.AbstractOperationReader;
import academy.devonline.javamm.compiler.component.impl.operation.ForInitOperationReader;
import academy.devonline.javamm.compiler.component.impl.operation.ForUpdateOperationReader;

import java.util.List;
import java.util.ListIterator;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
public class PrintlnOperationReader extends AbstractOperationReader<PrintlnOperation>
    implements ForInitOperationReader, ForUpdateOperationReader {

    private final ExpressionResolver expressionResolver;

    public PrintlnOperationReader(final ExpressionResolver expressionResolver) {
        this.expressionResolver = requireNonNull(expressionResolver);
    }

    @Override
    protected Optional<String> getOperationKeyword() {
        return Optional.of("println");
    }

    @Override
    protected void validate(final SourceLine sourceLine) {
        if (!")".equals(sourceLine.getLast())) {
            throw new JavammLineSyntaxError("Expected ) at the end of line", sourceLine);
        }
        if (!"(".equals(sourceLine.getToken(1))) {
            throw new JavammLineSyntaxError("Expected ( after 'println'", sourceLine);
        }
    }

    @Override
    protected PrintlnOperation get(final SourceLine sourceLine, final ListIterator<SourceLine> iterator) {
        final List<String> expressionTokens = sourceLine.subList(2, sourceLine.getTokenCount() - 1);
        final Expression expression = expressionResolver.resolve(expressionTokens, sourceLine);
        return new PrintlnOperation(sourceLine, expression);
    }
}
