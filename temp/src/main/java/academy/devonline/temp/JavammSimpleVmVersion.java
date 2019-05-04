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

package academy.devonline.temp;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public final class JavammSimpleVmVersion {

    private JavammSimpleVmVersion() {
    }

    public static void main(final String[] args) throws IOException {
        final List<String> lines = Files.readAllLines(Paths.get("cmd/src/main/resources/test.javamm"));
        for (final String line : lines) {
            if (!line.isBlank()) {
                final String cmd = line.trim();
                if (cmd.startsWith("println")) {
                    if (!cmd.startsWith("println (")) {
                        throw new SyntaxErrorException("Missing (: " + cmd);
                    }
                    if (!cmd.endsWith(")")) {
                        throw new SyntaxErrorException("Missing ): " + cmd);
                    }
                    System.out.println(cmd.substring(10, cmd.length() - 1));
                } else {
                    throw new SyntaxErrorException("Unsupported operation: " + cmd);
                }
            }
        }
    }

    private static final class SyntaxErrorException extends IllegalArgumentException {
        private SyntaxErrorException(final String s) {
            super(s);
        }
    }
}
