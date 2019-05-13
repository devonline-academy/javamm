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

package academy.devonline.javamm.interpreter.component.impl.error;

import academy.devonline.javamm.interpreter.JavammRuntimeError;
import academy.devonline.javamm.interpreter.model.StackTraceItem;

import java.util.List;

import static academy.devonline.javamm.interpreter.model.CurrentRuntimeProvider.getCurrentRuntime;
import static java.lang.String.format;
import static java.util.stream.Collectors.toUnmodifiableList;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
public class JavammLineRuntimeError extends JavammRuntimeError {

    private final List<StackTraceItem> currentStackTrace;

    public JavammLineRuntimeError(final String message) {
        super(message);
        this.currentStackTrace = getCurrentRuntime().getCurrentStackTrace();
    }

    @Override
    public String getMessage() {
        return String.format("%s%s%s",
            super.getMessage(),
            System.lineSeparator(),
            String.join(
                System.lineSeparator(),
                currentStackTrace.stream().map(this::toString).collect(toUnmodifiableList())
            ));
    }

    private String toString(final StackTraceItem s) {
        return format("    at %s [%s:%s]", s.getFunction(), s.getModuleName(), s.getSourceLineNumber());
    }

    @Override
    public List<StackTraceItem> getCurrentStackTrace() {
        return currentStackTrace;
    }
}
