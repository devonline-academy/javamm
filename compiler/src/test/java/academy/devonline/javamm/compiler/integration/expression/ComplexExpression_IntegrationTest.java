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

import academy.devonline.javamm.code.fragment.SourceLine;
import academy.devonline.javamm.compiler.CompilerConfigurator;
import academy.devonline.javamm.compiler.JavammSyntaxError;
import academy.devonline.javamm.compiler.component.ExpressionResolver;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

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
class ComplexExpression_IntegrationTest {

    private final SourceLine sourceLine = new SourceLine("module1", 5, List.of());

    private final ExpressionResolver expressionResolver = new CompilerConfigurator().getExpressionResolver();

    @ParameterizedTest
    @Order(1)
    @ValueSource(strings = {
        // Unary ~
        "5",
        "~ 5",
        "~ 5 + 5",
        "~ 5 + ~ 5",
        "~ 5 * ~ ~ ~ ~ 5",
        "~ 5 * ~ ( ~ ( ~ 5 ) )",

        // Unary -
        "- 5",
        "- 5 + 5",
        "- 5 + - 5",
        "- 5 * - + - + 5",
        "~ 5 * - ( + ( - 5 ) )",

        // Increment
        "a ++",
        "++ a",
        "a ++ + 5",
        "5 + a ++",
        "5 + a ++ + 5",
        "++ a + 5",
        "5 + ++ a",
        "5 + ++ a + 5",
        "+ 5 + ++ a + + + + + 5",
        "+ 5 + + a ++ + + + + + 5",
        "+ 5 + ++ a + ++ a + ++ a + 5",
        "+ 5 + a ++ + a ++ + a ++ + 5",

        // Binary assignment
        "a += 5",
        "a += 4 * 5",
        "3 * ( a += 4 + 5 )"
    })
    void Should_validate_successful(final String expression) {
        assertDoesNotThrow(() -> expressionResolver.resolve(List.of(expression.split(" ")), sourceLine));
    }

    @ParameterizedTest
    @Order(2)
    @CsvSource( {
        // Constants only
        "* 5 + 5,               Expression can't start with binary operator: '*'",
        "5 + 5 *,               Expression can't end with binary operator: '*'",
        "5 + 5 ~,               Expression can't end with unary operator: '~'",
        "5 * / 5,               An expression is expected between binary operators: '*' and '/'",
        "4 5 + 6,               A binary operator is expected between expressions: '4' and '5'",
        "3 + 4 ( 5 - 6 ),       A binary operator is expected between expressions: '4' and '5'",
        "( 3 + 4 ) 5 - 6,       A binary operator is expected between expressions: '4' and '5'",
        "( 5 + 5 - ~ ) - 5,     An expression is expected for unary operator: '~'",
        "( * 5 + 5 ) - 5,       An expression is expected for binary operator: '*'",
        "( 5 + 5 * ) - 5,       An expression is expected for binary operator: '*'",
        "~ * 5,                 An expression is expected for binary operator: '*'",
        "4 + 5 ( ) 5 - 6,       Parentheses are incorrectly placed",
        "4 + 5 ) ( 5 - 6,       Parentheses are incorrectly placed",
        "4 + 5 ( ( ) ) 5 - 6,   Parentheses are incorrectly placed",
        "4 + 5 ) ) ( ( 5 - 6,   Parentheses are incorrectly placed",
        "5 += 5,                A variable expression is expected for binary operator: '+='",
        "5 + 5 += 5,            A variable expression is expected for binary operator: '+='",
        "5 + 5 += 5 * 5,        A variable expression is expected for binary operator: '+='",
        "5 + ( 5 += 5 ) * 5,    A variable expression is expected for binary operator: '+='",
        "( 5 + 5 ) += 5,        A variable expression is expected for binary operator: '+='",

        // Increment / Decrement
        "5 ++,                  A variable expression is expected for unary operator: '++'",
        "++ 5,                  A variable expression is expected for unary operator: '++'",
        "++ 5 + 5 + + + + + 5,  A variable expression is expected for unary operator: '++'",
        "5 ++ + 5 + + + + + 5,  A variable expression is expected for unary operator: '++'",
        "+ 5 + ++ 5 + + + + 5,  A variable expression is expected for unary operator: '++'",
        "+ 5 + 5 ++ + + + + 5,  A variable expression is expected for unary operator: '++'",
        "+ 5 + + + + 5 + ++ 5,  A variable expression is expected for unary operator: '++'",
        "+ 5 + + + + 5 + 5 ++,  A variable expression is expected for unary operator: '++'",

        // Binary assignment
        "3 * a += 4 + 5,        A variable expression is expected for binary operator: '+='",
        "- a += 4 + 5,          A variable expression is expected for binary operator: '+='",

        // Increment without variable
        "++ +,                  An expression is expected for unary operator: '++'",
        "++ (,                  An expression is expected for unary operator: '++'",
        "+ ++,                  An expression is expected for unary operator: '++'",
        ") ++,                  An expression is expected for unary operator: '++'",

        // Increment and something is missing
        "++ a ++,               A variable expression is expected for unary operator: '++'",
        "++ a ++ a ++,          A variable expression is expected for unary operator: '++'",
        "a ++ ++ a,             A binary operator is expected between expressions: 'a++' and '++a'",
        "a ++ a ++,             A binary operator is expected between expressions: 'a++' and 'a++'",
        "++ a ++ a,             A binary operator is expected between expressions: '++a' and '++a'",
        "++ ++ a,               An expression is expected for unary operator: '++'",
        "a ++ ++,               A variable expression is expected for unary operator: '++'",
        "5 ++ a,                A binary operator is expected between expressions: '5' and '++a'",
        "a ++ 5,                A binary operator is expected between expressions: 'a++' and '5'",
        "5 ++ 5,                A variable expression is expected for unary operator: '++'",
    })
    void Should_throw_error(final String expression,
                            final String expectedMessage) {
        final JavammSyntaxError error = assertThrows(JavammSyntaxError.class, () ->
            expressionResolver.resolve(List.of(expression.split(" ")), sourceLine));
        assertEquals("Syntax error in 'module1' [Line: 5]: " + expectedMessage, error.getMessage());
    }
}
