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

package academy.devonline.javamm.interpreter.component.impl.expression.evaluator;

import academy.devonline.javamm.code.fragment.expression.PostfixNotationExpression;
import academy.devonline.javamm.interpreter.component.ExpressionEvaluator;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
public class PostfixNotationExpressionEvaluator extends AbstractExpressionEvaluator
    implements ExpressionEvaluator<PostfixNotationExpression> {

    @Override
    public Class<PostfixNotationExpression> getExpressionClass() {
        return PostfixNotationExpression.class;
    }

    @Override
    public Object evaluate(final PostfixNotationExpression expression) {
        return null;
    }
}
