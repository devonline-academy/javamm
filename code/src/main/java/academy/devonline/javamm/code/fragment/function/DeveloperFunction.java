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

package academy.devonline.javamm.code.fragment.function;

import academy.devonline.javamm.code.fragment.FunctionName;
import academy.devonline.javamm.code.fragment.SourceLine;
import academy.devonline.javamm.code.fragment.Variable;
import academy.devonline.javamm.code.fragment.operation.Block;

import java.util.List;

import static java.lang.String.format;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.joining;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
public final class DeveloperFunction extends AbstractFunction {

    private final List<Variable> parameters;

    private final Block body;

    private final SourceLine declarationSourceLine;

    private DeveloperFunction(final FunctionName name,
                              final List<Variable> parameters,
                              final Block body,
                              final SourceLine declarationSourceLine) {
        super(name);
        this.parameters = List.copyOf(parameters);
        this.body = requireNonNull(body);
        this.declarationSourceLine = requireNonNull(declarationSourceLine);
    }

    public List<Variable> getParameters() {
        return parameters;
    }

    public Block getBody() {
        return body;
    }

    public SourceLine getDeclarationSourceLine() {
        return declarationSourceLine;
    }

    @Override
    public String toString() {
        return format("%s(%s)",
            getName().getName(),
            parameters.stream().map(Object::toString).collect(joining(",")));
    }

    /**
     * @author devonline
     * @link http://devonline.academy/javamm
     */
    public static final class Builder {

        private FunctionName name;

        private List<Variable> parameters;

        private Block body;

        private SourceLine declarationSourceLine;

        public Builder setName(final FunctionName name) {
            this.name = requireNonNull(name);
            return this;
        }

        public Builder setParameters(final List<Variable> parameters) {
            this.parameters = requireNonNull(parameters);
            return this;
        }

        public Builder setBody(final Block body) {
            this.body = requireNonNull(body);
            return this;
        }

        public Builder setDeclarationSourceLine(final SourceLine declarationSourceLine) {
            this.declarationSourceLine = requireNonNull(declarationSourceLine);
            return this;
        }

        public DeveloperFunction build() {
            return new DeveloperFunction(
                name,
                parameters == null ? List.of() : parameters,
                body,
                declarationSourceLine);
        }
    }
}
