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

package academy.devonline.javamm.interpreter.model;

import static java.util.Objects.requireNonNull;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
public final class CurrentRuntimeProvider {

    // Static variable can be used as well
    private static final ThreadLocal<CurrentRuntime> CURRENT_RUNTIME_THREAD_LOCAL = new ThreadLocal<>();

    private CurrentRuntimeProvider() {
    }

    public static CurrentRuntime getCurrentRuntime() {
        return requireNonNull(CURRENT_RUNTIME_THREAD_LOCAL.get(), "CurrentRuntime is not set");
    }

    public static void setCurrentRuntime(final CurrentRuntime currentRuntime) {
        CURRENT_RUNTIME_THREAD_LOCAL.set(requireNonNull(currentRuntime, "CurrentRuntime can't be null"));
    }

    public static void releaseCurrentRuntime() {
        CURRENT_RUNTIME_THREAD_LOCAL.remove();
    }
}
