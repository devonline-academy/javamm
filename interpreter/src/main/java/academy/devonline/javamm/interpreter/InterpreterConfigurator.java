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
import academy.devonline.javamm.interpreter.component.impl.calculator.arithmetic.DecrementUnaryExpressionCalculator;
import academy.devonline.javamm.interpreter.component.impl.calculator.arithmetic.DivisionBinaryExpressionCalculator;
import academy.devonline.javamm.interpreter.component.impl.calculator.arithmetic.IncrementUnaryExpressionCalculator;
import academy.devonline.javamm.interpreter.component.impl.calculator.arithmetic.MinusUnaryExpressionCalculator;
import academy.devonline.javamm.interpreter.component.impl.calculator.arithmetic.ModulusBinaryExpressionCalculator;
import academy.devonline.javamm.interpreter.component.impl.calculator.arithmetic.MultiplicationBinaryExpressionCalculator;
import academy.devonline.javamm.interpreter.component.impl.calculator.arithmetic.PlusUnaryExpressionCalculator;
import academy.devonline.javamm.interpreter.component.impl.calculator.arithmetic.SubtractionBinaryExpressionCalculator;
import academy.devonline.javamm.interpreter.component.impl.calculator.bitwise.BitwiseAndBinaryExpressionCalculator;
import academy.devonline.javamm.interpreter.component.impl.calculator.bitwise.BitwiseInverseUnaryExpressionCalculator;
import academy.devonline.javamm.interpreter.component.impl.calculator.bitwise.BitwiseOrBinaryExpressionCalculator;
import academy.devonline.javamm.interpreter.component.impl.calculator.bitwise.BitwiseShiftLeftBinaryExpressionCalculator;
import academy.devonline.javamm.interpreter.component.impl.calculator.bitwise.BitwiseShiftRightBinaryExpressionCalculator;
import academy.devonline.javamm.interpreter.component.impl.calculator.bitwise.BitwiseShiftRightZeroFillBinaryExpressionCalculator;
import academy.devonline.javamm.interpreter.component.impl.calculator.bitwise.BitwiseXorBinaryExpressionCalculator;
import academy.devonline.javamm.interpreter.component.impl.calculator.logic.LogicAndBinaryExpressionCalculator;
import academy.devonline.javamm.interpreter.component.impl.calculator.logic.LogicNotUnaryExpressionCalculator;
import academy.devonline.javamm.interpreter.component.impl.calculator.logic.LogicOrBinaryExpressionCalculator;
import academy.devonline.javamm.interpreter.component.impl.calculator.predicate.IsEqualsBinaryExpressionCalculator;
import academy.devonline.javamm.interpreter.component.impl.calculator.predicate.IsGreaterBinaryExpressionCalculator;
import academy.devonline.javamm.interpreter.component.impl.calculator.predicate.IsGreaterOrEqualsBinaryExpressionCalculator;
import academy.devonline.javamm.interpreter.component.impl.calculator.predicate.IsLessBinaryExpressionCalculator;
import academy.devonline.javamm.interpreter.component.impl.calculator.predicate.IsLessOrEqualsBinaryExpressionCalculator;
import academy.devonline.javamm.interpreter.component.impl.calculator.predicate.IsNotEqualsBinaryExpressionCalculator;
import academy.devonline.javamm.interpreter.component.impl.calculator.predicate.TypeOfBinaryExpressionCalculator;
import academy.devonline.javamm.interpreter.component.impl.expression.evaluator.PostfixNotationComplexExpressionEvaluator;
import academy.devonline.javamm.interpreter.component.impl.expression.evaluator.VariableExpressionEvaluator;
import academy.devonline.javamm.interpreter.component.impl.expression.updater.VariableExpressionUpdater;
import academy.devonline.javamm.interpreter.component.impl.operation.block.IfElseOperationInterpreter;
import academy.devonline.javamm.interpreter.component.impl.operation.block.SimpleBlockOperationInterpreter;
import academy.devonline.javamm.interpreter.component.impl.operation.block.loop.DoWhileOperationInterpreter;
import academy.devonline.javamm.interpreter.component.impl.operation.block.loop.WhileOperationInterpreter;
import academy.devonline.javamm.interpreter.component.impl.operation.simple.PrintlnOperationInterpreter;
import academy.devonline.javamm.interpreter.component.impl.operation.simple.VariableDeclarationOperationInterpreter;

import java.util.Set;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
public class InterpreterConfigurator {

    private CalculatorFacade calculatorFacade = new CalculatorFacadeImpl(Set.of(
        // ------- Arithmetic -----------------------------------------
        AdditionBinaryExpressionCalculator.createArithmeticCalculator(),
        AdditionBinaryExpressionCalculator.createAssignmentCalculator(),

        SubtractionBinaryExpressionCalculator.createArithmeticCalculator(),
        SubtractionBinaryExpressionCalculator.createAssignmentCalculator(),

        MultiplicationBinaryExpressionCalculator.createArithmeticCalculator(),
        MultiplicationBinaryExpressionCalculator.createAssignmentCalculator(),

        DivisionBinaryExpressionCalculator.createArithmeticCalculator(),
        DivisionBinaryExpressionCalculator.createAssignmentCalculator(),

        ModulusBinaryExpressionCalculator.createArithmeticCalculator(),
        ModulusBinaryExpressionCalculator.createAssignmentCalculator(),
        // ------ Bitwise -------------------------------------------------
        BitwiseAndBinaryExpressionCalculator.createArithmeticCalculator(),
        BitwiseAndBinaryExpressionCalculator.createAssignmentCalculator(),

        BitwiseOrBinaryExpressionCalculator.createArithmeticCalculator(),
        BitwiseOrBinaryExpressionCalculator.createAssignmentCalculator(),

        BitwiseXorBinaryExpressionCalculator.createArithmeticCalculator(),
        BitwiseXorBinaryExpressionCalculator.createAssignmentCalculator(),

        BitwiseShiftLeftBinaryExpressionCalculator.createArithmeticCalculator(),
        BitwiseShiftLeftBinaryExpressionCalculator.createAssignmentCalculator(),

        BitwiseShiftRightBinaryExpressionCalculator.createArithmeticCalculator(),
        BitwiseShiftRightBinaryExpressionCalculator.createAssignmentCalculator(),

        BitwiseShiftRightZeroFillBinaryExpressionCalculator.createArithmeticCalculator(),
        BitwiseShiftRightZeroFillBinaryExpressionCalculator.createAssignmentCalculator(),
        // ------ Logic -------------------------------------------------
        new LogicAndBinaryExpressionCalculator(),
        new LogicOrBinaryExpressionCalculator(),
        // ------ Predicate ---------------------------------------------
        new IsEqualsBinaryExpressionCalculator(),
        new IsNotEqualsBinaryExpressionCalculator(),
        new TypeOfBinaryExpressionCalculator(),
        new IsGreaterBinaryExpressionCalculator(),
        new IsGreaterOrEqualsBinaryExpressionCalculator(),
        new IsLessBinaryExpressionCalculator(),
        new IsLessOrEqualsBinaryExpressionCalculator()
    ), Set.of(
        // ------- Arithmetic -----------------------------------------
        new IncrementUnaryExpressionCalculator(),
        new DecrementUnaryExpressionCalculator(),
        new PlusUnaryExpressionCalculator(),
        new MinusUnaryExpressionCalculator(),
        // ------ Bitwise -------------------------------------------------
        new BitwiseInverseUnaryExpressionCalculator(),
        // ------ Logic -------------------------------------------------
        new LogicNotUnaryExpressionCalculator()
    ));

    private Set<ExpressionEvaluator<?>> expressionEvaluators = Set.of(
        new VariableExpressionEvaluator(),
        new PostfixNotationComplexExpressionEvaluator(calculatorFacade)
    );

    private Set<ExpressionUpdater<?>> expressionUpdaters = Set.of(
        new VariableExpressionUpdater()
    );

    private ExpressionContext expressionContext = new ExpressionContextImpl(expressionEvaluators, expressionUpdaters);

    private Set<OperationInterpreter<?>> operationInterpreters = Set.of(
        new PrintlnOperationInterpreter(expressionContext),
        new VariableDeclarationOperationInterpreter(expressionContext),
        new IfElseOperationInterpreter(expressionContext, calculatorFacade),
        new WhileOperationInterpreter(expressionContext, calculatorFacade),
        new DoWhileOperationInterpreter(expressionContext, calculatorFacade),
        new SimpleBlockOperationInterpreter(expressionContext)
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
