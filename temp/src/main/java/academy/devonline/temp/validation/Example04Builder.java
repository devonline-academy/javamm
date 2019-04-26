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

package academy.devonline.temp.validation;

import java.util.Optional;

import static java.util.Objects.requireNonNull;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
public final class Example04Builder {

    private final String param1;

    private final Object param2;

    private Example04Builder(final String param1,
                             final Object param2) {
        this.param1 = requireNonNull(param1);
        // Optional
        this.param2 = param2;
    }

    public String getParam1() {
        return param1;
    }

    public Optional<Object> getParam2() {
        return Optional.ofNullable(param2);
    }

    public static class Builder {

        private String param1;

        private Object param2;

        public Builder setParam1(final String param1) {
            this.param1 = requireNonNull(param1);
            return this;
        }

        public Builder setParam2(final Object param2) {
            this.param2 = requireNonNull(param2);
            return this;
        }

        public Example04Builder build() {
            return new Example04Builder(param1, param2);
        }
    }
}
