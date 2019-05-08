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

package academy.devonline.javamm.interpreter.component.impl.operation.block;

import academy.devonline.javamm.code.component.ExpressionContext;
import academy.devonline.javamm.code.fragment.operation.Block;
import academy.devonline.javamm.code.fragment.operation.SwitchOperation;
import academy.devonline.javamm.interpreter.component.impl.operation.exception.BreakOperationException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
public class SwitchOperationInterpreter extends AbstractBlockOperationInterpreter<SwitchOperation> {

    public SwitchOperationInterpreter(final ExpressionContext expressionContext) {
        super(expressionContext);
    }

    @Override
    public Class<SwitchOperation> getOperationClass() {
        return SwitchOperation.class;
    }

    @Override
    protected void interpretOperation(final SwitchOperation operation) {
        final Object value = operation.getCondition().getValue(expressionContext);
        final List<Block> blocks = getBlocks(operation, value);
        interpretBlocks(blocks);
    }

    protected List<Block> getBlocks(final SwitchOperation operation,
                                    final Object value) {
        final List<Block> blocks = new ArrayList<>();
        final Iterator<SwitchOperation.CaseEntry> iterator = operation.getCases().iterator();
        while (iterator.hasNext()) {
            final SwitchOperation.CaseEntry caseEntry = iterator.next();
            if (Objects.equals(value, caseEntry.getKey().getValue())) {
                blocks.add(caseEntry.getBlock());
                addOtherCases(blocks, iterator);
            }
        }
        operation.getDefaultBlock().ifPresent(blocks::add);
        return blocks;
    }

    protected void addOtherCases(final List<Block> blocks,
                                 final Iterator<SwitchOperation.CaseEntry> iterator) {
        while (iterator.hasNext()) {
            blocks.add(iterator.next().getBlock());
        }
    }

    protected void interpretBlocks(final List<Block> blocks) {
        for (final Block block : blocks) {
            try {
                interpretBlock(block);
            } catch (final BreakOperationException e) {
                break;
            }
        }
    }
}
