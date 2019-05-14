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

import academy.devonline.javamm.code.fragment.SourceCode;
import academy.devonline.javamm.compiler.JavammSyntaxError;
import academy.devonline.javamm.ide.component.VirtualMachineRunner;
import academy.devonline.javamm.interpreter.JavammRuntimeError;
import academy.devonline.javamm.interpreter.TerminateInterpreterException;
import academy.devonline.javamm.vm.VirtualMachine;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import static java.lang.String.format;
import static java.util.Collections.unmodifiableList;
import static java.util.Objects.requireNonNull;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
public class VirtualMachineRunnerImpl implements VirtualMachineRunner {

    private final VirtualMachine virtualMachine;

    private final List<SourceCode> sourceCodes;

    private Thread runnerThread;

    VirtualMachineRunnerImpl(final VirtualMachine virtualMachine,
                             final List<SourceCode> sourceCodes) {
        this.virtualMachine = requireNonNull(virtualMachine);
        this.sourceCodes = unmodifiableList(sourceCodes);
    }

    @Override
    public void run(final VirtualMachineRunCompletedListener listener) {
        if (runnerThread != null) {
            throw new IllegalStateException("Runner thread already exists");
        }
        requireNonNull(listener);
        runnerThread = new Thread(() -> invokeVm(listener), "JavammVM-Run-Thread");
        runnerThread.start();
    }

    @Override
    public boolean isRunning() {
        return runnerThread != null && runnerThread.isAlive();
    }

    @Override
    public void terminate() {
        if (runnerThread == null) {
            throw new IllegalStateException("Runner thread not found");
        }
        runnerThread.interrupt();
    }

    private void invokeVm(final VirtualMachineRunCompletedListener listener) {
        CompleteStatus status = CompleteStatus.SUCCESSFUL;
        try {
            virtualMachine.run(sourceCodes.toArray(new SourceCode[0]));
        } catch (final JavammSyntaxError e) {
            status = CompleteStatus.SYNTAX_ERROR;
            System.err.println(e.getMessage());
        } catch (final JavammRuntimeError e) {
            status = CompleteStatus.RUNTIME_ERROR;
            System.err.println(e.getMessage());
        } catch (final TerminateInterpreterException e) {
            status = CompleteStatus.TERMINATED;
        } catch (final RuntimeException e) {
            status = CompleteStatus.INTERNAL_ERROR;
            System.err.println(format("Internal error: %s", stackTraceToString(e)));
        } finally {
            listener.onRunCompleted(status);
        }
    }

    private String stackTraceToString(final Throwable th) {
        final StringWriter sw = new StringWriter();
        th.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }
}
