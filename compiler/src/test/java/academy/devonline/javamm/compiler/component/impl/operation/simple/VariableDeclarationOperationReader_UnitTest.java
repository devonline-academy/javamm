/*
 * Copyright (c) 2019. http://devonline.academy
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package academy.devonline.javamm.compiler.component.impl.operation.simple;

import academy.devonline.javamm.compiler.component.ExpressionResolver;
import academy.devonline.javamm.compiler.component.OperationReader;
import academy.devonline.javamm.compiler.component.VariableBuilder;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static academy.devonline.javamm.code.syntax.Keywords.VAR;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class VariableDeclarationOperationReader_UnitTest extends AbstractVarOrFinalDeclarationOperationReaderUnitTest {

    @Override
    final boolean isConstantExpected() {
        return false;
    }

    @Override
    final String getOperationKeyword() {
        return VAR;
    }

    @Override
    final OperationReader buildOperationReader(final VariableBuilder variableBuilder, final ExpressionResolver expressionResolver) {
        return new VariableDeclarationOperationReader(variableBuilder, expressionResolver);
    }
}