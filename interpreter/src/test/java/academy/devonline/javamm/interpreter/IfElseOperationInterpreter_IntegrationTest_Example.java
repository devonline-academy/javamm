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

package academy.devonline.javamm.interpreter;

import academy.devonline.javamm.code.fragment.ByteCode;
import academy.devonline.javamm.code.fragment.SourceLine;
import academy.devonline.javamm.code.fragment.expression.ConstantExpression;
import academy.devonline.javamm.code.fragment.expression.PostfixNotationComplexExpression;
import academy.devonline.javamm.code.fragment.operation.Block;
import academy.devonline.javamm.code.fragment.operation.IfElseOperation;
import academy.devonline.javamm.code.fragment.operator.BinaryOperator;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * See academy.devonline.javamm.vm.integration.operation.IfElseOperation_IntegrationTest
 *
 * @author devonline
 * @link http://devonline.academy/javamm
 */
@Disabled
class IfElseOperationInterpreter_IntegrationTest_Example {

    private final Interpreter interpreter = new InterpreterConfigurator().getInterpreter();

    /*
    if ( 8 + 10 ) {
    }
     */
    @Test
    void Should_throw_runtime_error() {
        final ByteCode byteCode = () -> new Block(
            new IfElseOperation(
                new SourceLine("test", 2, List.of()),
                // Condition
                new PostfixNotationComplexExpression(
                    List.of(
                        ConstantExpression.valueOf(8),
                        ConstantExpression.valueOf(10),
                        BinaryOperator.ARITHMETIC_ADDITION),
                    "8 + 10"
                ),
                // True block
                new Block(
                    List.of(),
                    new SourceLine("test", 2, List.of())
                )
            ), new SourceLine("test", 2, List.of()));

        final JavammRuntimeError error = assertThrows(JavammRuntimeError.class, () -> interpreter.interpret(byteCode));
        assertEquals(
            "Runtime error in 'test' [Line: 2]: Condition expression should be boolean. Current type is integer",
            error.getMessage());
    }
}
