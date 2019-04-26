package academy.devonline.temp.junit;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
@SuppressWarnings("CheckStyle")
public final class DivBinaryCalculatorMainVersion3Test1 {

    private DivBinaryCalculatorMainVersion3Test1() {
    }

    public static void main(final String[] args) {
        final BinaryCalculator calculator = new DivBinaryCalculator();

        final int result = calculator.calculate(12, 4);
        if (3 == result) {
            System.out.println("Calculate success: 12 / 4 = 3");
        } else {
            System.out.println("Calculate failed: expected=3, actual=" + result);
        }
    }
}
