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

import java.util.Optional;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
public final class Example04Constructor {

    private final Integer param1;

    private final String param2;

    private final Boolean param3;

    private final Object param4;

    private final Integer param5;

    private Example04Constructor(final Integer param1,
                                 final String param2,
                                 final Boolean param3,
                                 final Object param4,
                                 final Integer param5) {
        this.param1 = param1;
        this.param2 = param2;
        this.param3 = param3;
        this.param4 = param4;
        this.param5 = param5;
    }

    public Integer getParam1() {
        return param1;
    }

    public String getParam2() {
        return param2;
    }

    public Optional<Boolean> getParam3() {
        return Optional.ofNullable(param3);
    }

    public Optional<Object> getParam4() {
        return Optional.ofNullable(param4);
    }

    public Optional<Integer> getParam5() {
        return Optional.ofNullable(param5);
    }

    /**
     * @author devonline
     * @link http://devonline.academy/javamm
     */
    public static final class Builder {

        private Integer param1;

        private String param2;

        private Boolean param3;

        private Object param4;

        private Integer param5;

        public Builder setParam1(final Integer param1) {
            this.param1 = param1;
            return this;
        }

        public Builder setParam2(final String param2) {
            this.param2 = param2;
            return this;
        }

        public Builder setParam3(final Boolean param3) {
            this.param3 = param3;
            return this;
        }

        public Builder setParam4(final Object param4) {
            this.param4 = param4;
            return this;
        }

        public Builder setParam5(final Integer param5) {
            this.param5 = param5;
            return this;
        }

        public Example04Constructor build() {
            return new Example04Constructor(param1, param2, param3, param4, param5);
        }
    }
}
