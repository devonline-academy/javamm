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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
final class FileSourceCode implements SourceCode {

    private final Path path;

    private final List<String> lines;

    FileSourceCode(final String fileName) throws IOException {
        path = Paths.get(fileName);
        lines = Collections.unmodifiableList(Files.readAllLines(path));
    }

    @Override
    public String getModuleName() {
        return path.getFileName().toString();
    }

    @Override
    public List<String> getLines() {
        return lines;
    }

    @Override
    public String toString() {
        return path.toString();
    }
}
