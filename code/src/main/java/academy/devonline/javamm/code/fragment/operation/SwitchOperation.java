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

import academy.devonline.javamm.code.fragment.CompiledCodeFragment;
import academy.devonline.javamm.code.fragment.Expression;
import academy.devonline.javamm.code.fragment.SourceLine;
import academy.devonline.javamm.code.fragment.expression.CaseValueExpression;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import static java.lang.String.format;
import static java.util.Objects.requireNonNull;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
public final class SwitchOperation extends AbstractOperation {

    private final Expression condition;

    private final List<CaseEntry> cases;

    private final Block defaultBlock;

    private SwitchOperation(final SourceLine sourceLine,
                            final Expression condition,
                            final List<CaseEntry> cases,
                            final Block defaultBlock) {
        super(sourceLine);
        this.condition = requireNonNull(condition);
        this.cases = List.copyOf(cases);
        this.defaultBlock = defaultBlock;
    }

    public Expression getCondition() {
        return condition;
    }

    public List<CaseEntry> getCases() {
        return cases;
    }

    public Optional<Block> getDefaultBlock() {
        return Optional.ofNullable(defaultBlock);
    }

    /**
     * @author devonline
     * @link http://devonline.academy/javamm
     */
    public interface CaseEntry extends CompiledCodeFragment {

        CaseValueExpression getKey();

        Block getBlock();
    }

    /**
     * @author devonline
     * @link http://devonline.academy/javamm
     */
    public static final class Builder {

        private final Set<CaseEntry> cases = new LinkedHashSet<>();

        private SourceLine sourceLine;

        private Expression condition;

        private Block defaultBlock;

        public Builder setSourceLine(final SourceLine sourceLine) {
            this.sourceLine = sourceLine;
            return this;
        }

        public Builder setCondition(final Expression condition) {
            this.condition = requireNonNull(condition);
            return this;
        }

        public Builder addCase(final CaseValueExpression caseKey, final Block caseBlock)
            throws IllegalArgumentException {
            if (!this.cases.add(new CaseEntryImpl(caseKey, caseBlock))) {
                throw new IllegalArgumentException(format("Duplicate case label '%s'", caseKey.getValue()));
            }
            return this;
        }

        public Builder setDefaultBlock(final Block defaultBlock)
            throws IllegalArgumentException {
            if (this.defaultBlock != null) {
                throw new IllegalArgumentException("Duplicate default label");
            }
            this.defaultBlock = requireNonNull(defaultBlock);
            return this;
        }

        public SwitchOperation build() {
            return new SwitchOperation(sourceLine, condition, List.copyOf(cases), defaultBlock);
        }

        /**
         * @author devonline
         * @link http://devonline.academy/javamm
         */
        private static final class CaseEntryImpl implements CaseEntry {

            private final CaseValueExpression caseKey;

            private final Block caseBlock;

            private CaseEntryImpl(final CaseValueExpression caseKey, final Block caseBlock) {
                this.caseKey = requireNonNull(caseKey);
                this.caseBlock = requireNonNull(caseBlock);
            }

            @Override
            public CaseValueExpression getKey() {
                return caseKey;
            }

            @Override
            public Block getBlock() {
                return caseBlock;
            }

            @Override
            public int hashCode() {
                return Objects.hashCode(caseKey.getValue());
            }

            @SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
            @Override
            public boolean equals(final Object obj) {
                return Objects.equals(caseKey.getValue(), ((CaseEntryImpl) obj).caseKey.getValue());
            }
        }
    }
}
