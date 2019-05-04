package academy.devonline.temp.to_string;

import static java.lang.String.format;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
public class Example03VarDeclarationFormat extends VarDeclaration {

    public Example03VarDeclarationFormat(final String variableName, final Object value) {
        super(variableName, value);
    }

    @Override
    public String toString() {
        return format("var %s = '%s'", variableName, value);
    }
}
