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

package academy.devonline.javamm.cmd;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
@ExtendWith(MockitoExtension.class)
class JmmVmLauncher_UnitTest {

    private static final PrintStream REAL_ERR = System.err;

    @Mock
    private PrintStream err;

    @BeforeEach
    void beforeEach() {
        System.setErr(err);
    }

    @Test
    void Should_run_successful(@TempDir Path tempDir) throws IOException {
        final Path testSourceCodePath = tempDir.resolve("test.javamm");
        Files.write(testSourceCodePath, List.of("function main(){", "}"));

        JmmVmLauncher.main(testSourceCodePath.toString());

        verify(err, never()).println(anyString());
    }

    @Test
    void Should_throw_runtime_error_if_main_function_not_found() {
        JmmVmLauncher.main();

        verify(err).println(
            "Runtime error: Main function not found, please define the main function as: 'function main()'");
    }

    @AfterEach
    void afterEach() {
        System.setErr(REAL_ERR);
    }
}