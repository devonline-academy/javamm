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
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
class StringSourceCode_UnitTest {

    private final SourceCode sourceCode = new StringSourceCode("module1", asList(
        "function main(){",
        "   println('Hello world')",
        "}"
    ));

    @Test
    void getModuleName_should_return_moduleName() {
        assertEquals("module1", sourceCode.getModuleName());
    }

    @Test
    void getLines_should_return_unmodifiable_list_of_lines() {
        assertEquals(List.of(
            "function main(){",
            "   println('Hello world')",
            "}"
        ), sourceCode.getLines());
        assertEquals(List.copyOf(Collections.emptyList()).getClass(), sourceCode.getLines().getClass());
    }
}