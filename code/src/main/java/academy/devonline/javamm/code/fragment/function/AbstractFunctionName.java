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

package academy.devonline.javamm.code.fragment.function;

import academy.devonline.javamm.code.fragment.FunctionName;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static java.lang.String.format;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.joining;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
public abstract class AbstractFunctionName implements FunctionName {

    private final String name;

    private final String uniqueName;

    public AbstractFunctionName(final String name, final int argumentCount) {
        this.name = requireNonNull(name);
        this.uniqueName = FunctionUniqueNameGenerator.generate(name, argumentCount);
    }

    public final String getName() {
        return name;
    }

    @SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
    @Override
    public final boolean equals(final Object o) {
        final FunctionName other = (FunctionName) o;
        if (other instanceof AbstractFunctionName) {
            return uniqueName.equals(((AbstractFunctionName) o).uniqueName);
        } else {
            return name.equals(other.getName());
        }
    }

    @Override
    public final int hashCode() {
        return getName().hashCode();
    }

    @Override
    public final int compareTo(final FunctionName other) {
        if (other instanceof AbstractFunctionName) {
            return uniqueName.compareTo(((AbstractFunctionName) other).uniqueName);
        } else {
            return name.compareTo(other.getName());
        }
    }

    @Override
    public final String toString() {
        return uniqueName;
    }

    /**
     * @author devonline
     * @link http://devonline.academy/javamm
     */
    private static final class FunctionUniqueNameGenerator {

        private static final List<String> ALPHABET;

        static {
            final List<String> list = new ArrayList<>();
            for (char ch = 'a'; ch <= 'z'; ch++) {
                list.add(String.valueOf(ch));
            }
            ALPHABET = List.copyOf(list);
        }

        private static String generate(final String name,
                                       final int argumentCount) {
            final int[] index = new int[1];
            return format("%s(%s)", name, Stream
                .generate(() -> {
                    if (index[0] == ALPHABET.size()) {
                        index[0] = 0;
                    }
                    return ALPHABET.get(index[0]++);
                })
                .limit(argumentCount)
                .collect(joining(",")));
        }
    }
}
