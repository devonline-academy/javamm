package academy.devonline.temp.to_string;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
public class Example01VarDeclarationConcat extends VarDeclaration {

    public Example01VarDeclarationConcat(final String variableName, final Object value) {
        super(variableName, value);
    }

    @Override
    public String toString() {
        return "var " + variableName + " = '" + value + "'";
    }
}
