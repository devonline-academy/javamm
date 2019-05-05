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

package academy.devonline.javamm.interpreter.component.impl;

import academy.devonline.javamm.code.exception.ConfigException;
import academy.devonline.javamm.code.fragment.Operation;
import academy.devonline.javamm.code.fragment.operation.Block;
import academy.devonline.javamm.interpreter.component.BlockOperationInterpreter;
import academy.devonline.javamm.interpreter.component.OperationInterpreter;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.function.BinaryOperator;

import static academy.devonline.javamm.interpreter.model.CurrentRuntimeProvider.getCurrentRuntime;
import static java.lang.String.format;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toUnmodifiableMap;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
public final class BlockOperationInterpreterImpl implements BlockOperationInterpreter {

    private final Map<Class<? extends Operation>, OperationInterpreter> operationInterpreterMap;

    public BlockOperationInterpreterImpl(final Set<OperationInterpreter<?>> operationInterpreters) {
        this.operationInterpreterMap = buildOperationInterpreterMap(operationInterpreters);
    }

    //Functional
    private Map<Class<? extends Operation>, OperationInterpreter> buildOperationInterpreterMap(
        final Collection<OperationInterpreter<?>> operationInterpreters) {
        return operationInterpreters.stream()
            .collect(toUnmodifiableMap(OperationInterpreter::getOperationClass, identity(), checkDuplicates()));
    }

    //Functional
    private BinaryOperator<OperationInterpreter> checkDuplicates() {
        return (oi1, oi2) -> {
            throw new ConfigException(format(
                "Duplicate of OperationInterpreter found: operation=%s, interpreter1=%s, interpreter2=%s",
                oi1.getOperationClass().getName(), oi1, oi2));
        };
    }

    /*
    //Imperative
    private Map<Class<? extends Operation>, OperationInterpreter> buildOperationInterpreterMap(
            final Collection<OperationInterpreter<?>> operationInterpreters) {
        final Map<Class<? extends Operation>, OperationInterpreter> map = new HashMap<>();
        for (final OperationInterpreter operationInterpreter : operationInterpreters) {
            final OperationInterpreter prev = map.put(operationInterpreter.getOperationClass(), operationInterpreter);
            if (prev != null) {
                throw new ConfigException(format(
                    "Duplicate of OperationInterpreter found: operation=%s, interpreter1=%s, interpreter2=%s",
                    prev.getOperationClass().getName(), prev, operationInterpreter));
            }
        }
        return Map.copyOf(map);
    }
    */

    //Imperative
    @Override
    @SuppressWarnings("unchecked")
    public void interpret(final Block block) {
        for (final Operation operation : block.getOperations()) {
            final OperationInterpreter operationInterpreter = operationInterpreterMap.get(operation.getClass());
            if (operationInterpreter != null) {
                getCurrentRuntime().setCurrentOperation(operation);
                operationInterpreter.interpret(operation);
            } else {
                throw new ConfigException("OperationInterpreter not defined for " + operation.getClass());
            }
        }
    }

    /*
    //Functional
    @SuppressWarnings("unchecked")
    public void interpret(final Block block) {
        block.getOperations().forEach(operation ->
            ofNullable(operationInterpreterMap.get(operation.getClass()))
                .ifPresentOrElse(operationInterpreter -> {
                    getCurrentRuntime().setCurrentOperation(operation);
                    operationInterpreter.interpret(operation);
                }, () -> {
                    throw new ConfigException("OperationInterpreter not defined for " + operation.getClass());
                }));
    }
    */
}
