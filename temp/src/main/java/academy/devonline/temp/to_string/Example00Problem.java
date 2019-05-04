package academy.devonline.temp.to_string;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
public final class Example00Problem {

    private Example00Problem() {
    }

    public static void main(final String[] args) {
        final Class1 class1 = new Class1("test");
        System.out.println(class1);

        final Class2 class2 = new Class2("test");
        System.out.println(class2);

        // debug
    }

    /**
     * @author devonline
     * @link http://devonline.academy/javamm
     */
    private static class Class1 {

        private final String value;

        private Class1(final String value) {
            this.value = value;
        }
    }


    /**
     * @author devonline
     * @link http://devonline.academy/javamm
     */
    private static class Class2 {

        private final String value;

        private Class2(final String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "Class2{" +
                    "value='" + value + '\'' +
                    '}';
        }
    }
}
