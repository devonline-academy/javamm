package academy.devonline.temp.functional;

import java.util.Optional;
import java.util.Random;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
@SuppressWarnings("CheckStyle")
public final class Example05Optional {

    private static final boolean NOT_NULL = new Random().nextBoolean();

    private Example05Optional() {
    }

    public static void main(final String[] args) {
        //Imperative
        final String s1 = nextString1();
        if (s1 != null) {
            System.out.println(s1.length());
        } else {
            System.out.println(-1);
        }

        //Imperative
        final Optional<String> s2Optional = nextString2();
        if (s2Optional.isPresent()) {
            System.out.println(s2Optional.get().length());
        } else {
            System.out.println(-1);
        }

        //Functional
        System.out.println(nextString2().map(String::length).orElse(-1));
    }

    private static String nextString1() {
        return NOT_NULL ? "hello world" : null;
    }

    private static Optional<String> nextString2() {
        return NOT_NULL ? Optional.of("hello world") : Optional.empty();
    }
}
