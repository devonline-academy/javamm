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

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
@SuppressWarnings("CheckStyle")
public final class DivBinaryCalculatorMainVersion2 {

    private DivBinaryCalculatorMainVersion2() {
    }

    public static void main(final String[] args) {
        final BinaryCalculator calculator = new DivBinaryCalculator();

        final int result = calculator.calculate(12, 4);
        if (3 == result) {
            System.out.println("Calculate success: 12 / 4 = 3");
        } else {
            System.out.println("Calculate failed: expected=3, actual=" + result);
        }

        try {
            calculator.calculate(12, 0);
            System.out.println("Calculate failed: should throw ArithmeticException");
        } catch (final ArithmeticException e) {
            if ("/ by zero".equals(e.getMessage())) {
                System.out.println("Calculate success: 12 / 0 -> exception");
            } else {
                System.out.println("Calculate failed: Invalid error message: expected=/ by zero, actual=" + e.getMessage());
            }
        }
    }
}
