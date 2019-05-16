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
import academy.devonline.javamm.code.fragment.operation.Block;

import java.util.ListIterator;

import static academy.devonline.javamm.compiler.component.impl.util.SyntaxValidationUtils.validateThatLineContainsOneTokenOnly;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
public class SimpleBlockOperationReader extends AbstractBlockOperationReader<Block> {

    @Override
    public boolean canRead(final SourceLine sourceLine) {
        return "{".equals(sourceLine.getFirst());
    }

    @Override
    protected void validate(final SourceLine sourceLine) {
        validateThatLineContainsOneTokenOnly("{", sourceLine);
    }

    @Override
    protected Block get(final SourceLine sourceLine, final ListIterator<SourceLine> iterator) {
        return getBlockOperationReader().read(sourceLine, iterator);
    }
}
