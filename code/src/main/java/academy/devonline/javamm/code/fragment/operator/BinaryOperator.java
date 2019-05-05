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

import static academy.devonline.javamm.code.syntax.Keywords.TYPEOF;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
public enum BinaryOperator implements Operator {

    ARITHMETIC_MULTIPLICATION("*"),

    ARITHMETIC_DIVISION("/"),

    ARITHMETIC_MODULUS("%"),

    ARITHMETIC_ADDITION("+"),

    ARITHMETIC_SUBTRACTION("-"),


    PREDICATE_GREATER(">"),

    PREDICATE_GREATER_OR_EQUALS(">="),

    PREDICATE_LESS("<"),

    PREDICATE_LESS_OR_EQUALS("<="),

    PREDICATE_EQUALS("=="),

    PREDICATE_NOT_EQUALS("!="),

    PREDICATE_TYPEOF(TYPEOF),


    BITWISE_AND("&"),

    BITWISE_OR("|"),

    BITWISE_XOR("^"),

    BITWISE_SHIFT_RIGHT(">>"),

    BITWISE_SHIFT_LEFT("<<"),

    BITWISE_SHIFT_RIGHT_ZERO_FILL(">>>"),


    LOGIC_AND("&&"),

    LOGIC_OR("||"),


    ASSIGNMENT_MULTIPLICATION("*="),

    ASSIGNMENT_DIVISION("/="),

    ASSIGNMENT_MODULUS("%="),

    ASSIGNMENT_ADDITION("+="),

    ASSIGNMENT_SUBTRACTION("-="),

    ASSIGNMENT_BITWISE_SHIFT_RIGHT(">>="),

    ASSIGNMENT_BITWISE_SHIFT_LEFT("<<="),

    ASSIGNMENT_BITWISE_SHIFT_RIGHT_ZERO_FILL(">>>="),

    ASSIGNMENT_BITWISE_AND("&="),

    ASSIGNMENT_BITWISE_OR("|="),

    ASSIGNMENT_BITWISE_XOR("^=");

    private final String code;

    BinaryOperator(final String code) {
        this.code = code;
    }

    public static Optional<BinaryOperator> of(final String code) {
        for (final BinaryOperator operator : values()) {
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
        return name().startsWith("ASSIGNMENT_");
    }

    @Override
    public String getType() {
        return "binary";
    }

    @Override
    public String toString() {
        return code;
    }
}
