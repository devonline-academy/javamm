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

package academy.devonline.javamm.compiler.integration;

import academy.devonline.javamm.code.fragment.ByteCode;
import academy.devonline.javamm.code.fragment.Operation;
import academy.devonline.javamm.code.fragment.operation.Block;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.params.provider.Arguments;

import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
public abstract class AbstractOperationReaderSuccessIntegrationTest extends AbstractIntegrationTest {

    @TestFactory
    @Order(1)
    @SuppressWarnings("unchecked")
    Stream<DynamicTest> Should_compile_the_code_successful() {
        return validSourceLineProvider().map(args -> {
            final List<String> lines = (List<String>) args.get()[0];
            return dynamicTest(
                String.join(" ", lines),
                () -> {
                    final ByteCode byteCode = assertDoesNotThrow(() ->
                        wrapMainFunctionAndCompile(lines, true));
                    final Block block = byteCode.getFunction(byteCode.getMainFunctionName()).orElseThrow().getBody();
                    assertEquals(
                        List.of(getResultOperationClass()),
                        block.getOperations().stream().map(Operation::getClass).collect(toList()),
                        "Invalid operation model class"
                    );
                });
        });
    }

    protected abstract Class<? extends Operation> getResultOperationClass();

    protected abstract Stream<Arguments> validSourceLineProvider();
}
