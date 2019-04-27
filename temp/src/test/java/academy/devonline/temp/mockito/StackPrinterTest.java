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

package academy.devonline.temp.mockito;

import academy.devonline.temp.mock.Stack;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.PrintStream;

import static academy.devonline.temp.mock.StackPrinter.printStack;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 * @link https://javadoc.io/page/org.mockito/mockito-core/latest/org/mockito/Mockito.html
 */
@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class StackPrinterTest {

    private static final PrintStream REAL_OUT = System.out;

    @Mock
    private Stack stack;

    @Mock
    private PrintStream out;

    @BeforeEach
    void beforeEach() {
        System.setOut(out);
    }

    @Test
    void Should_extract_elements_from_stack_and_print_to_console() {
        when(stack.hasElements())
            .thenReturn(true)
            .thenReturn(true)
            .thenReturn(true)
            .thenReturn(false);
        when(stack.pop())
            .thenReturn(1)
            .thenReturn(2)
            .thenReturn(3)
            .thenThrow(new IllegalStateException("Stack is empty"));
        assertDoesNotThrow(() -> printStack(stack));

        verify(out).println(1);
        verify(out).println(2);
        verify(out).println(3);
        verify(out, times(3)).println(anyInt());
    }

    @AfterEach
    void afterEach() {
        System.setOut(REAL_OUT);
    }
}