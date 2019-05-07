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

package academy.devonline.javamm.compiler.integration.operation;

import academy.devonline.javamm.code.fragment.Operation;
import academy.devonline.javamm.compiler.integration.AbstractOperationReaderSuccessIntegrationTest;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

import static java.util.List.of;
import static org.junit.jupiter.params.provider.Arguments.arguments;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
class WhileOperationReader_Expected_success_IntegrationTest extends AbstractOperationReaderSuccessIntegrationTest {

    @Override
    protected Class<? extends Operation> getResultOperationClass() {
        return Operation.class;
    }

    @Override
    protected Stream<Arguments> validSourceLineProvider() {
        return Stream.of(
            arguments(of(
                "while ( i < 10 ) {",

                "}"
            )),
            arguments(of(
                "while ( ( a + 4 ) * ( b - 8 ) + 4 / ( ( 4 - c ) * 3 - d ) > 0 ) {",

                "}"
            )),
            arguments(of(
                "while ( i < 10 ) {",
                "   while ( i < 10 ) {",
                "       while ( i < 10 ) {",

                "       }",
                "   }",
                "}"
            ))
        );
    }
}
