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

package academy.devonline.javamm.compiler.integration.expression;

import academy.devonline.javamm.code.fragment.Lexeme;
import academy.devonline.javamm.code.fragment.SourceLine;
import academy.devonline.javamm.code.fragment.expression.ComplexExpression;
import academy.devonline.javamm.compiler.CompilerConfigurator;
import academy.devonline.javamm.compiler.component.ComplexExpressionBuilder;
import academy.devonline.javamm.compiler.component.FunctionNameBuilder;
import academy.devonline.javamm.compiler.component.LexemeBuilder;
import academy.devonline.javamm.compiler.component.SingleTokenExpressionBuilder;
import academy.devonline.javamm.compiler.component.VariableBuilder;
import academy.devonline.javamm.compiler.component.impl.LexemeBuilderImpl;
import academy.devonline.javamm.compiler.component.impl.SingleTokenExpressionBuilderImpl;
import academy.devonline.javamm.compiler.component.impl.error.JavammLineSyntaxError;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static java.util.stream.Collectors.joining;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PostfixNotationComplexExpressionBuilder_IntegrationTest {

    private final SourceLine sourceLine = new SourceLine("module1", 5, List.of());

    private final ComplexExpressionBuilder complexExpressionBuilder = new CompilerConfigurator().getComplexExpressionBuilder();

    @Mock
    private VariableBuilder variableBuilder;

    private LexemeBuilder lexemeBuilder;

    @Mock
    private FunctionNameBuilder functionNameBuilder;

    @BeforeEach
    void beforeEach() {
        final SingleTokenExpressionBuilder singleTokenExpressionBuilder = new SingleTokenExpressionBuilderImpl(variableBuilder);
        lexemeBuilder = new LexemeBuilderImpl(singleTokenExpressionBuilder, functionNameBuilder);
    }

    private ComplexExpression build(final String source) {
        final List<String> expressionTokens = List.of(source.split(" "));
        final List<Lexeme> lexemes = lexemeBuilder.build(expressionTokens, sourceLine);
        return complexExpressionBuilder.build(lexemes, sourceLine);
    }

    @ParameterizedTest
    @CsvSource( {
        "3 + 4,                 3 4 +",
        "7 - 2 * 3,             7 2 3 * -",
        "5 * 2 + 10,            5 2 * 10 +",
        "( 1 + 2 ) * 4 + 3,     1 2 + 4 * 3 +",
        "5 * ( - 3 + 8 ),       5 3 - 8 + *",

        "~ ++ 3,                3 ++ ~",
        "~ 3 + 5,               3 ~ 5 +",
        "3 + ~ 5,               3 5 ~ +",

        "3 + 4 * 2 / ( 1 - 5 ) typeof 2,        3 4 2 * 1 5 - / + 2 typeof",
        "( 4 + 5 ) * 3 - ( 7 / 2 + 15 ),        4 5 + 3 * 7 2 / 15 + -",
        "( 8 + 2 * 5 ) / ( 1 + 3 * 2 - 4 ),     8 2 5 * + 1 3 2 * + 4 - /",
        "( 6 + 10 - 4 ) / ( 1 + 1 * 2 ) + 1,    6 10 + 4 - 1 1 2 * + / 1 +",

        "( ( ( ( 1 + 2 ) - ( 3 * 4 ) ) / ( 5 % 6 ) ^ 7 ) | 2 ) ^ ( 9 >> 2 | 4 << 2 | ~ 1 ), " +
            "1 2 + 3 4 * - 5 6 % / 7 ^ 2 | 9 2 >> 4 2 << | 1 ~ | ^"
    })
    @Order(1)
    void Should_build_a_valid_postfix_notation_expression_with_constants_only(final String expressionTokens,
                                                                              final String expectedResult) {
        final ComplexExpression actual = assertDoesNotThrow(() -> build(expressionTokens));
        final String actualResult = actual.getLexemes().stream().map(Object::toString).collect(joining(" "));
        assertEquals(expectedResult, actualResult);
    }

    @ParameterizedTest
    @ValueSource(strings = {
        ")",
        "2 )",
        "3 + 5 )",
        "5 + 8 - 6 * 7 )",
        "5 + ( 8 + 4 ) + 6 )",
        "( 5 + 8 ) + 4 + 6 )",
        "( ( 5 + 8 ) * 6 ) + 4 + 6 )",
        "( ( ( 5 + 8 ) * 6 ) - 6 * 7 ) + 4 + 6 )",
        ") + 7",
        "2 ) + 7",
        "3 + 5 ) + 7",
        "5 + 8 - 6 * 7 ) + 7",
        "5 + ( 8 + 4 ) + 6 ) + 7",
        "( 5 + 8 ) + 4 + 6 ) + 7",
        "( ( 5 + 8 ) * 6 ) + 4 + 6 ) + 7",
        "( ( ( 5 + 8 ) * 6 ) - 6 * 7 ) + 4 + 6 ) + 7"
    })
    @Order(4)
    void Should_throw_error_if_opening_parenthesis_is_missing(final String expressionTokens) {
        final JavammLineSyntaxError error = assertThrows(JavammLineSyntaxError.class, () -> build(expressionTokens));
        assertEquals("Syntax error in 'module1' [Line: 5]: Missing (", error.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "(",
        "( 2",
        "( 3 + 5",
        "( 5 + 8 - 6 * 7",
        "( 5 + ( 8 + 4 ) + 6",
        "( ( 5 + 8 ) + 4 + 6",
        "( ( ( 5 + 8 ) * 6 ) + 4 + 6",
        "( ( ( ( 5 + 8 ) * 6 ) - 6 * 7 ) + 4 + 6",
        "3 * (",
        "3 * ( 2",
        "3 * ( 3 + 5",
        "3 * ( 5 + 8 - 6 * 7",
        "3 * ( 5 + ( 8 + 4 ) + 6",
        "3 * ( ( 5 + 8 ) + 4 + 6",
        "3 * ( ( ( 5 + 8 ) * 6 ) + 4 + 6",
        "3 * ( ( ( ( 5 + 8 ) * 6 ) - 6 * 7 ) + 4 + 6"
    })
    @Order(5)
    void Should_throw_error_if_closing_parenthesis_is_missing(final String expressionTokens) {
        final JavammLineSyntaxError error = assertThrows(JavammLineSyntaxError.class, () -> build(expressionTokens));
        assertEquals("Syntax error in 'module1' [Line: 5]: Missing )", error.getMessage());
    }
}

