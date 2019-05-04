package academy.devonline.temp.generics;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
@SuppressWarnings("CheckStyle")
public final class Example05MultipleInheritance1 {

    private Example05MultipleInheritance1() {
    }

    public static void main(final String[] args) {
        final IntegerProvider integerProvider = new IntegerProvider();
        final NumberProvider<Integer> numberProvider = integerProvider;
        final ComparableProvider<Integer> comparableProvider = integerProvider;
    }

    /**
     * @author devonline
     * @link http://devonline.academy/javamm
     */
    private interface NumberProvider<T extends Number> {

        T getValue();
    }

    /**
     * @author devonline
     * @link http://devonline.academy/javamm
     */
    private interface ComparableProvider<T extends Comparable<T>> {

        T getValue();
    }

    /**
     * @author devonline
     * @link http://devonline.academy/javamm
     */
    private static final class IntegerProvider implements NumberProvider<Integer>, ComparableProvider<Integer> {

        @Override
        public Integer getValue() {
            return 12;
        }
    }

    /**
     * @author devonline
     * @link http://devonline.academy/javamm
     */
    /*private static final class BooleanProvider implements NumberProvider<Boolean>, ComparableProvider<Boolean> {

        @Override
        public Boolean getValue() {
            return true;
        }
    }*/
}
