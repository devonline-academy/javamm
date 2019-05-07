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

package academy.devonline.javamm.code.fragment.operation;

import academy.devonline.javamm.code.fragment.Expression;
import academy.devonline.javamm.code.fragment.SourceLine;

import java.util.Optional;

import static java.util.Objects.requireNonNull;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
public final class IfElseOperation extends AbstractOperation {

    private final Expression condition;

    private final Block trueBlock;

    private final Block falseBlock;

    public IfElseOperation(final SourceLine sourceLine,
                           final Expression condition,
                           final Block trueBlock,
                           final Block falseBlock) {
        super(sourceLine);
        this.condition = requireNonNull(condition);
        this.trueBlock = requireNonNull(trueBlock);
        this.falseBlock = requireNonNull(falseBlock);
    }

    public IfElseOperation(final SourceLine sourceLine,
                           final Expression condition,
                           final Block trueBlock) {
        super(sourceLine);
        this.condition = requireNonNull(condition);
        this.trueBlock = requireNonNull(trueBlock);
        this.falseBlock = null;
    }

    public Expression getCondition() {
        return condition;
    }

    public Block getTrueBlock() {
        return trueBlock;
    }

    public Optional<Block> getFalseBlock() {
        return Optional.ofNullable(falseBlock);
    }
}
