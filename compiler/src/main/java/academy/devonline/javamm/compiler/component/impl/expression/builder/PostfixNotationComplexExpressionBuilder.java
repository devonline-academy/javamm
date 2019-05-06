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

package academy.devonline.javamm.compiler.component.impl.expression.builder;

import academy.devonline.javamm.code.fragment.Lexeme;
import academy.devonline.javamm.code.fragment.SourceLine;
import academy.devonline.javamm.code.fragment.expression.ComplexExpression;
import academy.devonline.javamm.compiler.component.ComplexExpressionBuilder;
import academy.devonline.javamm.compiler.component.PrecedenceOperatorResolver;

import java.util.List;

/**
 * Infix      ->   Postfix
 * 7 - 2 * 3  ->   7 2 3 * -
 *
 * @author devonline
 * @link http://devonline.academy/javamm
 * @link http://interactivepython.org/runestone/static/pythonds/BasicDS/InfixPrefixandPostfixExpressions.html
 */
public class PostfixNotationComplexExpressionBuilder implements ComplexExpressionBuilder {

    public PostfixNotationComplexExpressionBuilder(final PrecedenceOperatorResolver precedenceOperatorResolver) {

    }

    @Override
    public ComplexExpression build(final List<Lexeme> lexemes, final SourceLine sourceLine) {
        return null;
    }
}
