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

package academy.devonline.javamm.code.syntax;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static academy.devonline.javamm.code.util.ExceptionUtils.wrapCheckedException;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
public final class Keywords {

    public static final String IF = "if";

    public static final String ELSE = "else";

    public static final String SWITCH = "switch";

    public static final String CASE = "case";

    public static final String DEFAULT = "default";

    public static final String WHILE = "while";

    public static final String VAR = "var";

    public static final String FUNCTION = "function";

    public static final String RETURN = "return";

    public static final String BREAK = "break";

    public static final String CONTINUE = "continue";

    public static final String NULL = "null";

    public static final String TRUE = "true";

    public static final String FALSE = "false";

    public static final String DO = "do";

    public static final String FOR = "for";

    public static final String FINAL = "final";

    public static final String TYPEOF = "typeof";

    public static final String ARRAY = "array";

    public static final String STRING = "string";

    public static final String INTEGER = "integer";

    public static final String DOUBLE = "double";

    public static final String BOOLEAN = "boolean";

    public static final String VOID = "void";

    public static final Collection<String> KEYWORDS = keywords();

    private Keywords() {
    }

    private static Collection<String> keywords() {
        final Set<String> set = new LinkedHashSet<>();
        for (final Field field : Keywords.class.getDeclaredFields()) {
            if (field.getType() == String.class) {
                set.add(String.valueOf(wrapCheckedException(() -> field.get(Keywords.class))));
            }
        }
        return List.copyOf(set);
    }
}
