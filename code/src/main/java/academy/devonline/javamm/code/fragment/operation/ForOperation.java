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
import academy.devonline.javamm.code.fragment.expression.ConstantExpression;

import java.util.Optional;

import static java.util.Objects.requireNonNull;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
public final class ForOperation extends AbstractLoopOperation {

    private final ForInitOperation initOperation;

    private final ForUpdateOperation updateOperation;

    private ForOperation(final SourceLine sourceLine,
                         final ForInitOperation initOperation,
                         final Expression condition,
                         final ForUpdateOperation updateOperation,
                         final Block body) {
        super(sourceLine, condition, body);
        this.initOperation = initOperation;
        this.updateOperation = updateOperation;
    }

    public Optional<ForInitOperation> getInitOperation() {
        return Optional.ofNullable(initOperation);
    }

    public Optional<ForUpdateOperation> getUpdateOperation() {
        return Optional.ofNullable(updateOperation);
    }

    /**
     * @author devonline
     * @link http://devonline.academy/javamm
     */
    public static final class Builder {

        private SourceLine sourceLine;

        private ForInitOperation initOperation;

        private Expression condition;

        private ForUpdateOperation updateOperation;

        private Block body;

        public Builder setSourceLine(final SourceLine sourceLine) {
            this.sourceLine = requireNonNull(sourceLine);
            return this;
        }

        public Builder setInitOperation(final ForInitOperation initOperation) {
            this.initOperation = requireNonNull(initOperation);
            return this;
        }

        public Builder setCondition(final Expression condition) {
            this.condition = requireNonNull(condition);
            return this;
        }

        public Builder setUpdateOperation(final ForUpdateOperation updateOperation) {
            this.updateOperation = requireNonNull(updateOperation);
            return this;
        }

        public Builder setBody(final Block body) {
            this.body = requireNonNull(body);
            return this;
        }

        public ForOperation build() {
            final Expression forCondition = condition == null ? ConstantExpression.valueOf(true) : condition;
            return new ForOperation(sourceLine, initOperation, forCondition, updateOperation, body);
        }
    }
}
