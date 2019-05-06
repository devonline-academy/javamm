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
import academy.devonline.javamm.code.fragment.Lexeme;
import academy.devonline.javamm.code.fragment.SourceLine;
import academy.devonline.javamm.code.fragment.expression.ComplexExpression;
import academy.devonline.javamm.compiler.component.ComplexExpressionBuilder;
import academy.devonline.javamm.compiler.component.ComplexLexemeValidator;
import academy.devonline.javamm.compiler.component.ExpressionBuilder;
import academy.devonline.javamm.compiler.component.ExpressionResolver;
import academy.devonline.javamm.compiler.component.LexemeBuilder;
import academy.devonline.javamm.compiler.component.impl.error.JavammLineSyntaxError;
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

import static academy.devonline.javamm.code.fragment.Parenthesis.OPENING_PARENTHESIS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
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
class ExpressionResolverImpl_UnitTest {

    private final SourceLine sourceLine = new SourceLine("module1", 5, List.of());

    @Mock
    private List<String> expressionTokens;

    @Mock
    private ExpressionBuilder expressionBuilder1;

    @Mock
    private Expression expectedExpression;

    @Mock
    private LexemeBuilder lexemeBuilder;

    @Mock
    private List<Lexeme> lexemes;

    @Mock
    private ComplexExpressionBuilder complexExpressionBuilder;

    @Mock
    private ComplexExpression complexExpression;

    @Mock
    private ComplexLexemeValidator complexLexemeValidator;

    private ExpressionResolver expressionResolver;

    @BeforeEach
    void beforeEach() {
        expressionResolver = new ExpressionResolverImpl(
            Set.of(expressionBuilder1), lexemeBuilder, complexLexemeValidator, complexExpressionBuilder);
    }

    @Test
    @Order(1)
    void Should_use_the_expression_builders_only() {
        when(expressionBuilder1.canBuild(expressionTokens)).thenReturn(true);
        when(expressionBuilder1.build(expressionTokens, sourceLine)).thenReturn(expectedExpression);

        final Expression actualExpression = expressionResolver.resolve(expressionTokens, sourceLine);

        assertSame(expectedExpression, actualExpression);
        verify(lexemeBuilder, never()).build(any(), any());
        verify(complexExpressionBuilder, never()).build(any(), any());
    }

    @Test
    @Order(2)
    void Should_use_the_lexeme_builder_only() {
        when(lexemeBuilder.build(expressionTokens, sourceLine)).thenReturn(List.of(expectedExpression));

        final Expression actualExpression = expressionResolver.resolve(expressionTokens, sourceLine);

        assertSame(expectedExpression, actualExpression);
        verify(complexExpressionBuilder, never()).build(any(), any());
    }

    @Test
    @Order(3)
    void Should_throw_error_if_lexemeBuilder_returns_one_lexeme_and_it_is_not_an_expression() {
        when(lexemeBuilder.build(expressionTokens, sourceLine)).thenReturn(List.of(OPENING_PARENTHESIS));

        final JavammLineSyntaxError error = assertThrows(JavammLineSyntaxError.class, () ->
            expressionResolver.resolve(expressionTokens, sourceLine));
        assertEquals("Syntax error in 'module1' [Line: 5]: Unresolved expression: (", error.getMessage());
        verify(complexExpressionBuilder, never()).build(any(), any());
    }

    @Test
    @Order(4)
    void Should_use_the_complex_expression_builder() {
        when(lexemeBuilder.build(expressionTokens, sourceLine)).thenReturn(lexemes);
        when(complexExpressionBuilder.build(lexemes, sourceLine)).thenReturn(complexExpression);

        final Expression actualExpression = expressionResolver.resolve(expressionTokens, sourceLine);

        assertSame(complexExpression, actualExpression);
    }
}