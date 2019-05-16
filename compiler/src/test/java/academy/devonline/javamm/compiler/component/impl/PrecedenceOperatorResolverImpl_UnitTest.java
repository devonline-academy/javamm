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

package academy.devonline.javamm.compiler.component.impl;

import academy.devonline.javamm.code.exception.ConfigException;
import academy.devonline.javamm.code.fragment.Operator;
import academy.devonline.javamm.code.fragment.operator.BinaryOperator;
import academy.devonline.javamm.compiler.CompilerConfigurator;
import academy.devonline.javamm.compiler.component.PrecedenceOperatorResolver;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PrecedenceOperatorResolverImpl_UnitTest {

    @Mock
    private Operator operator;

    @Test
    @Order(1)
    void Constructor_should_throw_Config_error_if_precedence_not_defined_for_operator() {
        final BinaryOperator firstBinaryOperator = BinaryOperator.values()[0];

        final ConfigException exception = assertThrows(ConfigException.class, () ->
            new PrecedenceOperatorResolverImpl(Map.of()));
        assertEquals("Precedence not defined for " + firstBinaryOperator.getCode(), exception.getMessage());
    }

    @Test
    @Order(2)
    void getPrecedence_should_throw_Config_error_if_precedence_not_defined_for_operator() {
        when(operator.getCode()).thenReturn("#");
        final PrecedenceOperatorResolver precedenceOperatorResolver = new CompilerConfigurator().getPrecedenceOperatorResolver();

        final ConfigException exception = assertThrows(ConfigException.class, () ->
            precedenceOperatorResolver.getPrecedence(operator));
        assertEquals("Precedence not defined for #", exception.getMessage());
    }


}