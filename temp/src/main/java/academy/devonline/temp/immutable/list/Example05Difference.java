package academy.devonline.temp.immutable.list;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
@SuppressWarnings("CheckStyle")
public final class Example05Difference {

    private Example05Difference() {
    }

    public static void main(final String[] args) {
        final List<Integer> list = new ArrayList<>();
        list.addAll(Arrays.asList(1, 2, 3, 4, 5));

        final List<Integer> unModifiable1 = List.copyOf(list);
        final List<Integer> unModifiable2 = Collections.unmodifiableList(list);
        System.out.println(unModifiable1);
        System.out.println(unModifiable2);

        list.clear();
        System.out.println(unModifiable1);
        System.out.println(unModifiable2);
    }
}
