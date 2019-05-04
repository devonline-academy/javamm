package academy.devonline.temp.generics;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author devonline
 * @link http://devonline.academy/javamm
 */
@SuppressWarnings("CheckStyle")
public final class Example04OverrideMethodReturnType {

    private Example04OverrideMethodReturnType() {
    }

    public static void main(final String[] args) {
        final List<NumberProvider> providers = List.of(
            new IntegerProvider(),
            new DoubleProvider(),
            new BigDecimalProvider()
        );
        for (final NumberProvider provider : providers) {
            System.out.println(provider.getValue());
        }
    }

    /**
     * @author devonline
     * @link http://devonline.academy/javamm
     */
    private interface NumberProvider {

        Number getValue();
    }

    /**
     * @author devonline
     * @link http://devonline.academy/javamm
     */
    private static final class IntegerProvider implements NumberProvider {

        @Override
        public Integer getValue() {
            return 12;//Integer.valueOf(12);
        }
    }

    /**
     * @author devonline
     * @link http://devonline.academy/javamm
     */
    private static final class DoubleProvider implements NumberProvider {

        @Override
        public Double getValue() {
            return 12.1;//Double.valueOf(12.1);
        }
    }

    /**
     * @author devonline
     * @link http://devonline.academy/javamm
     */
    private static final class BigDecimalProvider implements NumberProvider {

        @Override
        public BigDecimal getValue() {
            return new BigDecimal("12.1");
        }
    }
}
