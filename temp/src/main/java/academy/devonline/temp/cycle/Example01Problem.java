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

package academy.devonline.temp.cycle;

import java.util.List;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
@SuppressWarnings("CheckStyle")
public final class Example01Problem {

    private Example01Problem() {
    }

    public static void main(final String[] args) {
        final Parent parent = new Parent(List.of(new Child(), new Child()));
        parent.doSomething();
    }

    /**
     * @author devonline
     * @link http://devonline.academy/javamm
     */
    private static final class Child {

        private Parent parent;

        public void doSomething() {
            System.out.println("Child");
            parent.doSomething();
        }
    }

    /**
     * @author devonline
     * @link http://devonline.academy/javamm
     */
    private static final class Parent {

        private final List<Child> children;

        public Parent(final List<Child> children) {
            this.children = List.copyOf(children);
        }

        public void doSomething() {
            for (final Child child : children) {
                child.doSomething();
            }
        }
    }
}
