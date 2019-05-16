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

import academy.devonline.javamm.code.fragment.SourceLine;
import academy.devonline.javamm.code.fragment.Variable;
import academy.devonline.javamm.compiler.component.VariableBuilder;

import static academy.devonline.javamm.compiler.component.impl.util.SyntaxValidationUtils.DeveloperObject.VARIABLE;
import static academy.devonline.javamm.compiler.component.impl.util.SyntaxValidationUtils.validateThatFirstCharacterIsLetter;
import static academy.devonline.javamm.compiler.component.impl.util.SyntaxValidationUtils.validateThatNameIsNotKeyword;
import static java.util.Objects.requireNonNull;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
public class VariableBuilderImpl implements VariableBuilder {

    @Override
    public Variable build(final String name, final SourceLine sourceLine) {
        validateVariableName(name, sourceLine);
        return new VariableImpl(name);
    }

    private void validateVariableName(final String variableName, final SourceLine sourceLine) {
        validateThatFirstCharacterIsLetter(VARIABLE, variableName, sourceLine);
        validateThatNameIsNotKeyword(VARIABLE, variableName, sourceLine);
    }

    /**
     * @author devonline
     * @link http://devonline.academy/javamm
     */
    private static final class VariableImpl implements Variable {

        private final String name;

        private VariableImpl(final String name) {
            this.name = requireNonNull(name);
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return name;
        }

        @SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
        @Override
        public boolean equals(final Object o) {
            return name.equals(((Variable) o).getName());
        }

        @Override
        public int hashCode() {
            return getName().hashCode();
        }
    }
}
