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

package academy.devonline.temp.junit;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 * @link https://dzone.com/articles/7-popular-unit-test-naming
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class Example07_NamingConventions {

    @Test
    void Should_ExpectedBehavior_When_StateUnderTest() {
        class Example {

            void Should_assign_the_null_value_expression_to_the_variable() {
            }

            void Should_throw_error_if_variable_name_is_missing() {
            }

            void Should_throw_error_if_variable_expression_is_missing() {
            }

            void Should_throw_error_if_lexemeBuilder_returns_one_lexeme_and_it_is_not_an_expression() {
            }

            void Should_not_throw_error_if_string_constant_does_not_end_with_closing_quote() {
            }
        }
    }


    @Test
    void MethodName_Should_ExpectedBehavior_When_StateUnderTest() {
        class Example {

            void Constructor_should_throw_ConfigException_if_a_duplicate_of_operation_interpreter_is_found() {
            }

            void getModuleName_should_return_the_filename() {
            }

            void toString_should_return_the_absolute_file_path() {
            }
        }
    }
}
