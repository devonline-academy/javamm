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

package academy.devonline.temp.immutable.list;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
@SuppressWarnings("CheckStyle")
public final class Example06Set {

    private Example06Set() {
    }

    public static void main(final String[] args) {
        final List<String> list = Arrays.asList("hello", "world", "java", "the", "best");

        Set<String> set;
        set = new HashSet<>(list);

        System.out.println(Collections.unmodifiableSet(set));
        System.out.println(Set.copyOf(set));
        System.out.println("------------------------------------------------------------");

        set = new LinkedHashSet<>(list);

        System.out.println(Collections.unmodifiableSet(set));
        System.out.println(Set.copyOf(set));
        System.out.println("------------------------------------------------------------");
        set = new TreeSet<>(list);

        System.out.println(Collections.unmodifiableSet(set));
        System.out.println(Set.copyOf(set));
    }

}
