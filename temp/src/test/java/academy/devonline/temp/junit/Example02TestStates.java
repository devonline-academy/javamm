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

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 * @link https://junit.org/junit5/docs/current/user-guide
 */
class Example02TestStates {

    private final BinaryCalculator binaryCalculator = new DivBinaryCalculator();

    @Test
    void state_1_success() {
        System.out.println("success: " + binaryCalculator.toString());
        assertEquals(3, binaryCalculator.calculate(12, 4));
    }

    @Test
    void state_2_fail() {
        System.out.println("fail: " + binaryCalculator.toString());
        assertEquals(0, binaryCalculator.calculate(12, 4));
    }

    @Test
    @Disabled("for demonstration purposes")
    void state_3_skipped() {
        System.out.println("skipped: " + binaryCalculator.toString());
    }

    @Test
    void state_4_error() {
        System.out.println("error: " + binaryCalculator.toString());
        assertEquals(3, binaryCalculator.calculate(12, 0));
    }
}
