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

import academy.devonline.javamm.code.component.ExpressionContext;
import academy.devonline.javamm.interpreter.component.BlockOperationInterpreter;
import academy.devonline.javamm.interpreter.component.CalculatorFacade;
import academy.devonline.javamm.interpreter.component.ExpressionEvaluator;
import academy.devonline.javamm.interpreter.component.ExpressionUpdater;
import academy.devonline.javamm.interpreter.component.OperationInterpreter;
import academy.devonline.javamm.interpreter.component.RuntimeBuilder;
import academy.devonline.javamm.interpreter.component.impl.BlockOperationInterpreterImpl;
import academy.devonline.javamm.interpreter.component.impl.CalculatorFacadeImpl;
import academy.devonline.javamm.interpreter.component.impl.ExpressionContextImpl;
import academy.devonline.javamm.interpreter.component.impl.InterpreterImpl;
import academy.devonline.javamm.interpreter.component.impl.RuntimeBuilderImpl;
import academy.devonline.javamm.interpreter.component.impl.calculator.arithmetic.AdditionBinaryExpressionCalculator;
import academy.devonline.javamm.interpreter.component.impl.calculator.arithmetic.SubtractionBinaryExpressionCalculator;
import academy.devonline.javamm.interpreter.component.impl.calculator.logic.LogicAndBinaryExpressionCalculator;
import academy.devonline.javamm.interpreter.component.impl.expression.evaluator.BinaryExpressionEvaluator;
import academy.devonline.javamm.interpreter.component.impl.expression.evaluator.VariableExpressionEvaluator;
import academy.devonline.javamm.interpreter.component.impl.operation.simple.PrintlnOperationInterpreter;
import academy.devonline.javamm.interpreter.component.impl.operation.simple.VariableDeclarationOperationInterpreter;

import java.util.Set;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
public class InterpreterConfigurator {

    private CalculatorFacade calculatorFacade = new CalculatorFacadeImpl(Set.of(
        new SubtractionBinaryExpressionCalculator(),
        new AdditionBinaryExpressionCalculator(),

        new LogicAndBinaryExpressionCalculator()
    ));

    private Set<ExpressionEvaluator<?>> expressionEvaluators = Set.of(
        new VariableExpressionEvaluator(),
        new BinaryExpressionEvaluator(calculatorFacade)
    );

    private Set<ExpressionUpdater<?>> expressionUpdaters = Set.of(
        //TODO Add here
    );

    private ExpressionContext expressionContext = new ExpressionContextImpl(expressionEvaluators, expressionUpdaters);

    private Set<OperationInterpreter<?>> operationInterpreters = Set.of(
        new PrintlnOperationInterpreter(expressionContext),
        new VariableDeclarationOperationInterpreter(expressionContext)
    );

    private BlockOperationInterpreter blockOperationInterpreter =
        new BlockOperationInterpreterImpl(operationInterpreters);

    private RuntimeBuilder runtimeBuilder = new RuntimeBuilderImpl();

    private Interpreter interpreter = new InterpreterImpl(blockOperationInterpreter, runtimeBuilder);

    public Interpreter getInterpreter() {
        return interpreter;
    }

    public CalculatorFacade getCalculatorFacade() {
        return calculatorFacade;
    }
}
