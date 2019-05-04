package academy.devonline.temp.generics;

import java.util.List;
import java.util.Objects;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
@SuppressWarnings("CheckStyle")
public final class Example01Problem {

    private Example01Problem() {
    }

    public static void main(final String[] args) {
        final List<Object> items = List.of(0, 1, 2, 3, 4, 5);

        System.out.println(linearSearch(items, 5));
        System.out.println(linearSearch(items, "5"));
    }

    private static int linearSearch(final List<Object> items, final Object query) {
        for (int i = 0; i < items.size(); i++) {
            if (Objects.equals(query, items.get(i))) {
                return i;
            }
        }
        return -1;
    }
}
