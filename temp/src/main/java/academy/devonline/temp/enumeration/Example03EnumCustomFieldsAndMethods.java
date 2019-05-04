package academy.devonline.temp.enumeration;

import java.util.Iterator;
import java.util.List;

import static java.lang.String.format;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
@SuppressWarnings("CheckStyle")
public final class Example03EnumCustomFieldsAndMethods {

    private Example03EnumCustomFieldsAndMethods() {
    }

    public static void main(final String[] args) {
        final Color color = Color.RED;

        for (final Color color1 : color) {
            System.out.println(format("%s -> %s", color1.name(), Integer.toHexString(color1.getCode())));
        }
    }

    /**
     *
     * @author devonline
     * @link http://devonline.academy/javamm
     */
    private enum Color implements Iterable<Color> {

        RED(0xFF0000),

        GREEN(0x00FF00),

        BLUE(0x0000FF);

        private final int code;

        Color(final int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }

        @Override
        public Iterator<Color> iterator() {
            return List.of(values()).iterator();
        }
    }
}
