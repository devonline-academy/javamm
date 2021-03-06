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

import academy.devonline.javamm.code.fragment.Operation;
import academy.devonline.javamm.code.fragment.SourceLine;
import academy.devonline.javamm.code.fragment.function.DeveloperFunction;
import academy.devonline.javamm.interpreter.component.FunctionInvoker;

import java.util.List;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
public interface CurrentRuntime {

    FunctionInvoker getCurrentFunctionInvoker();

    SourceLine getCurrentSourceLine();

    void setCurrentSourceLine(SourceLine currentSourceLine);

    LocalContext getCurrentLocalContext();

    void setCurrentLocalContext(LocalContext localContext);

    default void setCurrentOperation(final Operation operation) {
        setCurrentSourceLine(operation.getSourceLine());
    }

    void enterToFunction(DeveloperFunction developerFunction);

    void exitFromFunction();

    List<StackTraceItem> getCurrentStackTrace();
}
