package academy.devonline.temp.creational_patterns;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 * {@link academy.devonline.javamm.interpreter.component.impl.calculator.arithmetic.AdditionBinaryExpressionCalculator }
 * {@link academy.devonline.javamm.code.fragment.expression.ConstantExpression }
 */
public final class Example02StaticMethod {

    private Example02StaticMethod() {
    }

    public static Example02StaticMethod create() {
        return new Example02StaticMethod();
    }
}
