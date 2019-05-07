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

package academy.devonline.javamm.vm.integration.operation;

import academy.devonline.javamm.vm.integration.AbstractIntegrationTest;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.util.List;
import java.util.stream.Stream;

import static java.util.List.of;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class SimpleBlockOperationInterpreter_IntegrationTest extends AbstractIntegrationTest {

    @ParameterizedTest
    @ArgumentsSource(BlockScopeProvider.class)
    void Should_throw_runtime_error(final List<String> lines) {
        assertDoesNotThrow(() -> {
            runBlock(lines);
            assertEquals(List.of(), getOutput());
        });
    }

    /**
     * @author devonline
     * @link http://devonline.academy/javamm
     */
    static final class BlockScopeProvider implements ArgumentsProvider {

        @Override
        public Stream<Arguments> provideArguments(final ExtensionContext context) {
            return Stream.of(
                arguments(of(
                    "{",
                    "}"
                ))
            );
        }
    }
}
