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

package academy.devonline.javamm.code.fragment.operation;

import academy.devonline.javamm.code.fragment.Expression;
import academy.devonline.javamm.code.fragment.SourceLine;
import academy.devonline.javamm.code.fragment.Variable;

import static java.util.Objects.requireNonNull;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
public final class VariableDeclarationOperation extends AbstractOperation {

    private final boolean constant;

    private final Variable variable;

    private final Expression expression;

    public VariableDeclarationOperation(final SourceLine sourceLine,
                                        final boolean constant,
                                        final Variable variable,
                                        final Expression expression) {
        super(sourceLine);
        this.constant = constant;
        this.variable = requireNonNull(variable);
        this.expression = requireNonNull(expression);
    }

    public boolean isConstant() {
        return constant;
    }

    public Variable getVariable() {
        return variable;
    }

    public Expression getExpression() {
        return expression;
    }
}
