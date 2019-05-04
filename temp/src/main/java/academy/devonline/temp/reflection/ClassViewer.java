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

package academy.devonline.temp.reflection;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;

import static java.lang.String.format;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.joining;

/**
 * --add-opens java.base/java.lang=temp
 *
 * @author devonline
 * @link http://devonline.academy/javamm
 */
public final class ClassViewer {

    private final Object inspectObject;

    public ClassViewer(final Object inspectObject) {
        this.inspectObject = requireNonNull(inspectObject);
    }

    public static void main(final String[] args) throws IllegalAccessException {
        new ClassViewer("Hello").printAll();
        new ClassViewer(1).printAll();
        new ClassViewer(true).printAll();
        new ClassViewer(new Object()).printAll();
    }

    public void printAll() throws IllegalAccessException {
        final Class<?> inspectClass = inspectObject.getClass();
        System.out.println("############################ CLASS  ############################");
        System.out.println(inspectClass.getName());
        System.out.println("---------------------------- FIELDS ----------------------------");
        final Field[] fields = inspectClass.getDeclaredFields();
        for (final Field field : fields) {
            field.setAccessible(true);
            System.out.println(format("%s%s %s = %s",
                getVisibilityModifier(field.getModifiers()),
                getType(field.getType()),
                field.getName(),
                valueToString(field.get(inspectObject))));
        }
        System.out.println("------------------------- CONSTRUCTORS -------------------------");
        final Constructor<?>[] constructors = inspectClass.getDeclaredConstructors();
        for (final Constructor<?> constructor : constructors) {
            System.out.println(format("%s%s(%s) %s",
                getVisibilityModifier(constructor.getModifiers()),
                constructor.getName(),
                Arrays.stream(constructor.getParameterTypes()).map(this::getType).collect(joining(", ")),
                getThrows(constructor.getExceptionTypes())));
        }
        System.out.println("--------------------------- METHODS ----------------------------");
        final Method[] methods = inspectClass.getDeclaredMethods();
        for (final Method method : methods) {
            System.out.println(format("%s%s(%s) %s",
                getVisibilityModifier(method.getModifiers()),
                method.getName(),
                Arrays.stream(method.getParameterTypes()).map(this::getType).collect(joining(", ")),
                getThrows(method.getExceptionTypes())));
        }
    }

    private String getThrows(final Class<?>[] exceptionClasses) {
        final String mergeResult = Arrays.stream(exceptionClasses).map(this::getType).collect(joining(", "));
        return mergeResult.isEmpty() ? "" : "throws " + mergeResult;
    }

    private String getType(final Class<?> clazz) {
        if (clazz.isArray()) {
            return clazz.getComponentType().getName() + "[]";
        } else {
            return clazz.getName();
        }
    }

    private String valueToString(final Object value) {
        if (value.getClass().isArray()) {
            final Object[] array = new Object[Array.getLength(value)];
            for (int i = 0; i < array.length; i++) {
                array[i] = Array.get(value, i);
            }
            return Arrays.toString(array);
        } else {
            return String.valueOf(value);
        }
    }

    private String getVisibilityModifier(final int modifiers) {
        if (Modifier.isPublic(modifiers)) {
            return "public ";
        }
        if (Modifier.isProtected(modifiers)) {
            return "protected ";
        }
        if (Modifier.isPrivate(modifiers)) {
            return "private ";
        }
        return "";
    }
}
