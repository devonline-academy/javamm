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

package academy.devonline.javamm.compiler.integration;

import academy.devonline.javamm.code.fragment.ByteCode;
import academy.devonline.javamm.code.fragment.SourceCode;
import academy.devonline.javamm.compiler.Compiler;
import academy.devonline.javamm.compiler.CompilerConfigurator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
public abstract class AbstractIntegrationTest {

    private final Compiler compiler = new CompilerConfigurator().getCompiler();

    protected ByteCode compile(final List<String> lines) {
        return compiler.compile(new SourceCode() {
            @Override
            public String getModuleName() {
                return "module1";
            }

            @Override
            public List<String> getLines() {
                return Collections.unmodifiableList(lines);
            }
        });
    }

    ByteCode wrapMainFunctionAndCompile(final List<String> lines,
                                        final boolean withClosingCurlyBrace) {
        final List<String> validLines = new ArrayList<>();
        validLines.add("function main(){");
        validLines.addAll(lines);
        if (withClosingCurlyBrace) {
            validLines.add("}");
        }
        return compile(validLines);
    }
}
