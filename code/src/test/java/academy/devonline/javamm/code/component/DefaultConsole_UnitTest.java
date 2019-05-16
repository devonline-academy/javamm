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

package academy.devonline.javamm.code.component;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.PrintStream;

import static org.mockito.Mockito.verify;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DefaultConsole_UnitTest {

    private static final PrintStream OUT = System.out;

    private static final PrintStream ERR = System.err;

    private final Console console = Console.DEFAULT;

    @Mock
    private PrintStream out;

    @Mock
    private PrintStream err;

    @BeforeEach
    void before() {
        System.setOut(out);
        System.setErr(err);
    }

    @Test
    @Order(1)
    void out_should_delegate_the_call_to_System_out() {
        console.outPrintln("out");
        verify(out).println((Object) "out");
    }

    @Test
    @Order(2)
    void err_should_delegate_the_call_to_System_err() {
        console.errPrintln("error");
        verify(err).println("error");
    }

    @AfterEach
    void after() {
        System.setOut(OUT);
        System.setErr(ERR);
    }
}