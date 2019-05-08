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

package academy.devonline.javamm.code.fragment.operator;

import academy.devonline.javamm.code.fragment.Operator;

import java.util.Optional;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
public enum UnaryOperator implements Operator {

    INCREMENT("++"),

    DECREMENT("--"),

    ARITHMETIC_UNARY_PLUS("+"),

    ARITHMETIC_UNARY_MINUS("-"),

    BITWISE_INVERSE("~"),

    HASH_CODE("#"),

    LOGIC_NOT("!");

    private final String code;

    UnaryOperator(final String code) {
        this.code = code;
    }

    public static Optional<UnaryOperator> of(final String code) {
        for (final UnaryOperator operator : values()) {
            if (operator.getCode().equals(code)) {
                return Optional.of(operator);
            }
        }
        return Optional.empty();
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public boolean isAssignment() {
        return this == INCREMENT || this == DECREMENT;
    }

    @Override
    public String getType() {
        return "unary";
    }

    @Override
    public String toString() {
        return code;
    }
}
