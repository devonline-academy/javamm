package academy.devonline.temp.to_string;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
abstract class VarDeclaration {

    final String variableName;

    final Object value;

    VarDeclaration(final String variableName,
                   final Object value) {
        this.variableName = variableName;
        this.value = value;
    }
}
