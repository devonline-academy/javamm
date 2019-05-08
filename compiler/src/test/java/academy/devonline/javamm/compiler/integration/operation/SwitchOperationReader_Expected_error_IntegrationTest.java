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

package academy.devonline.javamm.compiler.integration.operation;

import academy.devonline.javamm.compiler.integration.AbstractOperationReaderErrorIntegrationTest;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

import static java.util.List.of;
import static java.util.function.Function.identity;
import static org.junit.jupiter.params.provider.Arguments.arguments;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
class SwitchOperationReader_Expected_error_IntegrationTest extends AbstractOperationReaderErrorIntegrationTest {

    @Override
    protected Stream<Arguments> invalidSourceLineProvider() {
        return Stream.of(
            blockValidation(),
            expressionValidation(),
            switchValidation(),
            caseValidation(),
            defaultValidation()
        ).flatMap(identity());
    }

    private Stream<Arguments> blockValidation() {
        return named(Stream.of(
            arguments(
                of(
                    "switch"
                ), "Syntax error in 'module1' [Line: 2]: '{' expected at the end of the line"),
            arguments(
                of(
                    "switch ( a )"
                ), "Syntax error in 'module1' [Line: 2]: '{' expected at the end of the line"),
            arguments(
                of(
                    "switch ( a ) {"
                ), "Syntax error in 'module1': '}' expected to close 'switch' block statement at the end of file"),
            arguments(
                of(
                    "switch ( a ) {",

                    "} var a = 5"
                ), "Syntax error in 'module1' [Line: 3]: '}' expected only")
        ), "block");
    }

    private Stream<Arguments> expressionValidation() {
        return named(Stream.of(
            arguments(
                of(
                    "switch ( ) {"
                ), "Syntax error in 'module1' [Line: 2]: An expression is expected between '(' and ')'")
        ), "expression");
    }

    private Stream<Arguments> switchValidation() {
        return named(Stream.of(
            arguments(
                of(
                    "switch {"
                ), "Syntax error in 'module1' [Line: 2]: '(' expected after 'switch'"),
            arguments(
                of(
                    "switch a {"
                ), "Syntax error in 'module1' [Line: 2]: '(' expected after 'switch'"),
            arguments(
                of(
                    "switch a ) {"
                ), "Syntax error in 'module1' [Line: 2]: '(' expected after 'switch'"),
            arguments(
                of(
                    "switch ( a {"
                ), "Syntax error in 'module1' [Line: 2]: ')' expected before '{'"),
            arguments(
                of(
                    "switch ) a  ( {"
                ), "Syntax error in 'module1' [Line: 2]: '(' expected after 'switch'"),
            arguments(
                of(
                    "switch a ( a ) {"
                ), "Syntax error in 'module1' [Line: 2]: '(' expected after 'switch'"),
            arguments(
                of(
                    "switch this true ( a ) {"
                ), "Syntax error in 'module1' [Line: 2]: '(' expected after 'switch'"),
            arguments(
                of(
                    "switch ( a ) then {"
                ), "Syntax error in 'module1' [Line: 2]: ')' expected before '{'"),
            arguments(
                of(
                    "switch ( a ) then should be {"
                ), "Syntax error in 'module1' [Line: 2]: ')' expected before '{'"),
            arguments(
                of(
                    "switch ( a ) {",
                    "break",
                    "}"
                ), "Syntax error in 'module1' [Line: 3]: Unsupported 'switch' child statement")
        ), "switch");
    }

    private Stream<Arguments> caseValidation() {
        return named(Stream.of(
            arguments(
                of(
                    "switch ( a ) {",
                    "case 1 :",

                    "}",
                    "}"
                ), "Syntax error in 'module1' [Line: 3]: '{' expected at the end of the line"),
            arguments(
                of(
                    "switch ( a ) {",
                    "case 1 2 : {",

                    "}",
                    "}"
                ), "Syntax error in 'module1' [Line: 3]: An constant expected between 'case' and ':'"),
            arguments(
                of(
                    "switch ( a ) {",
                    "case 1 + 2 : {",

                    "}",
                    "}"
                ), "Syntax error in 'module1' [Line: 3]: An constant expected between 'case' and ':'"),
            arguments(
                of(
                    "switch ( a ) {",
                    "case b : {",

                    "}",
                    "}"
                ), "Syntax error in 'module1' [Line: 3]: An constant expected between 'case' and ':'"),
            arguments(
                of(
                    "switch ( a ) {",
                    "case : {",

                    "}",
                    "}"
                ), "Syntax error in 'module1' [Line: 3]: An constant expected between 'case' and ':'"),
            arguments(
                of(
                    "switch ( a ) {",
                    "case 1 {",

                    "}",
                    "}"
                ), "Syntax error in 'module1' [Line: 3]: ':' expected before '{'"),

            arguments(
                of(
                    "switch ( a ) {",
                    "case : 1 {",

                    "}",
                    "}"
                ), "Syntax error in 'module1' [Line: 3]: ':' expected before '{'"),
            arguments(
                of(
                    "switch ( a ) {",
                    "case : ; {",

                    "}",
                    "}"
                ), "Syntax error in 'module1' [Line: 3]: ':' expected before '{'"),
            arguments(
                of(
                    "switch ( a ) {",
                    "case 1 : {",

                    "}",
                    "case 1 : {",

                    "}",
                    "}"
                ), "Syntax error in 'module1' [Line: 5]: Duplicate case label '1'")
        ), "case");
    }

    private Stream<Arguments> defaultValidation() {
        return named(Stream.of(
            arguments(
                of(
                    "switch ( a ) {",
                    "default",

                    "}",
                    "}"
                ), "Syntax error in 'module1' [Line: 3]: '{' expected at the end of the line"),
            arguments(
                of(
                    "switch ( a ) {",
                    "default {",

                    "}",
                    "}"
                ), "Syntax error in 'module1' [Line: 3]: ':' expected after 'default'"),
            arguments(
                of(
                    "switch ( a ) {",
                    "default 3 {",

                    "}",
                    "}"
                ), "Syntax error in 'module1' [Line: 3]: ':' expected after 'default'"),
            arguments(
                of(
                    "switch ( a ) {",
                    "default 3 : {",

                    "}",
                    "}"
                ), "Syntax error in 'module1' [Line: 3]: ':' expected after 'default'"),
            arguments(
                of(
                    "switch ( a ) {",
                    "default : 3 {",

                    "}",
                    "}"
                ), "Syntax error in 'module1' [Line: 3]: ':' expected before '{'"),
            arguments(
                of(
                    "switch ( a ) {",
                    "default : : {",

                    "}",
                    "}"
                ), "Syntax error in 'module1' [Line: 3]: 'default : {' expected"),
            arguments(
                of(
                    "switch ( a ) {",
                    "default : 3 : {",

                    "}",
                    "}"
                ), "Syntax error in 'module1' [Line: 3]: 'default : {' expected"),
            arguments(
                of(
                    "switch ( a ) {",
                    "default : {",

                    "}",
                    "default : {",

                    "}",
                    "}"
                ), "Syntax error in 'module1' [Line: 5]: Duplicate default label")
        ), "default");
    }
}
