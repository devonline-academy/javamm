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

package academy.devonline.javamm.interpreter.component.impl.operation.simple;

import academy.devonline.javamm.code.component.ExpressionContext;
import academy.devonline.javamm.code.fragment.Expression;
import academy.devonline.javamm.code.fragment.SourceLine;
import academy.devonline.javamm.code.fragment.Variable;
import academy.devonline.javamm.code.fragment.operation.VariableDeclarationOperation;
import academy.devonline.javamm.interpreter.component.OperationInterpreter;
import academy.devonline.javamm.interpreter.component.impl.error.JavammLineRuntimeError;
import academy.devonline.javamm.interpreter.model.CurrentRuntime;
import academy.devonline.javamm.interpreter.model.LocalContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static academy.devonline.javamm.interpreter.TestRuntimeUtils.getCurrentTestRuntime;
import static academy.devonline.javamm.interpreter.model.CurrentRuntimeProvider.setCurrentRuntime;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class VariableDeclarationOperationInterpreter_UnitTest {

    private final SourceLine sourceLine = new SourceLine("module1", 5, List.of());

    @Mock
    private ExpressionContext expressionContext;

    @Mock
    private Variable variable;

    @Mock
    private Expression expression;

    private OperationInterpreter<VariableDeclarationOperation> operationInterpreter;

    @BeforeEach
    void beforeEach() {
        operationInterpreter = new VariableDeclarationOperationInterpreter(expressionContext);
    }

    @Test
    @Order(1)
    void getOperationClass_should_return_VariableDeclarationOperation_class() {
        assertEquals(VariableDeclarationOperation.class, operationInterpreter.getOperationClass());
    }

    @Test
    @Order(2)
    void interpret_should_set_variable_value() {
        final CurrentRuntime currentRuntime = getCurrentTestRuntime(sourceLine);
        final LocalContext localContext = currentRuntime.getCurrentLocalContext();
        when(localContext.isVariableDefined(variable)).thenReturn(false);
        when(expression.getValue(expressionContext)).thenReturn(2);
        setCurrentRuntime(currentRuntime);

        operationInterpreter.interpret(
            new VariableDeclarationOperation(sourceLine, false, variable, expression));

        verify(localContext).setVariableValue(variable, 2);
        verify(localContext, never()).setFinalValue(any(), any());
    }

    @Test
    @Order(3)
    void interpret_should_set_final_value() {
        final CurrentRuntime currentRuntime = getCurrentTestRuntime(sourceLine);
        final LocalContext localContext = currentRuntime.getCurrentLocalContext();
        when(expression.getValue(expressionContext)).thenReturn(2);
        setCurrentRuntime(currentRuntime);

        operationInterpreter.interpret(
            new VariableDeclarationOperation(sourceLine, true, variable, expression));

        verify(localContext).setFinalValue(variable, 2);
        verify(localContext, never()).setVariableValue(any(), any());
    }

    @Test
    @Order(4)
    void interpret_should_throw_error_if_variable_is_already_defined() {
        final CurrentRuntime currentRuntime = getCurrentTestRuntime(sourceLine);
        final LocalContext localContext = currentRuntime.getCurrentLocalContext();
        when(variable.toString()).thenReturn("a");
        when(localContext.isVariableDefined(variable)).thenReturn(true);
        setCurrentRuntime(currentRuntime);

        final JavammLineRuntimeError error = assertThrows(JavammLineRuntimeError.class, () ->
            operationInterpreter.interpret(
                new VariableDeclarationOperation(sourceLine, false, variable, expression)));
        assertEquals("Runtime error: Variable 'a' already defined", error.getSimpleMessage());
    }
}
