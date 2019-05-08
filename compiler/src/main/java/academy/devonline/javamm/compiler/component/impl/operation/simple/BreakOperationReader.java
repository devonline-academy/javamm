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

import academy.devonline.javamm.code.fragment.SourceLine;
import academy.devonline.javamm.code.fragment.operation.BreakOperation;
import academy.devonline.javamm.compiler.component.impl.error.JavammLineSyntaxError;
import academy.devonline.javamm.compiler.component.impl.operation.AbstractOperationReader;

import java.util.ListIterator;
import java.util.Optional;

import static academy.devonline.javamm.code.syntax.Keywords.BREAK;
import static java.lang.String.format;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
public class BreakOperationReader extends AbstractOperationReader<BreakOperation> {

    @Override
    protected Optional<String> getOperationKeyword() {
        return Optional.of(BREAK);
    }

    @Override
    protected void validate(final SourceLine sourceLine) {
        if (sourceLine.getTokenCount() > 1) {
            throw new JavammLineSyntaxError(format("'%s' expected only", BREAK), sourceLine);
        }
    }

    @Override
    protected BreakOperation get(final SourceLine sourceLine, final ListIterator<SourceLine> iterator) {
        return new BreakOperation(sourceLine);
    }
}
