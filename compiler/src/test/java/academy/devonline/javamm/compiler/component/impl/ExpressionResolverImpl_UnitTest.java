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

import academy.devonline.javamm.code.fragment.Expression;
import academy.devonline.javamm.code.fragment.SourceLine;
import academy.devonline.javamm.compiler.JavammSyntaxError;
import academy.devonline.javamm.compiler.component.ExpressionBuilder;
import academy.devonline.javamm.compiler.component.ExpressionResolver;
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
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ExpressionResolverImpl_UnitTest {

    private final SourceLine sourceLine = new SourceLine("module1", 5, List.of());

    @Mock
    private List<String> expressionTokens;

    @Mock
    private ExpressionBuilder expressionBuilder1;

    @Mock
    private Expression expectedExpression;

    private ExpressionResolver expressionResolver;

    @BeforeEach
    void beforeEach() {
        expressionResolver = new ExpressionResolverImpl(Set.of(expressionBuilder1));
    }

    @Test
    @Order(1)
    void Should_use_the_expression_builders_only() {
        when(expressionBuilder1.canBuild(expressionTokens)).thenReturn(true);
        when(expressionBuilder1.build(expressionTokens, sourceLine)).thenReturn(expectedExpression);

        final Expression actualExpression = assertDoesNotThrow(() ->
            expressionResolver.resolve(expressionTokens, sourceLine));
        assertSame(expectedExpression, actualExpression);
    }

    @Test
    @Order(2)
    void Should_throw_JavammSyntaxError_if_expression_cant_be_resolved() {
        final List<String> invalidExpressions = List.of("#");

        final JavammSyntaxError syntaxError = assertThrows(JavammSyntaxError.class, () ->
            expressionResolver.resolve(invalidExpressions, sourceLine));
        assertEquals("Syntax error in 'module1' [Line: 5]: Unsupported expression: #", syntaxError.getMessage());
    }
}