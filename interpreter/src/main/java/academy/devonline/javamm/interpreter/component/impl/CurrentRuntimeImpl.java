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

import academy.devonline.javamm.code.fragment.SourceLine;
import academy.devonline.javamm.code.fragment.function.DeveloperFunction;
import academy.devonline.javamm.interpreter.component.FunctionInvoker;
import academy.devonline.javamm.interpreter.component.impl.model.StackTraceItemImpl;
import academy.devonline.javamm.interpreter.model.CurrentRuntime;
import academy.devonline.javamm.interpreter.model.LocalContext;
import academy.devonline.javamm.interpreter.model.StackTraceItem;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
public class CurrentRuntimeImpl implements CurrentRuntime {

    private final FunctionInvoker functionInvoker;

    private final Deque<StackTraceItem> currentStack = new ArrayDeque<>();

    private SourceLine currentSourceLine;

    private LocalContext currentLocalContext;

    CurrentRuntimeImpl(final FunctionInvoker functionInvoker) {
        this.functionInvoker = requireNonNull(functionInvoker);
    }

    @Override
    public FunctionInvoker getCurrentFunctionInvoker() {
        return functionInvoker;
    }

    @Override
    public String getCurrentModuleName() {
        return currentSourceLine.getModuleName();
    }

    @Override
    public SourceLine getCurrentSourceLine() {
        return requireNonNull(currentSourceLine, "currentSourceLine is not set");
    }

    @Override
    public void setCurrentSourceLine(final SourceLine currentSourceLine) {
        this.currentSourceLine = requireNonNull(currentSourceLine, "currentSourceLine can't be null");
    }

    @Override
    public LocalContext getCurrentLocalContext() {
        return requireNonNull(currentLocalContext, "currentLocalContext is not set");
    }

    @Override
    public void setCurrentLocalContext(final LocalContext currentLocalContext) {
        this.currentLocalContext = requireNonNull(currentLocalContext, "currentLocalContext can't be null");
    }

    @Override
    public void enterToFunction(final DeveloperFunction developerFunction) {
        currentStack.push(new StackTraceItemImpl(developerFunction, currentSourceLine));
    }

    @Override
    public void exitFromFunction() {
        currentStack.poll();
    }

    @Override
    public List<StackTraceItem> getCurrentStackTrace() {
        return List.copyOf(currentStack);
    }
}
