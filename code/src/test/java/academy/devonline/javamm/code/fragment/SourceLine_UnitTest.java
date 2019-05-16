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

package academy.devonline.javamm.code.fragment;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SourceLine_UnitTest {

    @Test
    @Order(1)
    void Should_be_equal_if_all_values_are_equal() {
        final SourceLine sourceLine1 = new SourceLine("module1", 5, List.of());
        final SourceLine sourceLine2 = new SourceLine("module1", 5, List.of());

        assertEquals(sourceLine1.hashCode(), sourceLine2.hashCode());
        assertEquals(sourceLine1, sourceLine2);
        assertEquals(0, sourceLine1.compareTo(sourceLine2));
    }

    @Test
    @Order(2)
    void Should_be_equal_if_moduleName_and_number_are_equal_and_expected_to_ignore_of_comparison_for_the_token_list() {
        final SourceLine sourceLine1 = new SourceLine("module1", 5, List.of("var", "a"));
        final SourceLine sourceLine2 = new SourceLine("module1", 5, List.of("a", "++"));

        assertEquals(sourceLine1.hashCode(), sourceLine2.hashCode());
        assertEquals(sourceLine1, sourceLine2);
        assertEquals(0, sourceLine1.compareTo(sourceLine2));
    }

    @Test
    @Order(3)
    void Should_not_be_equal_if_numbers_are_different() {
        final SourceLine sourceLine1 = new SourceLine("module1", 4, List.of());
        final SourceLine sourceLine2 = new SourceLine("module1", 5, List.of());

        assertNotEquals(sourceLine1, sourceLine2);
        assertNotEquals(0, sourceLine1.compareTo(sourceLine2));
    }

    @Test
    @Order(4)
    void Should_not_be_equal_if_moduleNames_are_different() {
        final SourceLine sourceLine1 = new SourceLine("module1", 5, List.of());
        final SourceLine sourceLine2 = new SourceLine("module2", 5, List.of());

        assertNotEquals(sourceLine1, sourceLine2);
        assertNotEquals(0, sourceLine1.compareTo(sourceLine2));
    }
}