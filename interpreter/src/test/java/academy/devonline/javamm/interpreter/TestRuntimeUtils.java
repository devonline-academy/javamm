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

import academy.devonline.javamm.code.fragment.SourceLine;
import academy.devonline.javamm.interpreter.model.CurrentRuntime;
import academy.devonline.javamm.interpreter.model.LocalContext;

import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
public final class TestRuntimeUtils {

    private TestRuntimeUtils() {

    }

    public static CurrentRuntime getCurrentTestRuntime(final SourceLine sourceLine) {
        final LocalContext localContext = mock(LocalContext.class);
        final CurrentRuntime currentRuntime = mock(CurrentRuntime.class);

        lenient().when(currentRuntime.getCurrentSourceLine()).thenReturn(sourceLine);
        lenient().when(currentRuntime.getCurrentLocalContext()).thenReturn(localContext);

        return currentRuntime;
    }
}
