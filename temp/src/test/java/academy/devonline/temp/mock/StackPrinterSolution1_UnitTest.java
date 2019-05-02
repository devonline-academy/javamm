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

package academy.devonline.temp.mock;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
@Disabled
class StackPrinterSolution1_UnitTest {

    @Test
    void testPrintStack() {
        final Stack stack = new TestStack();
        StackPrinter.printStack(stack);
    }

    /**
     * @author devonline
     * @link http://devonline.academy/javamm
     */
    private static final class TestStack implements Stack {

        private int index;

        private int[] data = {1, 2, 3, 4, 5};

        @Override
        public boolean hasElements() {
            return index < data.length;
        }

        @Override
        public int pop() {
            return data[index++];
        }
    }
}