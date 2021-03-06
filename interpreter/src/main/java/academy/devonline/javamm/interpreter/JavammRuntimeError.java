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

package academy.devonline.javamm.interpreter;

import academy.devonline.javamm.code.exception.JavammError;
import academy.devonline.javamm.interpreter.model.StackTraceItem;

import java.util.List;

import static java.lang.String.format;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
public abstract class JavammRuntimeError extends JavammError {

    protected JavammRuntimeError(final String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return getSimpleMessage();
    }

    public final String getSimpleMessage() {
        return format("Runtime error: %s", super.getMessage());
    }

    public List<StackTraceItem> getCurrentStackTrace() {
        return List.of();
    }
}
