package academy.devonline.temp.functional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
@SuppressWarnings("CheckStyle")
public final class Example04SumOfNumbers {

    private Example04SumOfNumbers() {
    }

    public static void main(final String[] args) {
        final List<Integer> numbers = IntStream.range(0, 100).boxed().collect(Collectors.toUnmodifiableList());

        //Imperative
        int sum = 0;
        for (final Integer number : numbers) {
            sum += number;
        }
        System.out.println(sum);

        //Functional: reduce
        System.out.println(numbers.stream().reduce((n1, n2) -> n1 + n2).orElse(0));

        //Functional: sum
        System.out.println(numbers.stream().mapToInt(value -> value).sum());
    }
}
