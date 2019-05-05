package academy.devonline.temp.type;

import academy.devonline.javamm.code.fragment.Expression;

import static java.util.Objects.requireNonNull;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
class DemoVarExpression implements Expression {

    private final String variable;

    DemoVarExpression(final String variable) {
        this.variable = requireNonNull(variable);
    }

    public final String getVariable() {
        return variable;
    }

    @Override
    public String toString() {
        return variable;
    }
}
