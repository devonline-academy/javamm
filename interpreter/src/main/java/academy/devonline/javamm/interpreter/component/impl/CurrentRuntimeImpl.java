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
import academy.devonline.javamm.interpreter.model.CurrentRuntime;
import academy.devonline.javamm.interpreter.model.LocalContext;

import static java.util.Objects.requireNonNull;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
public class CurrentRuntimeImpl implements CurrentRuntime {

    private SourceLine currentSourceLine;

    private LocalContext currentLocalContext;

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
}
