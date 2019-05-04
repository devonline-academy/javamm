package academy.devonline.temp.to_string;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
public class Example02VarDeclarationStringBuilder extends VarDeclaration {

    public Example02VarDeclarationStringBuilder(final String variableName, final Object value) {
        super(variableName, value);
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("var ");
        stringBuilder.append(variableName);
        stringBuilder.append(" = '");
        stringBuilder.append(value);
        stringBuilder.append("'");
        return stringBuilder.toString();
    }
}
