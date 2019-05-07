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

package academy.devonline.javamm.compiler.component.impl.operation.simple;

import academy.devonline.javamm.code.fragment.Expression;
import academy.devonline.javamm.code.fragment.SourceLine;
import academy.devonline.javamm.code.fragment.expression.VariableExpression;
import academy.devonline.javamm.code.fragment.operation.VariableAssignmentOperation;
import academy.devonline.javamm.compiler.component.ExpressionResolver;
import academy.devonline.javamm.compiler.component.impl.error.JavammLineSyntaxError;
import academy.devonline.javamm.compiler.component.impl.operation.AbstractOperationReader;

import java.util.List;
import java.util.ListIterator;

import static academy.devonline.javamm.code.syntax.Keywords.KEYWORDS;
import static java.util.Objects.requireNonNull;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
public class VariableAssignmentOperationReader extends AbstractOperationReader<VariableAssignmentOperation> {

    private final ExpressionResolver expressionResolver;

    public VariableAssignmentOperationReader(final ExpressionResolver expressionResolver) {
        this.expressionResolver = requireNonNull(expressionResolver);
    }

    // Example:
    // var1 = 5
    // array1 [ 4 ] = 5
    // array1 [ 4  + 2 * a ] = 5
    @Override
    public boolean canRead(final SourceLine sourceLine) {
        return !KEYWORDS.contains(sourceLine.getFirst()) && sourceLine.contains("=");
    }

    @Override
    protected void validate(final SourceLine sourceLine) {
        if (sourceLine.getTokenCount() <= 2) {
            throw new JavammLineSyntaxError("Missing variable or expression", sourceLine);
        }
    }

    @Override
    protected VariableAssignmentOperation get(final SourceLine sourceLine,
                                              final ListIterator<SourceLine> iterator) {
        final int assignmentIndex = sourceLine.indexOf("=");
        final List<String> variableTokens = sourceLine.subList(0, assignmentIndex);
        final List<String> valueTokens = sourceLine.subList(assignmentIndex + 1);

        final Expression expression = expressionResolver.resolve(variableTokens, sourceLine);
        if (!(expression instanceof VariableExpression)) {
            throw new JavammLineSyntaxError("The assignment is possible to variable expression only", sourceLine);
        }
        final Expression valueExpression = expressionResolver.resolve(valueTokens, sourceLine);
        return new VariableAssignmentOperation(sourceLine, (VariableExpression) expression, valueExpression);
    }
}
