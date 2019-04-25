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

package academy.devonline.temp.immutable.simple;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
public class Example01Simple {

    private final int param1;

    private final String param2;

    public Example01Simple(final int param1,
                           final String param2) {
        this.param1 = param1;
        this.param2 = param2;
    }

    /*public void setParam1(final Object param1) {
        this.param1 = param1;
    }*/

    public int getParam1() {
        return param1;
    }

    /*public void setParam2(final Object param2) {
        this.param2 = param2;
    }*/

    public String getParam2() {
        return param2;
    }
}
