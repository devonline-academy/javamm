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

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 * @link https://junit.org/junit5/docs/current/user-guide
 */
@Disabled
class Example01JUnitBaseStructureTest {

    private final BinaryCalculator binaryCalculator = new DivBinaryCalculator();

    @BeforeAll
    static void beforeAll() {
        System.out.println("BEFORE ALL TESTS");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("AFTER ALL TESTS");
    }

    @BeforeEach
    void beforeEach() {
        System.out.println("Before: " + binaryCalculator.toString());
    }

    @Test
    void test1() {
        System.out.println("test1: " + binaryCalculator.toString());
    }

    @Test
    void test2() {
        System.out.println("test2: " + binaryCalculator.toString());
    }

    @AfterEach
    void afterEach() {
        System.out.println("After: " + binaryCalculator.toString());
    }
}
