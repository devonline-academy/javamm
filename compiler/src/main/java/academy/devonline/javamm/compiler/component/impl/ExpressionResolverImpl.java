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

package academy.devonline.javamm.compiler.component.impl;

import academy.devonline.javamm.code.fragment.Expression;
import academy.devonline.javamm.code.fragment.Lexeme;
import academy.devonline.javamm.code.fragment.SourceLine;
import academy.devonline.javamm.compiler.component.ComplexExpressionBuilder;
import academy.devonline.javamm.compiler.component.ComplexLexemeValidator;
import academy.devonline.javamm.compiler.component.ExpressionBuilder;
import academy.devonline.javamm.compiler.component.ExpressionResolver;
import academy.devonline.javamm.compiler.component.LexemeBuilder;
import academy.devonline.javamm.compiler.component.UnaryOperatorUpdater;
import academy.devonline.javamm.compiler.component.impl.error.JavammLineSyntaxError;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import static java.util.Objects.requireNonNull;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
public final class ExpressionResolverImpl implements ExpressionResolver {

    private final Collection<ExpressionBuilder> expressionBuilders;

    private final LexemeBuilder lexemeBuilder;

    private final UnaryOperatorUpdater unaryOperatorUpdater;

    private final ComplexLexemeValidator complexLexemeValidator;

    private final ComplexExpressionBuilder complexExpressionBuilder;

    public ExpressionResolverImpl(final Set<ExpressionBuilder> expressionBuilders,
                                  final LexemeBuilder lexemeBuilder,
                                  final UnaryOperatorUpdater unaryOperatorUpdater,
                                  final ComplexLexemeValidator complexLexemeValidator,
                                  final ComplexExpressionBuilder complexExpressionBuilder) {
        this.expressionBuilders = List.copyOf(expressionBuilders);
        this.lexemeBuilder = requireNonNull(lexemeBuilder);
        this.lexemeBuilder.setExpressionResolver(this);
        this.unaryOperatorUpdater = requireNonNull(unaryOperatorUpdater);
        this.complexLexemeValidator = requireNonNull(complexLexemeValidator);
        this.complexExpressionBuilder = requireNonNull(complexExpressionBuilder);
    }

    //Imperative
    @Override
    public Expression resolve(final List<String> expressionTokens, final SourceLine sourceLine) {
        for (final ExpressionBuilder expressionBuilder : expressionBuilders) {
            if (expressionBuilder.canBuild(expressionTokens)) {
                return expressionBuilder.build(expressionTokens, sourceLine);
            }
        }
        return resolveComplexExpression(expressionTokens, sourceLine);
    }

    /**
     * var a = 1 + 3 * 5
     * var a = ( 1 + 3 ) * a
     * <p>
     * var a = sum ( 1 , b ) + 4                                                -------->  var a = x + 4
     * var a = array [ 23 + g - h ] - a                                         -------->  var a = x - a
     * var a = array [ 23 + g - h ]                                             -------->  var a = x
     * var a = - array [ 23 + g - h ]                                           -------->  var a = - x
     * var a = sum ( array [ 23 + g ] , array [ 23 ] ) - 4 * sum ( 1 , 2 + b )  -------->  var a = x - 4 * y
     * var a = sum ( 1 , 2 + b ) + 4 * ( array [ 23 + g - h ] - 6 )             -------->  var a = x + 4 * ( y - z )
     */
    private Expression resolveComplexExpression(final List<String> expressionTokens, final SourceLine sourceLine) {
        List<Lexeme> lexemes = lexemeBuilder.build(expressionTokens, sourceLine);
        if (lexemes.size() == 1) {
            return toExpression(sourceLine, lexemes);
        } else {
            lexemes = unaryOperatorUpdater.update(lexemes, sourceLine);
            if (lexemes.size() == 1) {
                return toExpression(sourceLine, lexemes);
            } else {
                complexLexemeValidator.validate(lexemes, sourceLine);
                return complexExpressionBuilder.build(lexemes, sourceLine);
            }
        }
    }

    private Expression toExpression(final SourceLine sourceLine, final List<Lexeme> lexemes) {
        final Lexeme lexeme = lexemes.get(0);
        if (lexeme instanceof Expression) {
            return (Expression) lexeme;
        } else {
            throw new JavammLineSyntaxError("Unresolved expression: " + lexeme, sourceLine);
        }
    }

    /*
    //Functional
    @Override
    public Expression resolve(final List<String> expressionTokens, final SourceLine sourceLine) {
        return expressionBuilders
                .stream()
                .filter(expressionBuilder -> expressionBuilder.canBuild(expressionTokens))
                .findFirst()
                .map(expressionBuilder -> expressionBuilder.build(expressionTokens, sourceLine))
                .orElseGet(() -> {
                    complexLexemeValidator.validate(lexemes, sourceLine);
                    return resolveComplexExpression(expressionTokens, sourceLine)
                });
    }*/
}
