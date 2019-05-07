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

package academy.devonline.javamm.vm.integration.expression;

import academy.devonline.javamm.vm.integration.AbstractIntegrationTest;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.util.List;
import java.util.stream.Stream;

import static java.lang.String.format;
import static java.util.function.Function.identity;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ValidComplexExpression_IntegrationTest extends AbstractIntegrationTest {

    @ParameterizedTest
    @Order(1)
    @ArgumentsSource(ValidComplexExpressionProvider.class)
    void Should_evaluate_the_complex_expressions_correctly(final String expression,
                                                           final Object expectedResult) {

        assertDoesNotThrow(() -> {
            runBlock(format("println ( %s )", expression));
            assertEquals(List.of(expectedResult), getOutput());
        });
    }

    /**
     * @author devonline
     * @link http://devonline.academy/javamm
     */
    static final class ValidComplexExpressionProvider implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(final ExtensionContext context) {
            return Stream.of(
                arithmeticExpressionsProvider(),
                logicAndPredicateExpressionsProvider(),
                bitExpressionsProvider(),
                complexExpressionsProvider()
            ).flatMap(identity());
        }

        private Stream<Arguments> arithmeticExpressionsProvider() {
            return Stream.of(
                arguments(
                    "1 + 2 * ( 3 - 5 )",
                    1 + 2 * (3 - 5)
                ),
                arguments(
                    "5 - 2 * 2 - 1",
                    5 - 2 * 2 - 1
                ),
                arguments(
                    "1 + 2 - 3 * 4 / 5 % 6",
                    1 + 2 - 3 * 4 / 5 % 6
                ),
                arguments(
                    "( ( 1 + 2 ) - ( 3 * 4 ) ) / ( 5 % 6 )",
                    ((1 + 2) - (3 * 4)) / (5 % 6)
                ),
                arguments(
                    "+ 4 - - 5 * + 6 / - 7",
                    +4 - -5 * +6 / -7
                )
            );
        }

        @SuppressWarnings( {"PointlessBooleanExpression", "ConstantConditions", "ExcessiveRangeCheck"})
        private Stream<Arguments> logicAndPredicateExpressionsProvider() {
            return Stream.of(
                arguments(
                    "true && false || ! true && ! false",
                    true && false || !true && !false
                ),
                arguments(
                    "! ( ( true || false ) && ( ! true || ! false ) )",
                    !((true || false) && (!true || !false))
                ),
                arguments(
                    "1 > 2 && 1 < 2",
                    1 > 2 && 1 < 2
                ),
                arguments(
                    "1 >= 2 && 1 <= 2",
                    1 >= 2 && 1 <= 2
                ),
                arguments(
                    "1 != 2 && 1 == 2",
                    1 != 2 && 1 == 2
                ),
                arguments(
                    "1 > 2 || 1 < 2",
                    1 > 2 || 1 < 2
                ),
                arguments(
                    "1 >= 2 || 1 <= 2",
                    1 >= 2 || 1 <= 2
                ),
                arguments(
                    "1 != 2 || 1 == 2",
                    1 != 2 || 1 == 2
                )
            );
        }

        private Stream<Arguments> bitExpressionsProvider() {
            return Stream.of(
                arguments(
                    "56 & 45 | 12 | ~ 2",
                    56 & 45 | 12 | ~2
                ),
                arguments(
                    "56 & 45 | 12",
                    56 & 45 | 12
                ),
                arguments(
                    "56 & 45 | 12 & ~ 1",
                    56 & 45 | 12 & ~1
                ),
                arguments(
                    "56 >> 2 ^ 12 << 2 ^ ~ 1",
                    56 >> 2 ^ 12 << 2 ^ ~1
                ),
                arguments(
                    "56 >> 2 | 12 << 2 | ~ 1",
                    56 >> 2 | 12 << 2 | ~1
                )
            );
        }

        @SuppressWarnings("PointlessArithmeticExpression")
        private Stream<Arguments> complexExpressionsProvider() {
            return Stream.of(
                arguments(
                    "~ - 1 ^ ~ + - 2 | + - + - 3 & ~ + - + - + - + - 8",
                    ~-1 ^ ~+-2 | +-+-3 & ~+-+-+-+-8
                ),
                arguments(
                    "( ( ( ( 1 + 2 ) - ( 3 * 4 ) ) / ( 5 % 6 ) ^ 23 ) | 234567 ) ^ ( 56 >> 2 | 12 << 2 | ~ 1 )",
                    ((((1 + 2) - (3 * 4)) / (5 % 6) ^ 23) | 234567) ^ (56 >> 2 | 12 << 2 | ~1)
                ),
                arguments(
                    "( ( 5 & 4 | 8 & ~ + 1 ) >> 4 ) * ( ( ( 1 + 2 ) - ( 3 * 4 ) ) << 3 ) - ( ( ( 5 % 6 ) ^ 1 ) )",
                    ((5 & 4 | 8 & ~+1) >> 4) * (((1 + 2) - (3 * 4)) << 3) - (((5 % 6) ^ 1))
                )
            );
        }
    }
}