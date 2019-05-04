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

import academy.devonline.javamm.code.fragment.SourceCode;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static java.lang.String.format;
import static java.lang.System.lineSeparator;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class FileSourceCode_UnitTest {

    private static Path tempFile;

    private SourceCode sourceCode;

    @BeforeAll
    static void beforeAll() throws IOException {
        tempFile = Files.createTempFile("test", "javamm");
        Files.writeString(tempFile, format("1%s2%s3", lineSeparator(), lineSeparator()), StandardCharsets.UTF_8);
    }

    @AfterAll
    static void afterAll() throws IOException {
        if (tempFile != null) {
            Files.deleteIfExists(tempFile);
        }
    }

    @BeforeEach
    void beforeEach() throws IOException {
        sourceCode = new FileSourceCode(tempFile.toString());
    }

    @Test
    @Order(1)
    void getModuleName_should_return_the_filename() {
        assertEquals(tempFile.getFileName().toString(), sourceCode.getModuleName());
    }

    @Test
    @Order(2)
    void getLines_should_return_the_file_content() {
        assertEquals(List.of("1", "2", "3"), sourceCode.getLines());
    }

    @Test
    @Order(3)
    void toString_should_return_the_absolute_file_path() {
        assertEquals(tempFile.toString(), sourceCode.toString());
    }
}