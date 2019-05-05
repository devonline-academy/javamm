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

package academy.devonline.javamm.interpreter.component.impl;

import academy.devonline.javamm.code.fragment.Variable;
import academy.devonline.javamm.interpreter.component.impl.error.JavammLineRuntimeError;
import academy.devonline.javamm.interpreter.model.LocalContext;

import java.util.HashMap;
import java.util.Map;

import static java.lang.String.format;
import static java.util.Objects.requireNonNull;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
public class LocalContextImpl implements LocalContext {

    private final Map<Variable, Object> variables = new HashMap<>();

    private final Map<Variable, Object> finals = new HashMap<>();

    @Override
    public void setFinalValue(final Variable variable, final Object value) {
        validateThatVariableIsNotFinal(requireNonNull(variable));
        finals.put(variable, value);
    }

    @Override
    public void setVariableValue(final Variable variable, final Object value) {
        validateThatVariableIsNotFinal(requireNonNull(variable));
        variables.put(variable, value);
    }

    @Override
    public boolean isVariableDefined(final Variable variable) {
        requireNonNull(variable);
        return variables.containsKey(variable) || finals.containsKey(variable);
    }

    @Override
    public Object getVariableValue(final Variable variable) {
        if (variables.containsKey(variable)) {
            return variables.get(variable);
        } else {
            return finals.get(variable);
        }
    }

    private void validateThatVariableIsNotFinal(final Variable variable) {
        if (finals.containsKey(variable)) {
            throw new JavammLineRuntimeError(format("Final variable '%s' can't be changed", variable.getName()));
        }
    }
}
