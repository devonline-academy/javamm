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

import academy.devonline.javamm.code.component.ExpressionContext;
import academy.devonline.javamm.code.fragment.Expression;
import academy.devonline.javamm.code.fragment.Operation;
import academy.devonline.javamm.code.fragment.SourceLine;
import academy.devonline.javamm.code.fragment.Variable;
import academy.devonline.javamm.code.fragment.expression.NullValueExpression;
import academy.devonline.javamm.code.fragment.operation.VariableDeclarationOperation;
import academy.devonline.javamm.compiler.component.ExpressionResolver;
import academy.devonline.javamm.compiler.component.OperationReader;
import academy.devonline.javamm.compiler.component.VariableBuilder;
import academy.devonline.javamm.compiler.component.impl.error.JavammLineSyntaxError;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.List.of;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
abstract class AbstractVarOrFinalDeclarationOperationReaderUnitTest {

    @Mock
    private VariableBuilder variableBuilder;

    @Mock
    private ExpressionResolver expressionResolver;

    @Mock
    private Variable variable;

    @Mock
    private ListIterator<SourceLine> iterator;

    @Mock
    private ExpressionContext expressionContext;

    @Mock
    private Expression expression;

    private OperationReader reader;

    abstract boolean isConstantExpected();

    abstract String getOperationKeyword();

    abstract OperationReader buildOperationReader(final VariableBuilder variableBuilder, final ExpressionResolver expressionResolver);

    @BeforeEach
    void beforeEach() {
        reader = buildOperationReader(variableBuilder, expressionResolver);
    }

    @Test
    @Order(1)
    void getOperationKeyword_should_return_correct_keyword() {
        assertTrue(reader.canRead(new SourceLine("module1", 5, of(getOperationKeyword()))));
    }

    @Test
    @Order(2)
    void read_should_assign_the_null_value_expression_to_the_variable() {
        final SourceLine sourceLine = new SourceLine("module1", 5, of(getOperationKeyword(), "a"));
        when(variableBuilder.build("a", sourceLine)).thenReturn(variable);

        final Operation operation = reader.read(sourceLine, iterator);

        assertEquals(VariableDeclarationOperation.class, operation.getClass());
        final VariableDeclarationOperation variableDeclarationOperation = (VariableDeclarationOperation) operation;
        assertEquals(isConstantExpected(), variableDeclarationOperation.isConstant());
        assertEquals(variable, variableDeclarationOperation.getVariable());
        assertEquals(NullValueExpression.class, variableDeclarationOperation.getExpression().getClass());
    }

    @Test
    @Order(3)
    void read_should_assign_the_expression_returned_by_expressionResolver_to_the_variable() {
        final SourceLine sourceLine = new SourceLine("module1", 5, of(getOperationKeyword(), "a", "=", "1"));
        when(variableBuilder.build("a", sourceLine)).thenReturn(variable);
        when(expressionResolver.resolve(List.of("1"), sourceLine)).thenReturn(expression);

        final Operation operation = reader.read(sourceLine, iterator);

        assertEquals(VariableDeclarationOperation.class, operation.getClass());
        final VariableDeclarationOperation variableDeclarationOperation = (VariableDeclarationOperation) operation;
        assertEquals(isConstantExpected(), variableDeclarationOperation.isConstant());
        assertEquals(variable, variableDeclarationOperation.getVariable());
        assertSame(expression, variableDeclarationOperation.getExpression());
    }

    @Test
    @Order(4)
    void read_should_throw_error_if_variable_name_is_missing() {
        final SourceLine sourceLine = new SourceLine("module1", 5, of(getOperationKeyword()));

        final JavammLineSyntaxError error = assertThrows(JavammLineSyntaxError.class, () ->
            reader.read(sourceLine, iterator));
        assertEquals("Syntax error in 'module1' [Line: 5]: Missing a variable name", error.getMessage());
    }

    @Test
    @Order(5)
    void read_should_throw_error_if_variable_expression_is_missing() {
        final SourceLine sourceLine = new SourceLine("module1", 5, of(getOperationKeyword(), "a", "="));

        final JavammLineSyntaxError error = assertThrows(JavammLineSyntaxError.class, () ->
            reader.read(sourceLine, iterator));
        assertEquals("Syntax error in 'module1' [Line: 5]: Missing a variable expression", error.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "a +",
        "+ a",
        "= a",
        "a + 3",
        "a 3 ="
    })
    @Order(6)
    void read_should_throw_error_if_assignment_operator_is_missing(final String expression) {
        final List<String> tokens = Stream
            .of(
                Stream.of(getOperationKeyword()),
                Arrays.stream(expression.split(" "))
            )
            .flatMap(Function.identity())
            .collect(Collectors.toUnmodifiableList());
        final SourceLine sourceLine = new SourceLine("module1", 5, tokens);

        final JavammLineSyntaxError error = assertThrows(JavammLineSyntaxError.class, () ->
            reader.read(sourceLine, iterator));
        assertEquals("Syntax error in 'module1' [Line: 5]: Missing or invalid position of '='", error.getMessage());
    }
}
