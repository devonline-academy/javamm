package academy.devonline.temp.creational_patterns;

import academy.devonline.javamm.compiler.component.VariableBuilder;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 * {@link VariableBuilder}
 */
public final class Example03Factory {

    private Example03Factory() {
    }

    public static Instance create() {
        return new Instance();
    }

    public static final class Instance {

        private Instance() {
        }
    }
}
