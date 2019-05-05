package academy.devonline.temp.type;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
@SuppressWarnings("CheckStyle")
public final class Example01Main {

    private Example01Main() {
    }

    public static void main(final String[] args) {
        final String variable1 = "a";
        System.out.println(new DemoVarExpression(variable1));

        final String variable2 = "array";
        System.out.println(new DemoVarExpression(variable2));

        final String variable3 = new Example01Main().toString();
        System.out.println(new DemoVarExpression(variable3));
    }
}
