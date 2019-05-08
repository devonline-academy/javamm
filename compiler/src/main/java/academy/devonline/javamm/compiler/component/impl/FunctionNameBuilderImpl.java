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

package academy.devonline.javamm.compiler.component.impl;

import academy.devonline.javamm.code.fragment.FunctionName;
import academy.devonline.javamm.code.fragment.SourceLine;
import academy.devonline.javamm.code.fragment.function.AbstractFunctionName;
import academy.devonline.javamm.compiler.component.FunctionNameBuilder;
import academy.devonline.javamm.compiler.component.impl.error.JavammLineSyntaxError;

import java.util.List;

import static academy.devonline.javamm.code.fragment.SourceLine.EMPTY_SOURCE_LINE;
import static academy.devonline.javamm.compiler.component.impl.util.SyntaxValidationUtils.DeveloperObject.FUNCTION;
import static academy.devonline.javamm.compiler.component.impl.util.SyntaxValidationUtils.validateThatFirstCharacterIsLetter;
import static academy.devonline.javamm.compiler.component.impl.util.SyntaxValidationUtils.validateThatNameIsNotKeyword;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
public class FunctionNameBuilderImpl implements FunctionNameBuilder {

    @Override
    public boolean isValid(final String name) {
        try {
            validateFunctionName(name, EMPTY_SOURCE_LINE);
            return true;
        } catch (final JavammLineSyntaxError e) {
            return false;
        }
    }

    @Override
    public FunctionName build(final String name, final List<?> arguments, final SourceLine sourceLine) {
        validateFunctionName(name, sourceLine);
        return new DeveloperFunctionName(name, arguments);
    }

    private void validateFunctionName(final String name, final SourceLine sourceLine) {
        validateThatFirstCharacterIsLetter(FUNCTION, name, sourceLine);
        validateThatNameIsNotKeyword(FUNCTION, name, sourceLine);
    }

    /**
     * @author devonline
     * @link http://devonline.academy/javamm
     */
    private static final class DeveloperFunctionName extends AbstractFunctionName {

        private DeveloperFunctionName(final String name, final List<?> arguments) {
            super(name, arguments.size());
        }
    }
}
