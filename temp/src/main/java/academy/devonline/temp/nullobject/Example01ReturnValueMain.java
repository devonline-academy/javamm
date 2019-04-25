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

package academy.devonline.temp.nullobject;

import java.util.List;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
@SuppressWarnings("CheckStyle")
public final class Example01ReturnValueMain {

    private Example01ReturnValueMain() {
    }

    public static void main(final String[] args) {
        final Example01ReturnValue example = new Example01ReturnValue();
        System.out.println(example.getRequiredObject());
        System.out.println(example.getOptionalObject().toString());
        System.out.println(example.getRequiredList().toString());
        System.out.println(example.getOptionalList().toString());

        final List<String> optional = example.getOptionalList();
        // Not necessary:
        if (optional != null) {
            System.out.println(example.getOptionalList().toString());
        } else {
            System.out.println("null");
        }
    }
}
