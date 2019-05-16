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

package academy.devonline.javamm.ide.component.impl;

import academy.devonline.javamm.code.component.Console;
import academy.devonline.javamm.compiler.JavammSyntaxError;
import academy.devonline.javamm.ide.component.VirtualMachineRunner;
import academy.devonline.javamm.interpreter.JavammRuntimeError;
import academy.devonline.javamm.interpreter.TerminateInterpreterException;
import academy.devonline.javamm.vm.VirtualMachine;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.List;

import static academy.devonline.javamm.ThreadUtils.waitForThreadToDie;
import static academy.devonline.javamm.ide.component.VirtualMachineRunner.CompleteStatus.INTERNAL_ERROR;
import static academy.devonline.javamm.ide.component.VirtualMachineRunner.CompleteStatus.RUNTIME_ERROR;
import static academy.devonline.javamm.ide.component.VirtualMachineRunner.CompleteStatus.SUCCESSFUL;
import static academy.devonline.javamm.ide.component.VirtualMachineRunner.CompleteStatus.SYNTAX_ERROR;
import static academy.devonline.javamm.ide.component.VirtualMachineRunner.CompleteStatus.TERMINATED;
import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class VirtualMachineRunnerImpl_UnitTest {

    @Mock
    private Console console;

    @Mock
    private VirtualMachine virtualMachine;

    @Mock
    private VirtualMachineRunner.VirtualMachineRunCompletedListener listener;

    private VirtualMachineRunnerImpl virtualMachineRunner;

    @BeforeEach
    void before() {
        virtualMachineRunner = new VirtualMachineRunnerImpl(console, virtualMachine, List.of());
    }

    private VirtualMachineRunner.CompleteStatus runVmAndReturnCompleteStatus()
        throws IllegalAccessException, InterruptedException {
        final List<VirtualMachineRunner.CompleteStatus> statuses = new ArrayList<>(1);
        virtualMachineRunner.run(statuses::add);
        waitForThreadToDie(getRunnerThread());

        assertEquals(1, statuses.size(), "Expected only one status. Actual: " + statuses);

        return statuses.get(0);
    }

    private Thread getRunnerThread() throws IllegalAccessException {
        return (Thread) FieldUtils.readDeclaredField(virtualMachineRunner, "runnerThread", true);
    }

    /**
     * Controls test
     *
     * @author devonline
     * @link http://devonline.academy/javamm
     */
    @Nested
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    class RunnerControls_UnitTest {

        @Test
        @Order(11)
        void run_should_throw_IllegalStateException_if_it_already_invoked()
            throws IllegalAccessException, InterruptedException {
            runVmAndReturnCompleteStatus();

            final IllegalStateException exception = assertThrows(IllegalStateException.class, () ->
                virtualMachineRunner.run(listener));
            assertEquals("Runner thread already exists", exception.getMessage());
        }

        @Test
        @Order(12)
        void terminate_should_interrupt_the_working_thread()
            throws IllegalAccessException, InterruptedException {
            doAnswer((Answer<Void>) invocation -> {
                synchronized (this) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        //do nothing
                    }
                }
                return null;
            }).when(virtualMachine).run(any());

            virtualMachineRunner.run(listener);
            virtualMachineRunner.terminate();

            waitForThreadToDie(getRunnerThread());
            //check do nothing if already interrupted
            assertDoesNotThrow(() -> virtualMachineRunner.terminate());
        }

        @Test
        @Order(13)
        void terminate_should_throw_IllegalStateException_if_runner_is_not_started() {
            final IllegalStateException exception = assertThrows(IllegalStateException.class, () ->
                virtualMachineRunner.terminate());
            assertEquals("Runner thread not found", exception.getMessage());
        }

        @Test
        @Order(14)
        void isRunning_should_return_false_if_run_not_invoked_yet() {
            assertFalse(virtualMachineRunner.isRunning());
        }

        @Test
        @Order(15)
        void isRunning_should_return_false_if_working_thread_is_not_alive()
            throws IllegalAccessException, InterruptedException {
            runVmAndReturnCompleteStatus();

            assertFalse(virtualMachineRunner.isRunning());
        }

        @Test
        @Order(16)
        void isRunning_should_return_true_if_working_thread_is_alive() {
            virtualMachineRunner.run(listener);
            assertTrue(virtualMachineRunner.isRunning());
        }
    }

    /**
     * Statuses test
     *
     * @author devonline
     * @link http://devonline.academy/javamm
     */
    @Nested
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    class VirtualMachineStatuses_UnitTest {

        @Test
        @Order(1)
        void Should_return_SUCCESSFUL_status_and_display_no_messages()
            throws IllegalAccessException, InterruptedException {
            final VirtualMachineRunner.CompleteStatus status = runVmAndReturnCompleteStatus();

            verify(console, never()).outPrintln(any());
            verify(console, never()).errPrintln(anyString());
            assertEquals(SUCCESSFUL, status);
        }

        @Test
        @Order(2)
        void Should_return_SYNTAX_ERROR_status_and_display_syntax_error_message()
            throws IllegalAccessException, InterruptedException {
            doThrow(new JavammSyntaxError("Syntax error") {
            }).when(virtualMachine).run(any());

            final VirtualMachineRunner.CompleteStatus status = runVmAndReturnCompleteStatus();

            verify(console, never()).outPrintln(any());
            verify(console).errPrintln("Syntax error");
            assertEquals(SYNTAX_ERROR, status);
        }

        @Test
        @Order(3)
        void Should_return_RUNTIME_ERROR_status_and_display_runtime_error_message()
            throws IllegalAccessException, InterruptedException {
            doThrow(new JavammRuntimeError("Test") {
            }).when(virtualMachine).run(any());

            final VirtualMachineRunner.CompleteStatus status = runVmAndReturnCompleteStatus();

            verify(console, never()).outPrintln(any());
            verify(console).errPrintln("Runtime error: Test");
            assertEquals(RUNTIME_ERROR, status);
        }

        @Test
        @Order(4)
        void Should_return_TERMINATED_and_display_no_messages()
            throws IllegalAccessException, InterruptedException {
            doThrow(new TerminateInterpreterException()).when(virtualMachine).run(any());

            final VirtualMachineRunner.CompleteStatus status = runVmAndReturnCompleteStatus();

            verify(console, never()).outPrintln(any());
            verify(console, never()).errPrintln(anyString());
            assertEquals(TERMINATED, status);
        }

        @Test
        @Order(5)
        void Should_return_INTERNAL_ERROR_status_and_display_error_message_with_stacktrace()
            throws IllegalAccessException, InterruptedException {
            doThrow(new InternalErrorException()).when(virtualMachine).run(any());

            final VirtualMachineRunner.CompleteStatus status = runVmAndReturnCompleteStatus();

            verify(console, never()).outPrintln(any());
            verify(console).errPrintln(format("Internal error: %s: Internal%s",
                InternalErrorException.class.getName(), System.lineSeparator()));
            assertEquals(INTERNAL_ERROR, status);
        }

        private final class InternalErrorException extends RuntimeException {

            private InternalErrorException() {
                super("Internal", null, false, false);
            }
        }
    }
}