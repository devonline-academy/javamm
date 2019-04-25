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
public final class Example03Complex {

    private final int param1;

    private final String param2;

    private final boolean param3;

    private final Object param4;

    private final int param5;

    private Example03Complex(final int param1,
                             final String param2,
                             final boolean param3,
                             final Object param4,
                             final int param5) {
        this.param1 = param1;
        this.param2 = param2;
        this.param3 = param3;
        this.param4 = param4;
        this.param5 = param5;
    }

    public int getParam1() {
        return param1;
    }

    public String getParam2() {
        return param2;
    }

    public boolean getParam3() {
        return param3;
    }

    public Object getParam4() {
        return param4;
    }

    public int getParam5() {
        return param5;
    }

    /**
     * @author devonline
     * @link http://devonline.academy/javamm
     */
    public static final class Builder {

        private int param1;

        private String param2;

        private boolean param3;

        private Object param4;

        private int param5;

        public Builder setParam1(final int param1) {
            this.param1 = param1;
            return this;
        }

        public Builder setParam2(final String param2) {
            this.param2 = param2;
            return this;
        }

        public Builder setParam3(final boolean param3) {
            this.param3 = param3;
            return this;
        }

        public Builder setParam4(final Object param4) {
            this.param4 = param4;
            return this;
        }

        public Builder setParam5(final int param5) {
            this.param5 = param5;
            return this;
        }

        public Example03Complex build() {
            return new Example03Complex(param1, param2, param3, param4, param5);
        }
    }
}
