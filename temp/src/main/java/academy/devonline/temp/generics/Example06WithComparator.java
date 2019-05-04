package academy.devonline.temp.generics;

import java.util.Comparator;
import java.util.List;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
@SuppressWarnings("CheckStyle")
public final class Example06WithComparator {

    private Example06WithComparator() {
    }

    public static void main(final String[] args) {
        final List<Integer> items = List.of(3, 4, 0, 1, 5);

        final Integer result1 = findMin(items, Comparator.naturalOrder());
        System.out.println(result1);
        final Integer result2 = findMin(items, Comparator.reverseOrder());
        System.out.println(result2);
    }

    private static <T> T findMin(final List<T> items, final Comparator<T> comparator) {
        T min = items.get(0);
        for (final T item : items) {
            if (comparator.compare(item, min) < 0) {
                min = item;
            }
        }
        return min;
    }
}
