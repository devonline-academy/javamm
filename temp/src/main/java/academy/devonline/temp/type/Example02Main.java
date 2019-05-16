package academy.devonline.temp.type;

import academy.devonline.javamm.code.fragment.Variable;
import academy.devonline.javamm.code.fragment.expression.VariableExpression;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
@SuppressWarnings("CheckStyle")
public final class Example02Main {

    private Example02Main() {
    }

    public static void main(final String[] args) {
        /*
        final Variable variable1 = null;
        System.out.println(new VariableExpression(variable1));
        */

        final Variable variable2 = new Variable() {
            @Override
            public String getName() {
                return null;
            }

            @Override
            public String toString() {
                return super.toString();
            }

            @Override
            public int hashCode() {
                return super.hashCode();
            }

            @Override
            public boolean equals(final Object obj) {
                return super.equals(obj);
            }
        };
        System.out.println(new VariableExpression(variable2));
    }
}
