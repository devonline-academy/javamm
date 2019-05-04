package academy.devonline.temp.enumeration;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
@SuppressWarnings("CheckStyle")
public final class Example01EnumSimpleUsage {

    private Example01EnumSimpleUsage() {
    }

    public static void main(final String[] args) {
        final Color color = getDefaultColor();
        printColor(color);

        printColor(Color.BLUE);
    }

    private static void printColor(final Color color) {
        System.out.println(color);
    }

    private static Color getDefaultColor() {
        return Color.RED;
    }

    /**
     * @author devonline
     * @link http://devonline.academy/javamm
     */
    private enum Color {

        RED,

        GREEN,

        BLUE
    }
}
