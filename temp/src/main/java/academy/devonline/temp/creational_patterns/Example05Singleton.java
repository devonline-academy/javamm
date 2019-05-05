package academy.devonline.temp.creational_patterns;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 * {@link academy.devonline.javamm.code.fragment.expression.NullValueExpression}
 */
public final class Example05Singleton {

    private static final Example05Singleton INSTANCE = new Example05Singleton();

    private Example05Singleton() {
    }

    public static Example05Singleton getInstance() {
        return INSTANCE;
    }
}
