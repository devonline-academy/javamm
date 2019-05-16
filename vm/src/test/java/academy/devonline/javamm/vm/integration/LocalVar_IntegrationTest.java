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

package academy.devonline.javamm.vm.integration;

import academy.devonline.javamm.interpreter.JavammRuntimeError;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class LocalVar_IntegrationTest extends AbstractIntegrationTest  {

    @Test
    void Should_throw_runtime_error_if_program_tries_to_change_a_final_variable(){
        final JavammRuntimeError error = assertThrows(JavammRuntimeError.class, () -> runBlock(List.of(
            "final a = 1",
            "a = 2"
        )));
        assertEquals("Runtime error: Final variable 'a' can't be changed", error.getSimpleMessage());
    }

    @Test
    void Should_throw_runtime_error_if_program_tries_to_change_not_defined_variable(){
        final JavammRuntimeError error = assertThrows(JavammRuntimeError.class, () -> runBlock(List.of(
            "a = 2"
        )));
        assertEquals("Runtime error: Variable 'a' is not defined", error.getSimpleMessage());
    }

    @Test
    void Should_update_a_variable_during_evaluation_of_complex_expression(){
        runBlock(List.of(
            "var a = 1",
            "println(a += 2 + a - 2 * 3)",
            "println(a)"
        ));

        assertEquals(List.of(-2, -2), getOutput());
    }
}
