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

package academy.devonline.javamm.ide.model;

import academy.devonline.javamm.code.fragment.SourceCode;

import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
public class StringSourceCode implements SourceCode {

    private final String moduleName;

    private final List<String> lines;

    public StringSourceCode(final String moduleName,
                            final List<String> lines) {
        this.moduleName = requireNonNull(moduleName);
        this.lines = List.copyOf(lines);
    }

    @Override
    public String getModuleName() {
        return moduleName;
    }

    @Override
    public List<String> getLines() {
        return lines;
    }
}
