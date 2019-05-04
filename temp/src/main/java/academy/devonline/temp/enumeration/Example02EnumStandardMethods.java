package academy.devonline.temp.enumeration;

import java.util.List;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
@SuppressWarnings("CheckStyle")
public final class Example02EnumStandardMethods {

    private Example02EnumStandardMethods() {
    }

    public static void main(final String[] args) {
        final Color color = Color.RED;
        final Enum<Color> colorEnum = color;

        System.out.println("name=" + color.name());
        System.out.println("ordinal=" + color.ordinal());
        System.out.println("toString=" + color.toString());
        System.out.println("-------------------------------------");
        System.out.println("name=" + colorEnum.name());
        System.out.println("ordinal=" + colorEnum.ordinal());
        System.out.println("toString=" + colorEnum.toString());
        System.out.println("-------------------------------------");

        final Color color2 = Color.valueOf("RED");
        System.out.println("valueOf=" + color2);

        final Color[] colors = Color.values();
        System.out.print("All values: ");
        for (final Color color1 : colors) {
            System.out.print(color1 + ", ");
        }
        System.out.println();

        final List<Color> colorList = List.of(Enum.valueOf(Color.class, "RED"));
        System.out.println(colorList);

        //Compare:
        if(color == Color.RED){
            System.out.println("Color is red");
        }
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
