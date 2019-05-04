package academy.devonline.temp.generics;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
@SuppressWarnings("CheckStyle")
public final class Example03CastProblem {

    private Example03CastProblem() {
    }

    public static void main(final String[] args) {
        final List<Integer> items = List.of(3, 4, 0, 1, 5);

        System.out.println(findMin(items));
        System.out.println(findMin(List.of("hello", "world", "java")));
        System.out.println(findMin(List.of(new AtomicInteger(3), new AtomicInteger(0), new AtomicInteger(1))));
    }

    private static <T> T findMin(final List<T> items) {
        T min = items.get(0);
        for (final T item : items) {
            if (((Comparable<T>) item).compareTo(min) < 0) {
                min = item;
            }
        }
        return min;
    }
}
