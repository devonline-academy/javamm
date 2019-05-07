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

package academy.devonline.javamm.vm.integration;

import academy.devonline.javamm.code.fragment.SourceCode;
import academy.devonline.javamm.vm.VirtualMachine;
import academy.devonline.javamm.vm.VirtualMachineBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.mock;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
public abstract class AbstractIntegrationTest {

    private final PrintStream systemOut = System.out;

    private final TestPrintStream testPrintStream = new TestPrintStream();

    private final VirtualMachine virtualMachine = new VirtualMachineBuilder().build();

    @BeforeEach
    final void beforeEach() {
        System.setOut(testPrintStream);
    }

    protected final void runBlock(final List<String> operations) {
        final List<String> validOperations = new ArrayList<>();
        validOperations.add(""); //TODO Add function declaration here
        validOperations.addAll(operations);

        virtualMachine.run(new TestSourceCode(validOperations));
    }

    protected final void runBlock(final String operation) {
        runBlock(List.of(operation));
    }

    protected final List<Object> getOutput() {
        return Collections.unmodifiableList(testPrintStream.out);
    }

    @AfterEach
    final void afterEach() {
        System.setOut(systemOut);
    }

    /**
     * @author devonline
     * @link http://devonline.academy/javamm
     */
    private static final class TestPrintStream extends PrintStream {

        private final List<Object> out = new ArrayList<>();

        private TestPrintStream() {
            super(mock(OutputStream.class));
        }

        @Override
        public void println(final Object x) {
            out.add(x);
        }
    }

    /**
     * @author devonline
     * @link http://devonline.academy/javamm
     */
    private static final class TestSourceCode implements SourceCode {

        private final List<String> lines;

        private TestSourceCode(final List<String> lines) {
            this.lines = Collections.unmodifiableList(lines);
        }

        @Override
        public String getModuleName() {
            return "test";
        }

        @Override
        public List<String> getLines() {
            return lines;
        }
    }
}

